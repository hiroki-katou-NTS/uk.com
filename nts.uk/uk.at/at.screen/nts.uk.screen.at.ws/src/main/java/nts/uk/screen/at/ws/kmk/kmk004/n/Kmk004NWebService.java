package nts.uk.screen.at.ws.kmk.kmk004.n;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetemp.SaveMonthlyWorkTimeSetEmpCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.n.DeleteTransMonthlyWorkTimeSetEmpCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.n.DeleteTransMonthlyWorkTimeSetEmpCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.n.RegisterTransMonthlyWorkTimeSetEmpCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.n.UpdateTransMonthlyWorkTimeSetEmpCommandHandler;
import nts.uk.screen.at.app.query.kmk004.common.YearDto;
import nts.uk.screen.at.app.query.kmk004.common.YearListByEmployment;

/**
 * 
 * @author tutt
 *
 */
@Path("screen/at/kmk004")
@Produces("application/json")
public class Kmk004NWebService {

	@Inject
	private YearListByEmployment yearListByEmployment;
	
	@Inject
	private RegisterTransMonthlyWorkTimeSetEmpCommandHandler registerHandler;
	
	@Inject
	private UpdateTransMonthlyWorkTimeSetEmpCommandHandler updateHandler;
	
	@Inject
	private DeleteTransMonthlyWorkTimeSetEmpCommandHandler deleteHandler;

	
	@POST
	@Path("viewN/getListYear/{empCode}")
	public List<YearDto> getEmpYearList(@PathParam("empCode") String empCode) {
		return yearListByEmployment.get(empCode, LaborWorkTypeAttr.DEFOR_LABOR);
	}
	
	@POST
	@Path("viewM/monthlyWorkTimeSet/add")
	public void registerMonthlyWorkTimeSet(CommandHandlerContext<SaveMonthlyWorkTimeSetEmpCommand> command) {
		registerHandler.handle(command);
	}
	
	@POST
	@Path("viewM/monthlyWorkTimeSet/update")
	public void updateMonthlyWorkTimeSet(CommandHandlerContext<SaveMonthlyWorkTimeSetEmpCommand> command) {
		updateHandler.handle(command);
	}
	
	@POST
	@Path("viewM/monthlyWorkTimeSet/delete")
	public void deleteMonthlyWorkTimeSet(CommandHandlerContext<DeleteTransMonthlyWorkTimeSetEmpCommand> command) {
		deleteHandler.handle(command);
	}
}
