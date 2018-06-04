package nts.uk.ctx.at.shared.ws.remaingnumber;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.remainingnumber.subhdmana.CompensatoryDayOffManaDataCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.subhdmana.DeleteComDayOffManaCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.subhdmana.UpdateComDayOffManaCommandHandler;

/**
 * 代休管理データの修正（代休設定）
 * 
 * @author sang.nv
 *
 */
@Path("at/record/remainnumber/submana/comdayoff")
@Produces("application/json")
public class SubstituteHolidaySettingWebService extends WebService {

	@Inject
	private UpdateComDayOffManaCommandHandler udpateComDayCommand;

	@Inject
	private DeleteComDayOffManaCommandHandler deleteComDayCommand;

	@POST
	@Path("update")
	public List<String> updateComDayOffMana(CompensatoryDayOffManaDataCommand command) {
		return this.udpateComDayCommand.handle(command);
	}

	@POST
	@Path("delete")
	public void deleteComDayOffMana(CompensatoryDayOffManaDataCommand command) {
		this.deleteComDayCommand.handle(command);
	}
}
