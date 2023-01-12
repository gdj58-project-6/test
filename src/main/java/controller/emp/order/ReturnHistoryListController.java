package controller.emp.order;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.ReturnHistoryService;
import vo.Emp;

@WebServlet("/admin/returnHistoryList")
public class ReturnHistoryListController extends HttpServlet {
	private ReturnHistoryService returnHistoryService;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 정보 저장
		HttpSession session = request.getSession();
		Emp loginMember = (Emp)(session.getAttribute("loginMember"));
		
		// 로그인이 안되어있으면
		if(loginMember == null) {
			response.sendRedirect(request.getContextPath() + "/home");
			return;
		}
		
		// 로그인되어있으면
		this.returnHistoryService = new ReturnHistoryService();
		ArrayList<HashMap<String, Object>> list = returnHistoryService.getSelectReturnHistoryList();
		
		
		request.setAttribute("list", list);
		request.getRequestDispatcher("/WEB-INF/view/emp/order/returnHistoryList.jsp").forward(request, response);
	}
}
