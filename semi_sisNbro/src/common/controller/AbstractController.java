package common.controller;
//부모클래스 /// 재정의는 하지않되, 공통으로 쓸것적어줌
public abstract class AbstractController 
	   implements Command{
	
	private boolean isRedirect = false;
	
	/*
		우리끼리 약속인데
		뷰단 페이지(JSP페이지)로 이동시
		redirect방법(데이터전달은 없고, 단순히 페이지 이동만 발생)으로
		이동시키고자 한다면
		isRedirect 변수의 값을 true로 하겠다.
		(isRedirect이 기준)
		
		뷰단 페이지(JSP페이지)로 이동시
		redirect방법(데이터전달과함께 페이지 이동만 발생)으로
		이동시키고자 한다면
		isRedirect변수의 값을 false로 하겠다.
	*/
	
	private String viewPage;
	//뷰단 페이지(jsp페이지) 의 경로명으로 사용되어지는 변수이다.

	public boolean isRedirect() {
		return isRedirect;
	}//리턴타입이 boolean이라면  get이 아니라 is로 나타난다.(get없음)

	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}

	public String getViewPage() {
		return viewPage;
	}

	public void setViewPage(String viewPage) {
		this.viewPage = viewPage;
	}
	
	

}
