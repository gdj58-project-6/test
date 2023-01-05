package controller.member.log;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.CustomerService;
import vo.Customer;

@WebServlet("/member/memberOne")
public class MemberOneController extends HttpServlet {
	private CustomerService customerService;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 회원정보 view
		
		// 현재 로그인 정보
		HttpSession session = request.getSession();
		Customer loginCustomer = (Customer)(session.getAttribute("loginCustomer"));
		
		// 로그인 안되어있으면
		String memberId = loginCustomer.getCustomerId();
				
		Customer customer = new Customer();
		customer.setCustomerId(memberId);
		
		// Model
		this.customerService = new CustomerService();
		
		
		request.getRequestDispatcher("/WEB-INF/view/member/log/memberOne.jsp").forward(request, response);
	}

}
