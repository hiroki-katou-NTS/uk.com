package nts.uk.ctx.at.shared.ws.remaingnumber;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.remainingnumber.subhdmana.DeleteLeaveManagementDataCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.subhdmana.LeaveManagementDataCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.subhdmana.UpdateLeaveManagementDataCommandHandler;

/**
 * 代休管理データの修正（休出設定）
 * 
 * @author sang.nv
 *
 */
@Path("at/record/remainnumber/submana/holidaysetting")
@Produces("application/json")
public class HolidaySettingWebService extends WebService {

	@Inject
	private UpdateLeaveManagementDataCommandHandler updateLeaveManaHandler;

	@Inject
	private DeleteLeaveManagementDataCommandHandler deleteLeaveManaHandler;

	@POST
	@Path("update")
	public List<String> updateComDayOffMana(LeaveManagementDataCommand command) {
		return this.updateLeaveManaHandler.handle(command);
	}

	@POST
	@Path("delete")
	public void deleteComDayOffMana(LeaveManagementDataCommand command) {
		this.deleteLeaveManaHandler.handle(command);
	}
}
