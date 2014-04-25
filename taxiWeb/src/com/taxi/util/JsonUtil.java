package com.taxi.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.taxi.logging.Logger;

public class JsonUtil {
	
	@SuppressWarnings("unchecked")
	public static String getJSONStr(Object jsonObj) {
		if (jsonObj instanceof String) {
			return (String) jsonObj;
		}
		if (jsonObj instanceof HashMap) {
			return convertHashMaptoJSONString((HashMap<String, Object>) jsonObj);
			// TODO implement more verbose cases
			// JSONObject object=new JSONObject((Map<String, Object>) jsonObj);
			// return object.toString();
		} else if (jsonObj instanceof List) {
			return convertListToJSONString((List<Object>) jsonObj);
		}

		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static JSONArray listToJsonArray(List<HashMap<String, Object>> list) {
		JSONArray arr = new JSONArray();
		for (Object item : list) {
			if (item instanceof String) {
				arr.put(item);
			} else if (item instanceof HashMap) {
				arr.put(convertHashMaptoJSONString((HashMap<String, Object>) item));
			}
		}

		return arr;
	}

	@SuppressWarnings("unchecked")
	private static String convertListToJSONString(List<Object> list) {
		StringBuilder builder = new StringBuilder("[");
		boolean first = true;
		for (Object item : list) {
			if (!first)
				builder.append(",");
			first = false;

			builder.append('{');

			if (item instanceof String) {
				builder.append('"').append(item).append('"');
			} else if (item instanceof HashMap) {
				builder.append(convertHashMaptoJSONString((HashMap<String, Object>) item));
			}

			builder.append('}');
		}

		builder.append(']');
		return builder.toString();
	}

	// TODO this will handle a map that has one more level of map inside it's
	// values. For the second level, it uses ' instead of ".
	// I this needs to go deeper, this approach needs to be re-thought
	@SuppressWarnings("unchecked")
	public static String convertHashMaptoJSONString(HashMap<String, Object> map) {
		StringBuilder parsedString = new StringBuilder("{");
		boolean first = true;

		for (String key : map.keySet()) {
			if (!first)
				parsedString.append(",");
			first = false;

			Object val = map.get(key);
			if (val instanceof HashMap) {
				boolean innerFirst = true;
				for (String innerKey : ((HashMap<String, Object>) val).keySet()) {
					if (!innerFirst)
						parsedString.append(",");
					innerFirst = false;
					String innerVal = (String) ((HashMap<String, Object>) val).get(innerKey);
					parsedString.append("'").append(innerKey).append("':'").append(innerVal).append("'");
				}

			} else { // gonna assume it's a string
				if(key.equals("searchSpec")) {
					parsedString.append('"').append(key).append('"').append(":").append((String) val);

				} else {
				parsedString.append('"').append(key).append('"').append(":").append('"').append((String) val)
						.append('"');
				}
			}
		}

		parsedString.append("}");
		
		return parsedString.toString();
	}

	public static String convertHashMapToSingleQuotedJSONString(HashMap<String, String> map) {
		StringBuilder builder = new StringBuilder();
		builder.append("\"{");
		boolean first = true;
		for(String key : map.keySet()) {
			if(!first)
				builder.append(',');
			first = false;
			builder.append("'").append(key).append("':'").append(map.get(key)).append("'");	
		}
		builder.append('}').append('"');
		return builder.toString();
	}

	public static boolean isNullOrBlank(String str) {
		return (str == null || str.isEmpty());
	}
	
	public static Object getJSONObj(String jsonStr) {
		Object result = null;
		if (!isNullOrBlank(jsonStr)) {
			try {
				Object json = new JSONTokener(jsonStr).nextValue();
				if (json instanceof JSONArray) {
					result = getListFromJsonArray((JSONArray) json);
				} else {
					result = getMapFromJsonArray((JSONObject) json);
				}
			} catch (JSONException e) {
				Logger.logError(e.getMessage());
			}

		}
		return result;
	}
	
	private static List<Object> getListFromJsonArray(JSONArray array) {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < array.length(); i++) {
			list.add(convertJson(array.opt(i)));
		}
		return list;
	}

	public static Map<String, Object> getMapFromJsonArray(JSONObject object) {
		Map<String, Object> map = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		Iterator<String> keys = object.keys();
		while (keys.hasNext()) {
			String key = keys.next();
			map.put(key, convertJson(object.opt(key)));
		}
		return map;
	}

	private static Object convertJson(Object json) {
		if (json == JSONObject.NULL) {
			return null;
		}
		if (json instanceof JSONObject) {
			return getMapFromJsonArray((JSONObject) json);
		} else {
			if (json instanceof JSONArray) {
				return getListFromJsonArray((JSONArray) json);
			} else {
				return json;
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key1", "val1");
		Map<String, Object> subMap = new HashMap<String, Object>();
		subMap.put("subKey1", "subVal1");
		
		List<String> list = new ArrayList<String>();
		list.add("listVal1");
		list.add("listVal2");
		subMap.put("list", list);
		map.put("map", subMap);
		
		File json = new File("/Users/iraklivasha/Desktop/json.json");
		InputStream streamOfJson = null;
		try {
			streamOfJson = new FileInputStream(json);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		BufferedReader output = new BufferedReader(new InputStreamReader(streamOfJson));
		StringBuilder builder = new StringBuilder();
		for (String line = output.readLine(); line != null; line = output.readLine()) {
		     builder.append(line);
		}
		
		JSONObject obj = null;
		try {
			obj = new JSONObject(builder.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String str = obj.toString();
		Object o = getJSONObj(str);
		
		/* String str = new JsonUtil(true, map, map).getResponseStr();
		   System.out.println(str);
		*/   
		
	}
}
