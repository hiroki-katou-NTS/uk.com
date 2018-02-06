package nts.uk.ctx.pereg.ws.common;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pereg.app.find.common.CheckEnableParam;

@Path("ctx/pereg/person/common")
@Produces("application/json")
public class CheckEnableWebService extends WebService{
	
	@POST
	@Path("checkStartEnd")
	public boolean checkStartDateAndEndDate(CheckEnableParam param) {
		if ( param.getWorkTimeCode() != null ) {
			return true;
		} else {
			return false;
		}
	}
	
	@POST
	@Path("checkMultiTime")
	public boolean checkMultiTime(CheckEnableParam param) {
		if ( param.getWorkTimeCode() != null ) {
			return true;
		} else {
			return false;
		}
	}
}
