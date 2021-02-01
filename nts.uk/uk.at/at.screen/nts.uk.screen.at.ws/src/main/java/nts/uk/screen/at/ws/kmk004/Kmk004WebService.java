package nts.uk.screen.at.ws.kmk004;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.command.kmk.kmk004.MonthlyLaborTimeCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetcom.DeleteMonthlyWorkTimeSetComCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetcom.DeleteMonthlyWorkTimeSetComCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetcom.DeleteMonthlyWorkTimeSetComInput;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetcom.MonthlyWorkTimeSetComCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetcom.SaveMonthlyWorkTimeSetComCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetcom.SaveMonthlyWorkTimeSetComCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetcom.YearMonthPeriodCommand;
import nts.uk.screen.at.app.query.kmk004.a.DisplayInitialScreenForRegistration;
import nts.uk.screen.at.app.query.kmk004.a.DisplayInitialScreenForRegistrationDto;
import nts.uk.screen.at.app.query.kmk004.b.DisplayBasicSettings;
import nts.uk.screen.at.app.query.kmk004.b.DisplayBasicSettingsDto;
import nts.uk.screen.at.app.query.kmk004.b.WorkTimeComDto;
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

/**
 * 
 * @author chungnt
 *
 */

@Path("screen/at/kmk004")
@Produces("application/json")
public class Kmk004WebService extends WebService {

	@Inject
	private GetUsageUnitSetting getUsageUnitSetting;

	@Inject
	private DisplayBasicSettings basicSettings;

	@Inject
	private DisplayMonthlyWorkingHoursByCompany getworking;

	@Inject
	private SaveMonthlyWorkTimeSetComCommandHandler saveMonthlyWorkTimeSetComCommandHandler;

	@Inject
	private DeleteMonthlyWorkTimeSetComCommandHandler deleteMonthlyWorkTimeSetComCommandHandler;

	@Inject
	private GetYearMonthPeriod getYearMonthPeriod;

	/**
	 * Get Years
	 */
	@Inject
	private DisplayYearListByCompany displayYearListByCompany;

	@Inject
	private YearlyListByWorkplace yearlyListByWorkplace;

	@Inject
	private YearListByEmployee yearListByEmployee;

	@Inject
	private YearListByEmployment yearListByEmployment;

	@Inject
	private DisplayInitialScreenForRegistration initScreenViewA;

	// View A
	@POST
	@Path("viewA/init")
	public DisplayInitialScreenForRegistrationDto getInitViewA() {
		return this.initScreenViewA.get();
	}

	// View S
	@POST
	@Path("getUsageUnitSetting")
	public UsageUnitSettingDto get() {
		return this.getUsageUnitSetting.get();
	}

	// ViewB
	@POST
	@Path("getDisplayBasicSetting")
	public DisplayBasicSettingsDto getDisplayBasicSetting() {
		return this.basicSettings.getSetting();
	}

	@POST
	@Path("getWorkingHoursByCompany")
	public List<WorkTimeComDto> getDisplayMonthlyWorkingHoursByCompany(DisplayMonthlyWorkingInput param) {
		List<WorkTimeComDto> result = new ArrayList<>();
		List<DisplayMonthlyWorkingDto> list = this.getworking.get(param);
		result = list.stream().map(m -> {
			WorkTimeComDto w = new WorkTimeComDto();

			w.setYearMonth(m.getYearMonth());
			if (m.getLaborTime().getLegalLaborTime() == null) {
				w.setLaborTime(0);
			} else {
				w.setLaborTime(m.getLaborTime().getLegalLaborTime());
			}

			return w;
		}).collect(Collectors.toList());
		return result;
	}

	@POST
	@Path("viewB/com/monthlyWorkTime/add")
	public void addComMonthlyWorkTime(WorkTimeInputViewB input) {
		List<MonthlyWorkTimeSetComCommand> result = new ArrayList<>();

		for (int i = 0; i < input.getLaborTime().size(); i++) {
			MonthlyWorkTimeSetComCommand s = new MonthlyWorkTimeSetComCommand(0, input.getYearMonth().get(i),
					new MonthlyLaborTimeCommand(input.getLaborTime().get(i), null, null));
			result.add(s);
		}

		SaveMonthlyWorkTimeSetComCommand comCommand = new SaveMonthlyWorkTimeSetComCommand(result);
		saveMonthlyWorkTimeSetComCommandHandler.handle(comCommand);
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
	@Path("viewB/com/getListYear")
	public List<YearDto> getListYearCom() {
		return displayYearListByCompany.get(LaborWorkTypeAttr.REGULAR_LABOR.value);
	}

	// ViewC
	@POST
	@Path("viewC/workPlace/getListYear/{wkpId}")
	public List<YearDto> getListYearWorkPlace(@PathParam("wkpId") String wkpId) {
		return yearlyListByWorkplace.get(wkpId, LaborWorkTypeAttr.REGULAR_LABOR);
	}

	// ViewD
	@POST
	@Path("viewD/employment/getListYear/{empCode}")
	public List<YearDto> getListYearEmployment(@PathParam("empCode") String empCode) {
		return yearListByEmployment.get(empCode, LaborWorkTypeAttr.REGULAR_LABOR);
	}

	// ViewE
	@POST
	@Path("viewE/employee/getListYear/{empId}")
	public List<YearDto> getListYearEmployee(@PathParam("empId") String empId) {
		return yearListByEmployee.get(empId, LaborWorkTypeAttr.REGULAR_LABOR);
	}

}
