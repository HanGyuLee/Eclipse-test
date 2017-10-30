package common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexController extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//메인홈페이지구현
		
		 req.setAttribute("msg", "WELCOME TO <span style='color:orange;'>GYURI'S WORLD</span> :D ");
		 req.setAttribute("mymsg", "CARPE DIEM♬ ");
		 super.setRedirect(false);
		 super.setViewPage("/WEB-INF/index.jsp");
		
	}

}
