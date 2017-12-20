package nts.uk.ctx.at.record.ws.dailyperformanceformat;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.command.dailyperformanceformat.UpdateAttdItemAuthCommand;
import nts.uk.ctx.at.record.app.command.dailyperformanceformat.UpdateAttdItemAuthCommandHandler;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.DailyAttdItemAuthDto;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.DailyAttdItemAuthFinder;

@Path("at/record/DailyAttdItemAuth")
@Produces("application/json")
public class DailyAttdItemAuthWebService {
	@Inject
	private DailyAttdItemAuthFinder dailyAttdItemAuthFinder;
	@Inject
	private UpdateAttdItemAuthCommandHandler updateAttdItemAuthCommandHandler;

	@POST
	@Path("getListDailyAttendanceItemAuthority/{authorityId}")
	public List<DailyAttdItemAuthDto> getListDailyAttendanceItemAuthority(@PathParam("authorityId") String authorityId) {
			return this.dailyAttdItemAuthFinder.getListDailyAttendanceItemAuthority(authorityId);
	}
	@POST
	@Path("UpdateListDailyAttendanceItemAuthority")
	public void UpdateListDailyAttendanceItemAuthority(List<UpdateAttdItemAuthCommand> lstUpdateAttdItemAuthCommand){
		this.updateAttdItemAuthCommandHandler.handle(lstUpdateAttdItemAuthCommand);
		
	}
}
