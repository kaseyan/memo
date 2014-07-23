package ksd.memo.controller;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import ksd.common.manager.ManagerOperator;

/**
 * Application Lifecycle Listener implementation class memoHttpSessionListener
 *
 */
@WebListener
public class WsMemoHttpSessionListener implements HttpSessionListener {

	ManagerOperator mo = new ManagerOperator();

    public WsMemoHttpSessionListener() {}

    public void sessionCreated(HttpSessionEvent hse) {
    	System.out.println("memoHttpSessionListener#sessionCreated() called.");
    	mo.httpSessionCreated(hse.getSession());
    }

    public void sessionDestroyed(HttpSessionEvent hse) {
    	System.out.println("memoHttpSessionListener#sessionDestroyed() called.");
    	mo.closeHttpSession(hse.getSession().getId());
    }

}
