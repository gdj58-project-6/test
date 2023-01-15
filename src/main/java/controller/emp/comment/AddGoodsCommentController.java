package controller.emp.comment;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.CommentService;
import vo.Customer;
import vo.GoodsQuestionComment;

/**
 * Servlet implementation class AddCommentController
 */
@WebServlet("/admin/addGoodsComment")
public class AddGoodsCommentController extends HttpServlet {
	private CommentService commentService;
	// 답글 입력폼
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int goodsQuestionCode = Integer.parseInt(request.getParameter("goodsQuestionCode"));
		String goodsQuestionMemo = request.getParameter("goodsQuestionMemo");
		// System.out.println(goodsQuestionCode);
		// System.out.println(goodsQuestionMemo);
		HttpSession session = request.getSession();
		// 비로그인이거나 직원이 아니면 접근불가
		if(session.getAttribute("loginMember") == null || session.getAttribute("loginMember") instanceof Customer) {
			response.sendRedirect(request.getContextPath() + "/home");
			return;
		}
		
		request.setAttribute("code", goodsQuestionCode);
		request.setAttribute("memo", goodsQuestionMemo);
		
		
		request.getRequestDispatcher("/WEB-INF/view/emp/comment/addGoodsComment.jsp").forward(request, response);
	}
	// 답글 입력액션
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int goodsQuestionCode = Integer.parseInt(request.getParameter("goodsQuestionCode"));
		String goodsCommentMemo = request.getParameter("goodsCommentMemo");
		// System.out.println(goodsQuestionCode);
		// System.out.println(goodsCommentMemo);
		
		// 메서드 호출에 필요한 매개값
		GoodsQuestionComment g = new GoodsQuestionComment();
		g.setGoodsQuestionCode(goodsQuestionCode);
		g.setGoodsCommentMemo(goodsCommentMemo);
		
		// 메서드 호출
		this.commentService = new CommentService();
		int addGoodsComment = commentService.addGoodsQuestionComment(g);
		
		if(addGoodsComment == 1) {
			System.out.println("입력완료");
			response.sendRedirect(request.getContextPath() + "/admin/goodsComment");
		}
	}

}
