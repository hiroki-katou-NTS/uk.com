package nts.uk.screen.at.ws.kmk.kmk004.e;

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
import nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetcom.YearMonthPeriodCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetsha.DeleteMonthlyWorkTimeSetShaCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetsha.DeleteMonthlyWorkTimeSetShaCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetsha.MonthlyWorkTimeSetShaCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetsha.SaveMonthlyWorkTimeSetShaCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetsha.SaveMonthlyWorkTimeSetShaCommandHandler;
import nts.uk.screen.at.app.kmk004.e.BasicSettingsForEmployee;
import nts.uk.screen.at.app.query.kmk004.b.DisplayBasicSettingsDto;
import nts.uk.screen.at.app.query.kmk004.common.DisplayMonthlyWorkingDto;
import nts.uk.screen.at.app.query.kmk004.common.EmployeeIdDto;
import nts.uk.screen.at.app.query.kmk004.common.EmployeeList;
import nts.uk.screen.at.app.query.kmk004.common.MonthlyWorkingHoursByEmployee;
import nts.uk.screen.at.app.query.kmk004.common.YearDto;
import nts.uk.screen.at.app.query.kmk004.common.YearListByEmployee;

/**
 * 
 * @author chungnt
 *
 */

@Path("screen/at/kmk004")
@Produces("application/json")
public class Kmk004EWebSevice extends WebService {
	
	@Inject
	private EmployeeList employeeList;
	
	@Inject
	private BasicSettingsForEmployee basicSettings;
	
	@Inject
	private YearListByEmployee years;
	
	@Inject
	private MonthlyWorkingHoursByEmployee workTime;
	
	@Inject
	private SaveMonthlyWorkTimeSetShaCommandHandler saveWorkTime;
	
	@Inject
	private DeleteMonthlyWorkTimeSetShaCommandHandler deleteWorkTime;
	
	
	@POST
	@Path("viewe/sha/getEmployeeId")
	public List<EmployeeIdDto> getEmployeeId() {
		return this.employeeList.get(LaborWorkTypeAttr.REGULAR_LABOR);
	}
	
	@POST
	@Path("viewe/sha/getBaseSetting/{sid}")
	public DisplayBasicSettingsDto getBaseSetting(@PathParam("sid") String sid) {
		return this.basicSettings.getSetting(sid);
	}
	
	
	@POST
	@Path("viewe/sha/getYears/{sid}")
	public List<YearDto> getYears(@PathParam("sid") String sid) {
		return this.years.get(sid, LaborWorkTypeAttr.REGULAR_LABOR);
	}
	
	@POST
	@Path("viewe/sha/getWorkTimes/{sid}/{year}")
	public List<DisplayMonthlyWorkingDto> getYears(@PathParam("sid") String sid, @PathParam("year") int year) {
		return this.workTime.get(sid, LaborWorkTypeAttr.REGULAR_LABOR, year);
	}
	
	@POST
	@Path("viewd/sha/monthlyWorkTime/add")
	public void addWorkTimes(WorkTimeInputViewE input) {
		List<MonthlyWorkTimeSetShaCommand> result = input.laborTime.stream().map(m -> {
			return new MonthlyWorkTimeSetShaCommand(input.sid, 0, input.year,
					new MonthlyLaborTimeCommand(m, null, null));
		}).collect(Collectors.toList());

		SaveMonthlyWorkTimeSetShaCommand command = new SaveMonthlyWorkTimeSetShaCommand(result);
		this.saveWorkTime.handle(command);;
	}
	
	@POST
	@Path("viewd/sha/monthlyWorkTime/delete")
	public void addWorkTimes(InputDeleteWorkTimeViewE input) {
		DeleteMonthlyWorkTimeSetShaCommand command = new DeleteMonthlyWorkTimeSetShaCommand(input.sid, 0,
				new YearMonthPeriodCommand(input.startMonth, input.endMonth));
		this.deleteWorkTime.handle(command);
	}
}
