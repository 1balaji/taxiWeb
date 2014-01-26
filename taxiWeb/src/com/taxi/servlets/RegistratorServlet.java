package com.taxi.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taxi.ejbs.User;
import com.taxi.enums.BEAN_ENUM;
import com.taxi.factory.*;



/**
 * Servlet implementation class RegistratorServlet
 */
public class RegistratorServlet extends GenericServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistratorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User user = (User)BeanFactory.instance().getBean(BEAN_ENUM.USER_BEAN.getBeanName());
		
		com.taxi.pojos.User entity = new com.taxi.pojos.User();
		
		user.registerUser(entity);
		
	}

}
