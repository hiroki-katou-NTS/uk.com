package nts.uk.ctx.at.shared.ws.bonuspay;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.bonuspay.SpecBPTimesheetAddCommand;
import nts.uk.ctx.at.shared.app.command.bonuspay.SpecBPTimesheetAddCommandHandler;
import nts.uk.ctx.at.shared.app.command.bonuspay.SpecBPTimesheetDeleteCommand;
import nts.uk.ctx.at.shared.app.command.bonuspay.SpecBPTimesheetDeleteCommandHandler;
import nts.uk.ctx.at.shared.app.command.bonuspay.SpecBPTimesheetUpdateCommand;
import nts.uk.ctx.at.shared.app.command.bonuspay.SpecBPTimesheetUpdateCommandHandler;
import nts.uk.ctx.at.shared.app.find.bonuspay.SpecBPTimesheetDto;
import nts.uk.ctx.at.shared.app.find.bonuspay.SpecBPTimesheetFinder;

@Path("at/share/specBonusPayTimesheet")
@Produces("application/json")
public class SpecBonusPayTimesheetWebService extends WebService {
	@Inject
	private SpecBPTimesheetFinder specBPTimesheetFinder;
	@Inject
	private SpecBPTimesheetAddCommandHandler specBPTimesheetAddCommandHandler;
	@Inject
	private SpecBPTimesheetDeleteCommandHandler specBPTimesheetDeleteCommandHandler;
	@Inject
	private SpecBPTimesheetUpdateCommandHandler specBPTimesheetUpdateCommandHandler;

	@POST
	@Path("getListTimesheet/{bonusPaySettingCode}")
	public List<SpecBPTimesheetDto> getListTimesheet(
			@PathParam("bonusPaySettingCode") String bonusPaySettingCode) {
		return this.specBPTimesheetFinder.getListTimesheet(bonusPaySettingCode);
	}

	@POST
	@Path("addListTimesheet")
	public void addListTimesheet(List<SpecBPTimesheetAddCommand> lstSpecTimesheet) {
		this.specBPTimesheetAddCommandHandler.handle(lstSpecTimesheet);
	}

	@POST
	@Path("updateListTimesheet")
	public void updateListTimesheet(List<SpecBPTimesheetUpdateCommand> lstSpecTimesheet) {
		this.specBPTimesheetUpdateCommandHandler.handle(lstSpecTimesheet);

	}
	@POST
	@Path("removeListTimesheet")
	public void removeListTimesheet(List<SpecBPTimesheetDeleteCommand> lstSpecTimesheet) {
		this.specBPTimesheetDeleteCommandHandler.handle(lstSpecTimesheet);
	}

}
