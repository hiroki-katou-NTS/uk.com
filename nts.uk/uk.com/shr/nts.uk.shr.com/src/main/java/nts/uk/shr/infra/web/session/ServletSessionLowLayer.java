package nts.uk.shr.infra.web.session;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import nts.arc.layer.ws.ProducedRequest;
import nts.uk.shr.com.context.loginuser.SessionLowLayer;

@Stateless
public class ServletSessionLowLayer implements SessionLowLayer {

	private static final String LOGGED_IN_FLAG = "ServletSessionLowLayer.LOGGED_IN_FLAG";
	
	@Inject @ProducedRequest
	private HttpServletRequest request;

	@Override
	public void loggedIn() {
		this.getSession().setAttribute(LOGGED_IN_FLAG, true);
	}

	@Override
	public void loggedOut() {
		this.getSession().removeAttribute(LOGGED_IN_FLAG);
		this.getSession().invalidate();
	}
	
	@Override
	public boolean isLoggedIn() {
		Object flag = this.getSession().getAttribute(LOGGED_IN_FLAG);
		return flag != null && (boolean)flag == true;
	}
	
	private HttpSession getSession() {
		return this.request.getSession(false);
	}
}
