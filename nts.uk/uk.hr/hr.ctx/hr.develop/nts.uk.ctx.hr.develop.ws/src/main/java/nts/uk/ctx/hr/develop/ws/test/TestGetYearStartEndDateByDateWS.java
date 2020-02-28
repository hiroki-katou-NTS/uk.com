package nts.uk.ctx.hr.develop.ws.test;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.hr.develop.app.humanresourcedev.hryear.TestGetYearStartEndDateByDateFinder;
import nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear.service.YearStartEnd;

@Path("getDate")
@Produces(MediaType.APPLICATION_JSON)
public class TestGetYearStartEndDateByDateWS {
	
	@Inject
	private TestGetYearStartEndDateByDateFinder finder;
	@POST
	@Path("/YearStartEnd")
	public YearStartEnd getDate(ParamDate param){
		YearStartEnd data = finder.getByDate(param.companyId, param.getBaseDate()).orElse(null);
		return data;
	}
}
