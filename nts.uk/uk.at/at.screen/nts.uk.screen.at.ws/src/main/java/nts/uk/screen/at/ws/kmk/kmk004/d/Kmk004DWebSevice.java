package nts.uk.screen.at.ws.kmk.kmk004.d;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.command.kmk.kmk004.MonthlyLaborTimeCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetcom.YearMonthPeriodCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetemp.DeleteMonthlyWorkTimeSetEmpCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetemp.DeleteMonthlyWorkTimeSetEmpCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetemp.MonthlyWorkTimeSetEmpCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetemp.SaveMonthlyWorkTimeSetEmpCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetemp.SaveMonthlyWorkTimeSetEmpCommandHandler;
import nts.uk.screen.at.app.kmk004.d.BasicSettingsByEmployment;
import nts.uk.screen.at.app.query.kmk004.b.DisplayBasicSettingsDto;
import nts.uk.screen.at.app.query.kmk004.b.WorkTimeComDto;
import nts.uk.screen.at.app.query.kmk004.common.DisplayMonthlyWorkingDto;
import nts.uk.screen.at.app.query.kmk004.common.EmploymentCodeDto;
import nts.uk.screen.at.app.query.kmk004.common.EmploymentList;
import nts.uk.screen.at.app.query.kmk004.common.MonthlyWorkingHoursByEmployment;
import nts.uk.screen.at.app.query.kmk004.common.YearDto;
import nts.uk.screen.at.app.query.kmk004.common.YearListByEmployment;

/**
 * 
 * @author chungnt
 *
 */

@Path("screen/at/kmk004")
@Produces("application/json")
public class Kmk004DWebSevice extends WebService {

	@Inject
	private EmploymentList employmentList;
	
	@Inject
	private BasicSettingsByEmployment baseSetting;
	
	@Inject
	private YearListByEmployment getYears;
	
	@Inject
	private MonthlyWorkingHoursByEmployment workTime;
	
	@Inject
	private SaveMonthlyWorkTimeSetEmpCommandHandler saveWorkTime;
	
	@Inject
	private DeleteMonthlyWorkTimeSetEmpCommandHandler deleteWorkTime;
	
	@POST
	@Path("viewd/emp/getEmploymentId")
	public List<EmploymentCodeDto> getEmploymentCode() {
		return this.employmentList.get(LaborWorkTypeAttr.REGULAR_LABOR);
	}
	
	@POST
	@Path("viewd/emp/getBaseSetting/{employmentCode}")
	public DisplayBasicSettingsDto getBaseSetting(@PathParam("employmentCode") String employmentCode) {
		return this.baseSetting.getSetting(employmentCode);
	}
	
	@POST
	@Path("viewd/emp/years/{employmentCode}")
	public List<YearDto> getYears(@PathParam("employmentCode") String employmentCode) {
		return this.getYears.get(employmentCode, LaborWorkTypeAttr.DEFOR_LABOR);
	}
	
	@POST
	@Path("viewd/emp/getWorkTimes/{employmentCode}/{year}")
	public List<WorkTimeComDto> getWorkTimes(@PathParam("employmentCode") String employmentCode, @PathParam("year") int year) {
		
		List<WorkTimeComDto> result = new ArrayList<>();
		List<DisplayMonthlyWorkingDto> list = this.workTime.get(employmentCode, LaborWorkTypeAttr.REGULAR_LABOR, year);
		result = list.stream().map(m -> {
			WorkTimeComDto w = new WorkTimeComDto();
			
			w.setYearMonth(m.getYearMonth());
			if (m.getLaborTime().getLegalLaborTime() == null){
				w.setLaborTime(0);
			}else {
				w.setLaborTime(m.getLaborTime().getLegalLaborTime());
			}
			
			return w;
		}).collect(Collectors.toList());
		return result;
	}
	
	@POST
	@Path("viewd/emp/monthlyWorkTime/add")
	public void addWorkTimes(WorkTimeInputViewD input) {
		List<MonthlyWorkTimeSetEmpCommand> result = new ArrayList<>();
		for (int i = 0; i < input.getLaborTime().size(); i++) {
			MonthlyWorkTimeSetEmpCommand s = new MonthlyWorkTimeSetEmpCommand(input.employmentCode, 0,
					input.getYearMonth().get(i), new MonthlyLaborTimeCommand(input.getLaborTime().get(i), null, null));
			result.add(s);
		}
		
		SaveMonthlyWorkTimeSetEmpCommand command = new SaveMonthlyWorkTimeSetEmpCommand(result);
		this.saveWorkTime.handle(command);;
	}
	
	@POST
	@Path("viewd/emp/monthlyWorkTime/delete")
	public void deleteWorkTimes(InputDeleteWorkTimeViewD input) {
		DeleteMonthlyWorkTimeSetEmpCommand command = new DeleteMonthlyWorkTimeSetEmpCommand(input.employmentCode, 0,
				new YearMonthPeriodCommand(input.startMonth, input.endMonth));
		this.deleteWorkTime.handle(command);
	}
}
