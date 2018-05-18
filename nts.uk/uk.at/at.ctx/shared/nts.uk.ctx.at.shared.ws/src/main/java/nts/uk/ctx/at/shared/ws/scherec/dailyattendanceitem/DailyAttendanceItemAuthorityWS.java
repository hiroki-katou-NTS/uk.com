package nts.uk.ctx.at.shared.ws.scherec.dailyattendanceitem;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.scherec.dailyattendanceitem.DailyAttendanceItemAuthorityCmd;
import nts.uk.ctx.at.shared.app.command.scherec.dailyattendanceitem.DailyAttendanceItemAuthorityCmdHandler;
import nts.uk.ctx.at.shared.app.find.scherec.dailyattendanceitem.DailyAttendanceItemAuthorityDto;
import nts.uk.ctx.at.shared.app.find.scherec.dailyattendanceitem.DailyAttendanceItemAuthorityFinder;

@Path("at/shared/scherec/dailyattditem/auth")
@Produces("application/json")
public class DailyAttendanceItemAuthorityWS extends WebService {

	@Inject
	private DailyAttendanceItemAuthorityFinder finder;
	
	@Inject
	private DailyAttendanceItemAuthorityCmdHandler dailyAttdHandler;
	
	
	@POST
	@Path("getdailyattd/{roleid}")
	public DailyAttendanceItemAuthorityDto getDailyAttdItemByRoleID(@PathParam("roleid") String roleID) {
		DailyAttendanceItemAuthorityDto data = finder.getDailyAttdItemByRoleID(roleID);
		return data;
	}
	
	@POST
	@Path("updatedailyattd")
	public void updateDailyAttd(DailyAttendanceItemAuthorityCmd command) {
		this.dailyAttdHandler.handle(command);
	}
	
}
