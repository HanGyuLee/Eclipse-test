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
	//어떤 객체이던 모든지 다 hashMap에 넣어준다.
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		/*
		  웹브라우저 주소차엥서 *.do를 하면 FrontController 서블릿이 받는데
		  맨처음에 자동적으로 실행되어지는 메소드가 init(ServletConfig config) 이다
		  그런데 이 메소드는 WAS(==웹컨테이너, 톰캣서버)가 구동되어진 후 
		  딱 1번만 수행되어지고, 그 이후에는 수행되지 않는다.
		  그러므로 FrontController서블릿을 수행할때 딱 1번만 수행해야할 업무들은
		  이 메소드속에 기술해 주면 된다.
		 */
		//System.out.println("==>확인용 :FrontController서블릿의 init(ServletConfig config)메소드가 작동함.");
		
		String props = config.getInitParameter("propertyConfig");
		
		/*
			초기화 파라미터 데이터 값인 "C:/myjsp/MyMVC/WebContent/WEB-INF/Command.properties"을 가져와서 
			문자열 변수 props에 저장시켜둔 것이다.
		*/
		//System.out.println("==>확인용 : props변수에 저장된 값 => " + props);
		//결과 : ==>확인용 : props변수에 저장된 값 => C:/myjsp/MyMVC/WebContent/WEB-INF/Command.properties
		
		Properties pr = new Properties();
		
		FileInputStream fis = null;
		
		try {
			 fis = new FileInputStream(props);
			 
			 pr.load(fis);
			 /*
			     C:/myjsp/MyMVC/WebContent/WEB-INF/Command.properties 파일의 내용을 읽어다가
			     Properties클래스의 객체인 pr에 로드시킨다.
			          그러면 pr은 읽어온 파일(Command.properties)의 내용에서
			     = 을 기준으로 왼쪽은 키값으로 보고, 오른쪽은 value값으로 인식한다.
			  */
			 
			//String str_class =  pr.getProperty("/test1.do");
			/*
			    pr.getProperty("/test1.do");에서 /test1.do은 key값이다.
			     pr.getProperty("/test1.do"); 의 리턴값은 
			     Command.properties에 기술된 key값 /test1.do 에 해당하고
			     value값 test.controller.Test1Controller을 리턴해준다.
			   
			 */
			// System.out.println("확인용 str_class에 저장된 값 " + str_class);
			 //확인용 str_class에 저장된 값 test.controller.Test1Controller
			 
			 
			 Enumeration<Object> en = pr.keys();
			 /*
			    pr.keys(); 은
			    Command.properties 파일의 내용물에서 
			    = 을 기준으로 왼쪽에 있는 모든 key값들만
			       읽어오는 것이다.
			    
			  */
			 while(en.hasMoreElements()){
				 String key_urlname = (String)en.nextElement();//다음에 element가 있으면 읽어준다.
				 
				 String str_classname = pr.getProperty(key_urlname);
				 
			 //System.out.println("==>확인용 : key_urlname=>" + key_urlname);
			 /*	==>확인용 : key_urlname=>/test3.do
			  	==>확인용 : key_urlname=>/test2.do
				==>확인용 : key_urlname=>/test1.do
			*/
				 
				//System.out.println("==>확인용 :str_classname=>" + str_classname);
				/*
				 ==>확인용 :str_classname=>test.controller.Test3Controller
				 ==>확인용 :str_classname=>test.controller.Test2Controller
				 ==>확인용 :str_classname=>test.controller.Test1Controller
				 */
				 
				 if(str_classname != null){
					 str_classname=str_classname.trim();//공백제거후 다시 변수에 넣기
					 Class cls = Class.forName(str_classname);//class일뿐 객체는 아니다.
					 Object obj = cls.newInstance(); //문자열을 읽어 class화 해서 그 class를 객체로 만들어준다.
					 //어떤걸 받아야 할지 모르니까 Object로 받아온다.(모든것을 다 받아줌)
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
	doGet메소드와  doPost메소드에서 해야할 업무처리를 
	requestProcess(request, response); 이 메소드로 넘겨버린다.

*/
	private void requestProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {//doGet인지 doPost인지 모르니까 걍 다 받아주는 메소드 하나 만듬
		
		String uri = request.getRequestURI();
		 //System.out.println("확인용 uri : "+uri);
		 //확인용 uri : /MyMVC/test1.do
		/*
		  request.getRequestURI(); 메소드는 웹브라우저 주소창에서 요청되어진 URI 값을 반환시켜주는 메소드이다.
		  URI라 함은 URL이 http://ip주소:9090/URI 값인데
		  http://ip주소:9090을 제외한 나머지를 말한다.
		    그런데 나머지 중에 ?(GET방식)이전까지를 말한다.
		 */
		
		String ctxName = request.getContextPath();
		//System.out.println("==>확인용  ctxName:"+ctxName);
		//==>확인용  ctxName:/MyMVC
		int beginIndex = ctxName.length();
		String mapkey = uri.substring(beginIndex);
		//System.out.println("=>확인용 mapKey :"+mapkey);
		//=>확인용 mapKey :/test2.do
		
		AbstractController action = (AbstractController)cmdMap.get(mapkey);
		if(action == null){
			System.out.println(mapkey+" URL 패턴에 매핑된 객체가 없습니다.");
		}else{
			 try {
				action.execute(request, response);//메소드 호출
				boolean bool = action.isRedirect();
				//private으로 설정되어있기때문에 접근불가능-> 우회로 public boolean의 값인 isRedirect로
				//접근해서 forward인지 redirect인지 알아봄
				
				String viewPage = action.getViewPage();//내가 보여줄(읽어와야할) view단 페이지
				
				if(bool){
					//sendRedirect방식으로 view단 페이지 이동
					response.sendRedirect(viewPage);//페이지이동
				}else{
					//forward방식  view단 페이지 이동
					RequestDispatcher dispatcher=request.getRequestDispatcher(viewPage);//setViewPage를 가져와서 넘겨준다.
					dispatcher.forward(request, response);
					
				}
				
			 } catch (Exception e) {
				e.printStackTrace();
			 }
		}
	}//requestProcess(HttpServletRequest request, HttpServletResponse response)
	
	
}
