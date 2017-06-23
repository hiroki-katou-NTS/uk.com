package nts.uk.ctx.at.shared.ws.bonuspay;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.BPTimesheetAddCommand;
import nts.uk.ctx.at.shared.app.command.BPTimesheetAddCommandHandler;
import nts.uk.ctx.at.shared.app.command.BPTimesheetDeleteCommand;
import nts.uk.ctx.at.shared.app.command.BPTimesheetDeleteCommandHandler;
import nts.uk.ctx.at.shared.app.command.BPTimesheetUpdateCommand;
import nts.uk.ctx.at.shared.app.command.BPTimesheetUpdateCommandHandler;
import nts.uk.ctx.at.shared.app.find.bonuspay.BPTimesheetDto;
import nts.uk.ctx.at.shared.app.find.bonuspay.BPTimesheetFinder;

@Path("at/share/bpTimesheet")
@Produces("application/json")
public class BPTimesheetWebService extends WebService {
	@Inject
	private BPTimesheetFinder bpTimesheetFinder;
	@Inject
	private BPTimesheetAddCommandHandler bpTimesheetAddCommandHandler;
	@Inject
	private BPTimesheetDeleteCommandHandler cpTimesheetDeleteCommandHandler;
	@Inject
	private BPTimesheetUpdateCommandHandler cpTimesheetUpdateCommandHandler;

	@POST
	@Path("getListTimesheet/{companyId}/{bonusPaySettingCode}")
	List<BPTimesheetDto> getListTimesheet(@PathParam("companyId") String companyId,
			@PathParam("bonusPaySettingCode") String bonusPaySettingCode) {
		return this.bpTimesheetFinder.getListTimesheet(companyId, bonusPaySettingCode);
	}
	@POST
	@Path("addListTimesheet")
	void addListTimesheet(List<BPTimesheetAddCommand> lstTimesheet) {
		this.bpTimesheetAddCommandHandler.handle(lstTimesheet);
	}
	@POST
	@Path("updateListTimesheet")
	void updateListTimesheet(List<BPTimesheetUpdateCommand> lstTimesheet) {
		this.cpTimesheetUpdateCommandHandler.handle(lstTimesheet);
	}
	@POST
	@Path("removeListTimesheet")
	void removeListTimesheet(List<BPTimesheetDeleteCommand> lstTimesheet) {
		this.cpTimesheetDeleteCommandHandler.handle(lstTimesheet);
	}

}
