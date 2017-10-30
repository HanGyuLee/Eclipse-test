package common.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FrontController
 */
@WebServlet(
		urlPatterns = { "*.do" }, 
		initParams = { 
				@WebInitParam(name = "propertyConfig", value = "C:/semi_sisNbro/WebContent/WEB-INF/Command.properties")
		})
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	HashMap<String, Object> cmdMap = new HashMap<String, Object>();
	//� ��ü�̴� ����� �� hashMap�� �־��ش�.
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		/*
		  �������� �ּ������� *.do�� �ϸ� FrontController ������ �޴µ�
		  ��ó���� �ڵ������� ����Ǿ����� �޼ҵ尡 init(ServletConfig config) �̴�
		  �׷��� �� �޼ҵ�� WAS(==�������̳�, ��Ĺ����)�� �����Ǿ��� �� 
		  �� 1���� ����Ǿ�����, �� ���Ŀ��� ������� �ʴ´�.
		  �׷��Ƿ� FrontController������ �����Ҷ� �� 1���� �����ؾ��� ��������
		  �� �޼ҵ�ӿ� ����� �ָ� �ȴ�.
		 */
		//System.out.println("==>Ȯ�ο� :FrontController������ init(ServletConfig config)�޼ҵ尡 �۵���.");
		
		String props = config.getInitParameter("propertyConfig");
		
		/*
			�ʱ�ȭ �Ķ���� ������ ���� "C:/myjsp/MyMVC/WebContent/WEB-INF/Command.properties"�� �����ͼ� 
			���ڿ� ���� props�� ������ѵ� ���̴�.
		*/
		//System.out.println("==>Ȯ�ο� : props������ ����� �� => " + props);
		//��� : ==>Ȯ�ο� : props������ ����� �� => C:/myjsp/MyMVC/WebContent/WEB-INF/Command.properties
		
		Properties pr = new Properties();
		
		FileInputStream fis = null;
		
		try {
			 fis = new FileInputStream(props);
			 
			 pr.load(fis);
			 /*
			     C:/myjsp/MyMVC/WebContent/WEB-INF/Command.properties ������ ������ �о�ٰ�
			     PropertiesŬ������ ��ü�� pr�� �ε��Ų��.
			          �׷��� pr�� �о�� ����(Command.properties)�� ���뿡��
			     = �� �������� ������ Ű������ ����, �������� value������ �ν��Ѵ�.
			  */
			 
			//String str_class =  pr.getProperty("/test1.do");
			/*
			    pr.getProperty("/test1.do");���� /test1.do�� key���̴�.
			     pr.getProperty("/test1.do"); �� ���ϰ��� 
			     Command.properties�� ����� key�� /test1.do �� �ش��ϰ�
			     value�� test.controller.Test1Controller�� �������ش�.
			   
			 */
			// System.out.println("Ȯ�ο� str_class�� ����� �� " + str_class);
			 //Ȯ�ο� str_class�� ����� �� test.controller.Test1Controller
			 
			 
			 Enumeration<Object> en = pr.keys();
			 /*
			    pr.keys(); ��
			    Command.properties ������ ���빰���� 
			    = �� �������� ���ʿ� �ִ� ��� key���鸸
			       �о���� ���̴�.
			    
			  */
			 while(en.hasMoreElements()){
				 String key_urlname = (String)en.nextElement();//������ element�� ������ �о��ش�.
				 
				 String str_classname = pr.getProperty(key_urlname);
				 
			 //System.out.println("==>Ȯ�ο� : key_urlname=>" + key_urlname);
			 /*	==>Ȯ�ο� : key_urlname=>/test3.do
			  	==>Ȯ�ο� : key_urlname=>/test2.do
				==>Ȯ�ο� : key_urlname=>/test1.do
			*/
				 
				//System.out.println("==>Ȯ�ο� :str_classname=>" + str_classname);
				/*
				 ==>Ȯ�ο� :str_classname=>test.controller.Test3Controller
				 ==>Ȯ�ο� :str_classname=>test.controller.Test2Controller
				 ==>Ȯ�ο� :str_classname=>test.controller.Test1Controller
				 */
				 
				 if(str_classname != null){
					 str_classname=str_classname.trim();//���������� �ٽ� ������ �ֱ�
					 Class cls = Class.forName(str_classname);//class�ϻ� ��ü�� �ƴϴ�.
					 Object obj = cls.newInstance(); //���ڿ��� �о� classȭ �ؼ� �� class�� ��ü�� ������ش�.
					 //��� �޾ƾ� ���� �𸣴ϱ� Object�� �޾ƿ´�.(������ �� �޾���)
					 cmdMap.put(key_urlname, obj);
					 
				 }
			 }//end of while---
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}//init(ServletConfig config)

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		requestProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	
		requestProcess(request, response);
	}
	
	
/*
	doGet�޼ҵ��  doPost�޼ҵ忡�� �ؾ��� ����ó���� 
	requestProcess(request, response); �� �޼ҵ�� �Ѱܹ�����.

*/
	private void requestProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {//doGet���� doPost���� �𸣴ϱ� �� �� �޾��ִ� �޼ҵ� �ϳ� ����
		
		String uri = request.getRequestURI();
		 //System.out.println("Ȯ�ο� uri : "+uri);
		 //Ȯ�ο� uri : /MyMVC/test1.do
		/*
		  request.getRequestURI(); �޼ҵ�� �������� �ּ�â���� ��û�Ǿ��� URI ���� ��ȯ�����ִ� �޼ҵ��̴�.
		  URI�� ���� URL�� http://ip�ּ�:9090/URI ���ε�
		  http://ip�ּ�:9090�� ������ �������� ���Ѵ�.
		    �׷��� ������ �߿� ?(GET���)���������� ���Ѵ�.
		 */
		
		String ctxName = request.getContextPath();
		//System.out.println("==>Ȯ�ο�  ctxName:"+ctxName);
		//==>Ȯ�ο�  ctxName:/MyMVC
		int beginIndex = ctxName.length();
		String mapkey = uri.substring(beginIndex);
		//System.out.println("=>Ȯ�ο� mapKey :"+mapkey);
		//=>Ȯ�ο� mapKey :/test2.do
		
		AbstractController action = (AbstractController)cmdMap.get(mapkey);
		if(action == null){
			System.out.println(mapkey+" URL ���Ͽ� ���ε� ��ü�� �����ϴ�.");
		}else{
			 try {
				action.execute(request, response);//�޼ҵ� ȣ��
				boolean bool = action.isRedirect();
				//private���� �����Ǿ��ֱ⶧���� ���ٺҰ���-> ��ȸ�� public boolean�� ���� isRedirect��
				//�����ؼ� forward���� redirect���� �˾ƺ�
				
				String viewPage = action.getViewPage();//���� ������(�о�;���) view�� ������
				
				if(bool){
					//sendRedirect������� view�� ������ �̵�
					response.sendRedirect(viewPage);//�������̵�
				}else{
					//forward���  view�� ������ �̵�
					RequestDispatcher dispatcher=request.getRequestDispatcher(viewPage);//setViewPage�� �����ͼ� �Ѱ��ش�.
					dispatcher.forward(request, response);
					
				}
				
			 } catch (Exception e) {
				e.printStackTrace();
			 }
		}
	}//requestProcess(HttpServletRequest request, HttpServletResponse response)
	
	
}
