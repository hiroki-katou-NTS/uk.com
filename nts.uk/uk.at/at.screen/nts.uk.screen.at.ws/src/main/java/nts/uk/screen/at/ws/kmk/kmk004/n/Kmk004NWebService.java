package nts.uk.screen.at.ws.kmk.kmk004.n;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetemp.SaveMonthlyWorkTimeSetEmpCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.n.DeleteTransMonthlyWorkTimeSetEmpCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.n.DeleteTransMonthlyWorkTimeSetEmpCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.n.RegisterTransMonthlyWorkTimeSetEmpCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.n.UpdateTransMonthlyWorkTimeSetEmpCommandHandler;
import nts.uk.screen.at.app.kmk004.n.DeforLaborMonthTimeEmpDto;
import nts.uk.screen.at.app.kmk004.n.DisplayDeforBasicSettingByEmployment;
import nts.uk.screen.at.app.kmk004.n.DisplayInitialDeforScreenByEmployment;
import nts.uk.screen.at.app.kmk004.n.DisplayInitialDeforScreenByEmploymentDto;
import nts.uk.screen.at.app.kmk004.n.SelectEmploymentDefor;
import nts.uk.screen.at.app.kmk004.n.SelectEmploymentDeforDto;
import nts.uk.screen.at.app.kmk004.n.SelectYearByEmployment;
import nts.uk.screen.at.app.kmk004.n.AfterCopyDeforMonthlyWorkTimeSetEmp;
import nts.uk.screen.at.app.query.kmk004.b.WorkTimeComDto;
import nts.uk.screen.at.app.query.kmk004.common.DisplayMonthlyWorkingByEmploymentInputDto;

/**
 * 
 * @author tutt
 *
 */
@Path("screen/at/kmk004/viewN")
@Produces("application/json")
public class Kmk004NWebService {

	@Inject
	private RegisterTransMonthlyWorkTimeSetEmpCommandHandler registerHandler;

	@Inject
	private UpdateTransMonthlyWorkTimeSetEmpCommandHandler updateHandler;

	@Inject
	private DeleteTransMonthlyWorkTimeSetEmpCommandHandler deleteHandler;

	@Inject
	private DisplayDeforBasicSettingByEmployment dislaySetting;

	@Inject
	private DisplayInitialDeforScreenByEmployment initScreen;

	@Inject
	private SelectEmploymentDefor select;

	@Inject
	private SelectYearByEmployment selectYearByEmp;

	@Inject
	private AfterCopyDeforMonthlyWorkTimeSetEmp afterCopy;

	@POST
	@Path("monthlyWorkTimeSet/add")
	public void registerMonthlyWorkTimeSet(SaveMonthlyWorkTimeSetEmpCommand command) {
		registerHandler.handle(command);
	}

	@POST
	@Path("monthlyWorkTimeSet/update")
	public void updateMonthlyWorkTimeSet(SaveMonthlyWorkTimeSetEmpCommand command) {
		updateHandler.handle(command);
	}

	@POST
	@Path("monthlyWorkTimeSet/delete")
	public void deleteMonthlyWorkTimeSet(DeleteTransMonthlyWorkTimeSetEmpCommand command) {
		deleteHandler.handle(command);
	}

	@POST
	@Path("getBasicSetting/{empCode}")
	public DeforLaborMonthTimeEmpDto getBasicSetting(@PathParam("empCode") String empCode) {
		return dislaySetting.displayDeforBasicSettingByEmployment(empCode);
	}

	@POST
	@Path("selectEmp/{empCode}")
	public SelectEmploymentDeforDto selectWkp(@PathParam("empCode") String empCode) {
		return select.selectEmploymentDefor(empCode);
	}

	@POST
	@Path("initScreen")
	public DisplayInitialDeforScreenByEmploymentDto initScreen() {
		return initScreen.displayInitialDeforScreenByEmployment();
	}

	@POST
	@Path("getWorkingHoursByEmp")
	public List<WorkTimeComDto> getWorkingHoursByEmp(DisplayMonthlyWorkingByEmploymentInputDto param) {
		return selectYearByEmp.getDeforDisplayMonthlyWorkingHoursByEmp(param);
	}

	@POST
	@Path("after-copy")
	public List<String> afterCopy() {
		return this.afterCopy.afterCopyDeforMonthlyWorkTimeSetEmp();
	}
}
