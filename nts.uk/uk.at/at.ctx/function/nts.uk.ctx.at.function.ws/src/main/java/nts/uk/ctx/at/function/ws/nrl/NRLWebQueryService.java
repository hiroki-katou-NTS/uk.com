package nts.uk.ctx.at.function.ws.nrl;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import nts.uk.ctx.at.function.app.nrwebquery.NRWebQueryDispatcher;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQueryMenuName;

@Path("/nr/process/query")
public class NRLWebQueryService {

	@Inject
	private NRWebQueryDispatcher nrWebQueryDispatcher;

	@POST
	@Path("nrl1refmenu")
	public Response menu(@Context UriInfo queryParam) {
		return nrWebQueryDispatcher.process(queryParam.getQueryParameters(), NRWebQueryMenuName.MENU);
	}

	@POST
	@Path("nrl1refsche")
	public Response schedule(@Context UriInfo queryParam) {
		return nrWebQueryDispatcher.process(queryParam.getQueryParameters(), NRWebQueryMenuName.SCHEDULE);
	}

	@POST
	@Path("nrl1refday")
	public Response dailyrecord(@Context UriInfo queryParam) {
		return nrWebQueryDispatcher.process(queryParam.getQueryParameters(), NRWebQueryMenuName.DAILY);
	}

	@POST
	@Path("nrl1refmonth")
	public Response monthly(@Context UriInfo queryParam) {
		return nrWebQueryDispatcher.process(queryParam.getQueryParameters(), NRWebQueryMenuName.MONTHLY);
	}

	@POST
	@Path("nrl1refsinsei")
	public Response application(@Context UriInfo queryParam) {
		return nrWebQueryDispatcher.process(queryParam.getQueryParameters(), NRWebQueryMenuName.APPLICATION);
	}

	@POST
	@Path("l1refmonmoney")
	public Response monthmoney(@Context UriInfo queryParam) {
		return nrWebQueryDispatcher.process(queryParam.getQueryParameters(), NRWebQueryMenuName.MONTH_WAGE);
	}

	@POST
	@Path("l1refyearmoney")
	public Response yearmoney(@Context UriInfo queryParam) {
		return nrWebQueryDispatcher.process(queryParam.getQueryParameters(), NRWebQueryMenuName.ANNUAL_WAGE);
	}
}
