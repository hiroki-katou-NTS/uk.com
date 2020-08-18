package nts.sample.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import nts.arc.security.csrf.CsrfToken;

@Path("/test/csrf/")
public class CsrfTokenPublishApi {

	@GET
	@Path("publish")
	public void publish() {
		CsrfToken.loggedIn();
	}
	
}
