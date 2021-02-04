package nts.uk.screen.at.ws.kmk.kmk004.o;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetsha.SaveMonthlyWorkTimeSetShaCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.o.DeleteTransMonthlyWorkTimeSetShaCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.o.DeleteTransMonthlyWorkTimeSetShaCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.o.RegisterTransMonthlyWorkTimeSetShaCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.o.UpdateTransMonthlyWorkTimeSetShaCommandHandler;
import nts.uk.screen.at.app.kmk004.o.AfterCopyDeforMonthlyWorkTimeSetSha;
import nts.uk.screen.at.app.kmk004.o.DeforLaborMonthTimeShaDto;
import nts.uk.screen.at.app.kmk004.o.DisplayDeforBasicSettingByEmployee;
import nts.uk.screen.at.app.kmk004.o.SelectEmployeeDefor;
import nts.uk.screen.at.app.kmk004.o.SelectEmployeeDeforDto;
import nts.uk.screen.at.app.kmk004.o.SelectYearByEmployee;
import nts.uk.screen.at.app.query.kmk004.b.WorkTimeComDto;
import nts.uk.screen.at.app.query.kmk004.common.DisplayMonthlyWorkingByShaInputDto;
import nts.uk.screen.at.app.query.kmk004.common.EmployeeIdDto;
import nts.uk.screen.at.app.query.kmk004.common.EmployeeList;

/**
 * 
 * @author tutt
 *
 */
@Path("screen/at/kmk004/viewO")
@Produces("application/json")
public class Kmk004OWebService {

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
	
	@Inject
	private SelectYearByEmployee selectYearByEmployee;

	@Inject
	private EmployeeList employeeList;
	
	@Inject
	private AfterCopyDeforMonthlyWorkTimeSetSha afterCopy;

	@POST
	@Path("monthlyWorkTimeSet/add")
	public void registerMonthlyWorkTimeSet(SaveMonthlyWorkTimeSetShaCommand command) {
		registerHandler.handle(command);
	}

	@POST
	@Path("monthlyWorkTimeSet/update")
	public void updateMonthlyWorkTimeSet(SaveMonthlyWorkTimeSetShaCommand command) {
		updateHandler.handle(command);
	}

	@POST
	@Path("monthlyWorkTimeSet/delete")
	public void deleteMonthlyWorkTimeSet(DeleteTransMonthlyWorkTimeSetShaCommand command) {
		deleteHandler.handle(command);
	}
	
	@POST
	@Path("getBasicSetting/{employeeId}")
	public DeforLaborMonthTimeShaDto getBasicSetting(@PathParam("employeeId") String empId) {
		return dislaySetting.displayDeforBasicSettingByEmployee(empId);
	}
	
	@POST
	@Path("selectSha/{empId}")
	public SelectEmployeeDeforDto selectWkp(@PathParam("empId") String empId) {
		return select.selectEmployeeDefor(empId);
	}
	
	@POST
	@Path("getWorkingHoursByEmployee")
	public List<WorkTimeComDto> getWorkingHoursBySha(DisplayMonthlyWorkingByShaInputDto param) {
		return selectYearByEmployee.getDeforDisplayMonthlyWorkingHoursByEmployee(param) ;
	}
	
	@POST
	@Path("getEmployeeIds")
	public List<EmployeeIdDto> getEmployeeId() {
		return this.employeeList.get(LaborWorkTypeAttr.DEFOR_LABOR);
	}
	
	@POST
	@Path("after-copy")
	public List<String> afterCopy() {
		return this.afterCopy.afterCopyDeforMonthlyWorkTimeSetSha();
	}
}
