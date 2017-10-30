package common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//servlet은 하나! 각 기능에 맞게 class가 정해지고, servlet으로 넘김(즉, servlet처럼 행동하지만 servlet은 아니다.) 따라서 request와 response가 필요하다.
/*
  	 인터페이스  ==> 멤버로 추상메소드와 상수만 갖는다.
  	 
  	// == 추상메소드 선언 ==
  	 * 1. 인터페이스에서 메소드 선언시 
  	  	   자동적으로 public abstract 지정자가 붙는다.
  	   
  	   2. 인터페이스에서 변수 선언시 
  	        자동으로 public static final 지정자가 붙으므로 
  	        상수변수가 된다.
 */

public interface Command {
//앞으로 나올 class의 부모클래스로쓰인다
	void execute(HttpServletRequest req, HttpServletResponse res)
	throws Exception;
	
}
