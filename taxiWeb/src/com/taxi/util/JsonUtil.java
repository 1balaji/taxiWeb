package com.taxi.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.taxi.servlets.StrConstants;

public class JsonUtil {

	private boolean success;
	private Map<String, Object> data;
	private Map<String, Object> attributes;
	private JSONObject jsonObj;
	
	private void initJsonObj() {
		this.jsonObj = new JSONObject();
	}
	
	public JsonUtil() {
		this.initJsonObj();
	}
	
	public JsonUtil(String jsonStr) {
		
		try {
			
			this.jsonObj = new JSONObject(jsonStr);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public JsonUtil(boolean success, Map<String, Object> attributes, Map<String, Object> data) {
		this.initJsonObj();
		this.success 	= success;
		this.attributes = attributes;
		this.data 		= data;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
	public Object get(String key) {
		
		Object obj = null;
		
		try {
			
			obj = this.jsonObj.get(key);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	
	public String getResponseStr() {
		
		try {
			
			this.jsonObj.put(StrConstants.API_JSON_KEY_SUCCESS, this.success);
			this.jsonObj.put(StrConstants.API_JSON_KEY_ATTR, this.attributes);
			this.jsonObj.put(StrConstants.API_JSON_KEY_DATA, this.data);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return this.jsonObj.toString();
	}

	
	
	public static void main(String[] args) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key1", "val1");
		Map<String, Object> subMap = new HashMap<String, Object>();
		subMap.put("subKey1", "subVal1");
		
		List<String> list = new ArrayList<String>();
		list.add("listVal1");
		list.add("listVal2");
		subMap.put("list", list);
		map.put("map", subMap);
		
		String str = new JsonUtil(true, map, map).getResponseStr();
		System.out.println(str);
		
	}
}
