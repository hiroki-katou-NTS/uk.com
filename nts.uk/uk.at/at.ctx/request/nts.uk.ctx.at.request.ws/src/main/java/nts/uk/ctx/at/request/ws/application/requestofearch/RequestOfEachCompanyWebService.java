package nts.uk.ctx.at.request.ws.application.requestofearch;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;

@Path("at/request/application/requestofearch")
@Produces("application/json")
public class RequestOfEachCompanyWebService extends WebService{
//	@Inject
//	private RequestOfEachCompanyFinder requestFinder;
//	
//	@POST
//	@Path("getrequestofearch")
//	public RequestOfEachCompanyDto getRequest() {
//		return this.requestFinder.findRequest();
//	}
}
