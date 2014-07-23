package ksd.memo.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ksd.memo.data.Consts;
import ksd.memo.data.Message;
import ksd.memo.manager.ApplicationOperator;

/**
 * Servlet implementation class DocServlet
 */
@WebServlet("/memo")
public class WsMemoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public WsMemoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {

		System.out.println("doGet called.");

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		System.out.println("doPost called.");
		String command = req.getParameter("command");
		System.out.println(command);
		Message m = null;

		if (Consts.COMMAND_LOCK.equals(command)) {
			System.out.println("doPost#lock called.");
			String lineId = req.getParameter("lineId");
			String pageId = req.getParameter("pageId");
			m = ApplicationOperator.lockLine(pageId, lineId, req.getSession().getId());

		} else if (Consts.COMMAND_UNLOCK.equals(command)) {
			System.out.println("doPost#unlock called.");
			String lineId = req.getParameter("lineId");
			String pageId = req.getParameter("pageId");
			m = ApplicationOperator.unlockLine(pageId, lineId, req.getSession().getId());

		} else if (Consts.COMMAND_ON_EDIT.equals(command)) {
			System.out.println("doPost#on_edit called.");

		} else if (Consts.COMMAND_FIXED.equals(command)) {
			System.out.println("doPost#fixed called.");
			String data = req.getParameter("data");
			String lineId = req.getParameter("lineId");
			String pageId = req.getParameter("pageId");

			m = ApplicationOperator.fix(pageId, lineId,data, req.getSession().getId());
		}

		if (m != null) {
			res.setCharacterEncoding("utf-8");
			res.setContentType("text/json; charset=utf-8");
			PrintWriter out = res.getWriter();
			out.println(m.toJson());
			out.flush();
		} else {
			System.err.println("Message null.");
		}
	}

}
