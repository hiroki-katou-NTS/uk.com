package nts.uk.ctx.at.shared.ws.bonuspay;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.bonuspay.BPTimesheetAddCommand;
import nts.uk.ctx.at.shared.app.command.bonuspay.BPTimesheetAddCommandHandler;
import nts.uk.ctx.at.shared.app.command.bonuspay.BPTimesheetDeleteCommand;
import nts.uk.ctx.at.shared.app.command.bonuspay.BPTimesheetDeleteCommandHandler;
import nts.uk.ctx.at.shared.app.command.bonuspay.BPTimesheetUpdateCommand;
import nts.uk.ctx.at.shared.app.command.bonuspay.BPTimesheetUpdateCommandHandler;
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
	@Path("getListTimesheet/{bonusPaySettingCode}")
	public List<BPTimesheetDto> getListTimesheet(
			@PathParam("bonusPaySettingCode") String bonusPaySettingCode) {
		return this.bpTimesheetFinder.getListTimesheet( bonusPaySettingCode);
	}
	@POST
	@Path("checkUseArt")
	public void checkUseArt(List<Boolean> lstuseArt){
		this.bpTimesheetFinder.checkUseArt(lstuseArt);
	}
	@POST
	@Path("addListTimesheet")
	public void addListTimesheet(List<BPTimesheetAddCommand> lstTimesheet) {
		this.bpTimesheetAddCommandHandler.handle(lstTimesheet);
	}
	@POST
	@Path("updateListTimesheet")
	public void updateListTimesheet(List<BPTimesheetUpdateCommand> lstTimesheet) {
		this.cpTimesheetUpdateCommandHandler.handle(lstTimesheet);
	}
	@POST
	@Path("removeListTimesheet")
	public void removeListTimesheet(List<BPTimesheetDeleteCommand> lstTimesheet) {
		this.cpTimesheetDeleteCommandHandler.handle(lstTimesheet);
	}

}
