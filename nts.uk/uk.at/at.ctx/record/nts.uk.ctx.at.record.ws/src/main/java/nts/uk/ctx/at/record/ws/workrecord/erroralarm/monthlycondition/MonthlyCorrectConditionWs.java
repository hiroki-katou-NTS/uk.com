/**
 * 10:28:53 AM Mar 29, 2018
 */
package nts.uk.ctx.at.record.ws.workrecord.erroralarm.monthlycondition;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.command.workrecord.erroralarm.monthlycondition.DeleteMonthlyConCmdHandler;
import nts.uk.ctx.at.record.app.command.workrecord.erroralarm.monthlycondition.UpdateMonthlyCorrectConCmd;
import nts.uk.ctx.at.record.app.command.workrecord.erroralarm.monthlycondition.UpdateMonthlyCorrectConCmdHandler;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.monthlycondition.MonthlyCorrectConditionDto;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.monthlycondition.MonthlyCorrectConditionFinder;

/**
 * @author hungnm
 *
 */
@Path("ctx/at/record/workrecord/erroralarm/monthly")
@Produces("application/json")
public class MonthlyCorrectConditionWs {

	@Inject
	private MonthlyCorrectConditionFinder finder;

	@Inject
	private UpdateMonthlyCorrectConCmdHandler updateCommandHandler;

	@Inject
	private DeleteMonthlyConCmdHandler deleteErAlCommandHandler;
	
	@POST
	@Path("getall")
	public List<MonthlyCorrectConditionDto> getAll() {
		return this.finder.findAllMonthlyCorrectCondition();
	}
	
	@POST
	@Path("findbycheckid/{checkId}/{errorCode}")
	public MonthlyCorrectConditionDto findByCheckId(@PathParam("checkId") String checkId, @PathParam("errorCode") String errorCode) {
		return this.finder.getMonthlyCorrectConditionDto(checkId, errorCode);
	}

	@POST
	@Path("update")
	public void update(UpdateMonthlyCorrectConCmd command) {
		this.updateCommandHandler.handle(command);
	}

	@POST
	@Path("remove")
	public void remove(String code) {
		this.deleteErAlCommandHandler.handle(code);
	}

}
