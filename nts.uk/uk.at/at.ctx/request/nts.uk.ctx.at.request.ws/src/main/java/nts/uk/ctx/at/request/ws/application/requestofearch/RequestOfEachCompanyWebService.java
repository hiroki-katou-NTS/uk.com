package nts.uk.ctx.at.request.ws.application.requestofearch;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.setting.workplace.RequestOfEachCompanyDto;
import nts.uk.ctx.at.request.app.find.setting.workplace.RequestOfEachCompanyFinder;

@Path("at/request/application/requestofearch")
@Produces("application/json")
public class RequestOfEachCompanyWebService extends WebService{
	@Inject
	private RequestOfEachCompanyFinder requestFinder;
	
	@POST
	@Path("getrequestofearch")
	public RequestOfEachCompanyDto getRequest() {
		return this.requestFinder.findRequest();
	}
}
