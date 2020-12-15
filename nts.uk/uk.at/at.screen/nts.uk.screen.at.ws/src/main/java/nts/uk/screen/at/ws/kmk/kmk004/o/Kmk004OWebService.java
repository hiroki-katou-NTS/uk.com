package nts.uk.screen.at.ws.kmk.kmk004.o;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetsha.SaveMonthlyWorkTimeSetShaCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.o.DeleteTransMonthlyWorkTimeSetShaCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.o.DeleteTransMonthlyWorkTimeSetShaCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.o.RegisterTransMonthlyWorkTimeSetShaCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.o.UpdateTransMonthlyWorkTimeSetShaCommandHandler;
import nts.uk.screen.at.app.kmk004.o.DeforLaborMonthTimeShaDto;
import nts.uk.screen.at.app.kmk004.o.DisplayDeforBasicSettingByEmployee;
import nts.uk.screen.at.app.kmk004.o.SelectEmployeeDefor;
import nts.uk.screen.at.app.kmk004.o.SelectEmployeeDeforDto;
import nts.uk.screen.at.app.query.kmk004.common.YearDto;
import nts.uk.screen.at.app.query.kmk004.common.YearListByEmployee;

/**
 * 
 * @author tutt
 *
 */
@Path("screen/at/kmk004")
@Produces("application/json")
public class Kmk004OWebService {

	@Inject
	private YearListByEmployee yearListByEmployee;

	@Inject
	private RegisterTransMonthlyWorkTimeSetShaCommandHandler registerHandler;

	@Inject
	private UpdateTransMonthlyWorkTimeSetShaCommandHandler updateHandler;

	@Inject
	private DeleteTransMonthlyWorkTimeSetShaCommandHandler deleteHandler;
	
	@Inject
	private DisplayDeforBasicSettingByEmployee dislaySetting;
	
	@Inject
	private SelectEmployeeDefor select;

	@POST
	@Path("viewO/getListYear/{empId}")
	public List<YearDto> getemployeeYearList(@PathParam("empId") String empId) {
		return yearListByEmployee.get(empId, LaborWorkTypeAttr.DEFOR_LABOR);
	}

	@POST
	@Path("viewO/monthlyWorkTimeSet/add")
	public void registerMonthlyWorkTimeSet(CommandHandlerContext<SaveMonthlyWorkTimeSetShaCommand> command) {
		registerHandler.handle(command);
	}

	@POST
	@Path("viewO/monthlyWorkTimeSet/update")
	public void updateMonthlyWorkTimeSet(CommandHandlerContext<SaveMonthlyWorkTimeSetShaCommand> command) {
		updateHandler.handle(command);
	}

	@POST
	@Path("viewO/monthlyWorkTimeSet/delete")
	public void deleteMonthlyWorkTimeSet(CommandHandlerContext<DeleteTransMonthlyWorkTimeSetShaCommand> command) {
		deleteHandler.handle(command);
	}
	
	@POST
	@Path("viewO/getBasicSetting/{empId}")
	public DeforLaborMonthTimeShaDto getBasicSetting(@PathParam("empId") String empId) {
		return dislaySetting.displayDeforBasicSettingByEmployee(empId);
	}
	
	@POST
	@Path("viewO/selectSha/{empId}")
	public SelectEmployeeDeforDto selectWkp(@PathParam("empId") String empId) {
		return select.selectEmployeeDefor(empId);
	}
}
