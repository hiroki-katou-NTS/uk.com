package nts.uk.screen.at.ws.kmk004;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetcom.DeleteMonthlyWorkTimeSetComCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetcom.DeleteMonthlyWorkTimeSetComCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetcom.DeleteMonthlyWorkTimeSetComInput;
import nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetcom.SaveMonthlyWorkTimeSetComCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetcom.SaveMonthlyWorkTimeSetComCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetcom.YearMonthPeriodCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.p.AddEmpBasicSettingCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.p.AddShaBasicSettingCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.p.AddWkpBasicSettingCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.p.ComBasicSettingCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.p.EmpBasicSettingCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.p.RemoveEmpBasicSettingCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.p.RemoveShaBasicSettingCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.p.RemoveWkpBasicSettingCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.p.ShaBasicSettingCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.p.UpdateComBasicSettingCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.p.UpdateEmpBasicSettingCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.p.UpdateShaBasicSettingCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.p.UpdateWkpBasicSettingCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.p.WkpBasicSettingCommand;
import nts.uk.screen.at.app.query.kmk004.b.DisplayBasicSettings;
import nts.uk.screen.at.app.query.kmk004.b.DisplayBasicSettingsDto;
import nts.uk.screen.at.app.query.kmk004.common.DisplayMonthlyWorkingDto;
import nts.uk.screen.at.app.query.kmk004.common.DisplayMonthlyWorkingHoursByCompany;
import nts.uk.screen.at.app.query.kmk004.common.DisplayMonthlyWorkingInput;
import nts.uk.screen.at.app.query.kmk004.common.DisplayYearListByCompany;
import nts.uk.screen.at.app.query.kmk004.common.GetUsageUnitSetting;
import nts.uk.screen.at.app.query.kmk004.common.GetYearMonthPeriod;
import nts.uk.screen.at.app.query.kmk004.common.UsageUnitSettingDto;
import nts.uk.screen.at.app.query.kmk004.common.YearDto;
import nts.uk.screen.at.app.query.kmk004.common.YearListByEmployee;
import nts.uk.screen.at.app.query.kmk004.common.YearListByEmployment;
import nts.uk.screen.at.app.query.kmk004.common.YearlyListByWorkplace;
import nts.uk.screen.at.app.query.kmk004.p.DeforLaborComDto;
import nts.uk.screen.at.app.query.kmk004.p.DeforLaborEmpDto;
import nts.uk.screen.at.app.query.kmk004.p.DeforLaborShaDto;
import nts.uk.screen.at.app.query.kmk004.p.DeforLaborWkpDto;
import nts.uk.screen.at.app.query.kmk004.p.GetComBasicSetting;
import nts.uk.screen.at.app.query.kmk004.p.GetEmpBasicSetting;
import nts.uk.screen.at.app.query.kmk004.p.GetShaBasicSetting;
import nts.uk.screen.at.app.query.kmk004.p.GetWorkplaceBasicSetting;

/**
 * 
 * @author chungnt
 *
 */

@Path("screen/at/kmk004")
@Produces("application/json")
public class Kmk004WebService extends WebService{

	@Inject
	private GetUsageUnitSetting getUsageUnitSetting;
	
	@Inject
	private DisplayBasicSettings basicSettings;
	
	@Inject
	private DisplayMonthlyWorkingHoursByCompany getworking;
	
	@Inject
	private GetComBasicSetting comBasicSetting;
	
	@Inject
	private GetWorkplaceBasicSetting wkpBasicSetting;
	
	@Inject
	private GetEmpBasicSetting empBasicSetting;
	
	@Inject
	private GetShaBasicSetting shaBasicSetting;
	
	/** Company */
	@Inject
	private UpdateComBasicSettingCommandHandler updateComBasicSettingCommandHandler;
	
	/** Workplace */
	@Inject
	private AddWkpBasicSettingCommandHandler addWkpBasicSettingCommandHandler;
	
	@Inject
	private UpdateWkpBasicSettingCommandHandler updateWkpBasicSettingCommandHandler;
	
	@Inject
	private RemoveWkpBasicSettingCommandHandler removeWkpBasicSettingCommandHandler;
	
	/** Employment */
	@Inject
	private AddEmpBasicSettingCommandHandler addEmpBasicSettingCommandHandler;
	
	@Inject
	private UpdateEmpBasicSettingCommandHandler updateEmpBasicSettingCommandHandler;
	
	@Inject
	private RemoveEmpBasicSettingCommandHandler removeEmpBasicSettingCommandHandler;
	
	/** Employee */
	@Inject
	private AddShaBasicSettingCommandHandler addShaBasicSettingCommandHandler;
	
	@Inject
	private UpdateShaBasicSettingCommandHandler updateShaBasicSettingCommandHandler;
	
	@Inject
	private RemoveShaBasicSettingCommandHandler removeShaBasicSettingCommandHandler;
	
	@Inject
	private SaveMonthlyWorkTimeSetComCommandHandler saveMonthlyWorkTimeSetComCommandHandler;
	
	
	//
	@Inject
	private DisplayYearListByCompany displayYearListByCompany;
	
	@Inject
	private DeleteMonthlyWorkTimeSetComCommandHandler deleteMonthlyWorkTimeSetComCommandHandler;
	
	@Inject
	private GetYearMonthPeriod getYearMonthPeriod;
	
	@Inject
	private YearlyListByWorkplace yearlyListByWorkplace;
	
	@Inject
	private YearListByEmployee yearListByEmployee;
	
	@Inject
	private YearListByEmployment yearListByEmployment;
	
	//View S
	@POST
	@Path("getUsageUnitSetting")
	public UsageUnitSettingDto get() {
		return this.getUsageUnitSetting.get();
	}
	
	//ViewB
	@POST
	@Path("getDisplayBasicSetting")
	public DisplayBasicSettingsDto getDisplayBasicSetting() {
		return this.basicSettings.getSetting();
	}
	
	@POST
	@Path("getWorkingHoursByCompany")
	public List<DisplayMonthlyWorkingDto> getDisplayMonthlyWorkingHoursByCompany(DisplayMonthlyWorkingInput param) {
		return this.getworking.get(param);
	}
	
	@POST
	@Path("viewB/com/monthlyWorkTime/add")
	public void addComMonthlyWorkTime(SaveMonthlyWorkTimeSetComCommand command) {
		saveMonthlyWorkTimeSetComCommandHandler.handle(command);
	}
	
	@POST
	@Path("viewB/com/monthlyWorkTime/delete")
	public void deleteComMonthlyWorkTime(DeleteMonthlyWorkTimeSetComInput param) {
		YearMonthPeriod yearMonthPeriod = this.getYearMonthPeriod.get(param.year);
		
		DeleteMonthlyWorkTimeSetComCommand comCommand = new DeleteMonthlyWorkTimeSetComCommand(param.workType,
				new YearMonthPeriodCommand(yearMonthPeriod.start().v(), yearMonthPeriod.end().v()));
		
		this.deleteMonthlyWorkTimeSetComCommandHandler.handle(comCommand);
	}
	
	@POST
	@Path("viewB/com/getListYear/{workTypeAttr}")
	public List<YearDto> getListYearCom(@PathParam("workTypeAttr") String workTypeAttr) {
		return displayYearListByCompany.get(Integer.parseInt(workTypeAttr));
	}
	
	//ViewC
	@POST
	@Path("viewB/workPlace/getListYear/{workTypeAttr}")
	public List<YearDto> getListYearWorkPlace(@PathParam("workTypeAttr") String workTypeAttr) {
		return displayYearListByCompany.get(Integer.parseInt(workTypeAttr));
	}
	
	//ViewD
	@POST
	@Path("viewB/employment/getListYear/{workTypeAttr}")
	public List<YearDto> getListYearEmployment(@PathParam("workTypeAttr") String workTypeAttr) {
		return displayYearListByCompany.get(Integer.parseInt(workTypeAttr));
	}
	
	//ViewE
	@POST
	@Path("viewB/employee/getListYear/{workTypeAttr}")
	public List<YearDto> getListYearEmployee(@PathParam("workTypeAttr") String workTypeAttr) {
		return displayYearListByCompany.get(Integer.parseInt(workTypeAttr));
	}
	
	// View P
	// Company
	@POST
	@Path("viewP/com/basicSetting")
	public DeforLaborComDto getComBasicSetting() {
		return comBasicSetting.get();
	}
	
	@POST
	@Path("viewP/com/basicSetting/update")
	public void updateComBasicSetting(ComBasicSettingCommand command) {
		updateComBasicSettingCommandHandler.handle(command);
	}
	
	@POST
	@Path("viewL/getListYear")
	public List<YearDto> getComYearList() {
		return displayYearListByCompany.get(LaborWorkTypeAttr.DEFOR_LABOR.value);
	}
	
	// Workplace
	@POST
	@Path("viewP/wkp/basicSetting/{wkpId}")
	public DeforLaborWkpDto getWkpBasicSetting(@PathParam("wkpId") String wkpId) {
		return wkpBasicSetting.get(wkpId);
	}
	
	@POST
	@Path("viewP/wkp/basicSetting/add")
	public void addWkpBasicSetting(WkpBasicSettingCommand command) {
		addWkpBasicSettingCommandHandler.handle(command);
	}
	
	@POST
	@Path("viewP/wkp/basicSetting/update")
	public void updateWkpBasicSetting(WkpBasicSettingCommand command) {
		updateWkpBasicSettingCommandHandler.handle(command);
	}
	
	@POST
	@Path("viewP/wkp/basicSetting/delete")
	public void deleteWkpBasicSetting(WkpBasicSettingCommand command) {
		removeWkpBasicSettingCommandHandler.handle(command);
	}
	
	@POST
	@Path("viewM/getListYear/{wkpId}")
	public List<YearDto> getWkpYearList(@PathParam("wkpId") String wkpId) {
		return yearlyListByWorkplace.get(wkpId, LaborWorkTypeAttr.DEFOR_LABOR);
	}
	
	// Employment
	@POST
	@Path("viewP/emp/basicSetting/{empCode}")
	public DeforLaborEmpDto getEmpBasicSetting(@PathParam("empCode") String empCode) {
		return empBasicSetting.get(empCode);
	}
	
	@POST
	@Path("viewP/emp/basicSetting/add")
	public void addEmpBasicSetting(EmpBasicSettingCommand command) {
		addEmpBasicSettingCommandHandler.handle(command);
	}
	
	@POST
	@Path("viewP/emp/basicSetting/update")
	public void updateEmpBasicSetting(EmpBasicSettingCommand command) {
		updateEmpBasicSettingCommandHandler.handle(command);
	}
	
	@POST
	@Path("viewP/emp/basicSetting/delete")
	public void deleteEmpBasicSetting(EmpBasicSettingCommand command) {
		removeEmpBasicSettingCommandHandler.handle(command);
	}
	
	@POST
	@Path("viewN/getListYear/{empCode}")
	public List<YearDto> getEmpYearList(@PathParam("empCode") String empCode) {
		return yearListByEmployment.get(empCode, LaborWorkTypeAttr.DEFOR_LABOR);
	}
	
	// Employee
	@POST
	@Path("viewP/sha/basicSetting/{empId}")
	public DeforLaborShaDto getshaBasicSetting(@PathParam("empId") String empId) {
		return shaBasicSetting.get(empId);
	}
	
	@POST
	@Path("viewP/sha/basicSetting/add")
	public void addShaBasicSetting(ShaBasicSettingCommand command) {
		addShaBasicSettingCommandHandler.handle(command);
	}
	
	@POST
	@Path("viewP/sha/basicSetting/update")
	public void updateShaBasicSetting(ShaBasicSettingCommand command) {
		updateShaBasicSettingCommandHandler.handle(command);
	}
	
	@POST
	@Path("viewP/sha/basicSetting/delete")
	public void deleteShaBasicSetting(ShaBasicSettingCommand command) {
		removeShaBasicSettingCommandHandler.handle(command);
	}
	
	@POST
	@Path("viewO/getListYear/{empId}")
	public List<YearDto> getemployeeYearList(@PathParam("empId") String empId) {
		return yearListByEmployee.get(empId, LaborWorkTypeAttr.DEFOR_LABOR);
	}
}
