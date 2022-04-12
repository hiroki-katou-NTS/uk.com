package nts.uk.ctx.at.function.ws.nrl;

import java.io.InputStream;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import nts.uk.ctx.at.function.app.nrwebquery.NRWebQueryDispatcher;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQueryMenuName;

@Path("/nr/process/query")
public class NRLWebQueryService {

	@Inject
	private NRWebQueryDispatcher nrWebQueryDispatcher;
	
	@POST
	@Path("nrl1refmenu")
	public Response menu(InputStream is) {
		return nrWebQueryDispatcher.process(is, NRWebQueryMenuName.MENU);
	}

	@POST
	@Path("nrl1refsche")
	public Response schedule(InputStream is) {
		return nrWebQueryDispatcher.process(is, NRWebQueryMenuName.SCHEDULE);
	}

	@POST
	@Path("nrl1refday")
	public Response dailyrecord(InputStream is) {
		return nrWebQueryDispatcher.process(is, NRWebQueryMenuName.DAILY);
	}

	@POST
	@Path("nrl1refmonth")
	public Response monthly(InputStream is) {
		return nrWebQueryDispatcher.process(is, NRWebQueryMenuName.MONTHLY);
	}

	@POST
	@Path("nrl1refsinsei")
	public Response application(InputStream is) {
		return nrWebQueryDispatcher.process(is, NRWebQueryMenuName.APPLICATION);
	}

	@POST
	@Path("l1refmonmoney")
	public Response monthmoney(InputStream is) {
		return nrWebQueryDispatcher.process(is, NRWebQueryMenuName.MONTH_WAGE);
	}

	@POST
	@Path("l1refyearmoney")
	public Response yearmoney(InputStream is) {
		return nrWebQueryDispatcher.process(is, NRWebQueryMenuName.ANNUAL_WAGE);
	}
}
