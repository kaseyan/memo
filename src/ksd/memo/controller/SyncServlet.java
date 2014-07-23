package ksd.memo.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/sync")
public class SyncServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SyncServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String command = req.getParameter("command");
		ServletContext sc = getServletContext();
		if ("login".equals(command)) {
			sc.getRequestDispatcher("/dir/top.jsp").forward(req, res);
		} else if ("memo".equals(command)) {
			sc.getRequestDispatcher("/page/page.jsp").forward(req, res);
		}
	}

}
