package nts.uk.screen.at.ws.kmk.kmk004.c;

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
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetwkp.DeleteMonthlyWorkTimeSetWkpCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetwkp.DeleteMonthlyWorkTimeSetWkpCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetwkp.MonthlyWorkTimeSetWkpCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetwkp.SaveMonthlyWorkTimeSetWkpCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetwkp.SaveMonthlyWorkTimeSetWkpCommandHandler;
import nts.uk.screen.at.app.kmk004.c.BasicSettingsForWorkplace;
import nts.uk.screen.at.app.query.kmk004.b.DisplayBasicSettingsDto;
import nts.uk.screen.at.app.query.kmk004.b.WorkTimeComDto;
import nts.uk.screen.at.app.query.kmk004.common.DisplayMonthlyWorkingDto;
import nts.uk.screen.at.app.query.kmk004.common.MonthlyWorkingHoursByWorkplace;
import nts.uk.screen.at.app.query.kmk004.common.WorkplaceIdDto;
import nts.uk.screen.at.app.query.kmk004.common.WorkplaceList;
import nts.uk.screen.at.app.query.kmk004.common.YearDto;
import nts.uk.screen.at.app.query.kmk004.common.YearlyListByWorkplace;

/**
 * 
 * @author chungnt
 *
 */
@Path("screen/at/kmk004")
@Produces("application/json")
public class Kmk004CWebSevice extends WebService{

	@Inject
	private WorkplaceList workplaceList;
	
	@Inject
	private BasicSettingsForWorkplace baseSetting;
	
	@Inject
	private YearlyListByWorkplace getYears;
	
	@Inject
	private MonthlyWorkingHoursByWorkplace getWorkTimes;
	
	@Inject
	private SaveMonthlyWorkTimeSetWkpCommandHandler saveWorkTime;
	
	@Inject
	private DeleteMonthlyWorkTimeSetWkpCommandHandler deleteWorkTime;
	
	@POST
	@Path("viewc/wkp/getWorkPlaceId")
	public List<WorkplaceIdDto> getWorkPlaceId() {
		return this.workplaceList.get(LaborWorkTypeAttr.REGULAR_LABOR);
	}
	
	
	@POST
	@Path("viewc/wkp/getBaseSetting/{wkpId}")
	public DisplayBasicSettingsDto getBaseSetting(@PathParam("wkpId") String wkpId) {
		return this.baseSetting.getSetting(wkpId);
	}
	
	@POST
	@Path("viewc/wkp/getYears/{wkpId}")
	public List<YearDto> getYears(@PathParam("wkpId") String wkpId) {
		return this.getYears.get(wkpId, LaborWorkTypeAttr.REGULAR_LABOR);
	}
	
	@POST
	@Path("viewc/wkp/getWorkTimes/{wkpId}/{year}")
	public List<WorkTimeComDto> getWorkTimes(@PathParam("wkpId") String wkpId, @PathParam("year") int year) {
		
		List<WorkTimeComDto> result = new ArrayList<>();
		List<DisplayMonthlyWorkingDto> list = this.getWorkTimes.get(wkpId, LaborWorkTypeAttr.REGULAR_LABOR, year);
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
	@Path("viewc/wkp/monthlyWorkTime/add")
	public void addOrUpdateWorkTimes(WorkTimeInputViewC input) {
		List<MonthlyWorkTimeSetWkpCommand> result = new ArrayList<>();
		
		for (int i = 0; i < input.getLaborTime().size(); i++) {
			MonthlyWorkTimeSetWkpCommand s = new MonthlyWorkTimeSetWkpCommand(input.workPlaceId, 0,
					input.getYearMonth().get(i), new MonthlyLaborTimeCommand(input.getLaborTime().get(i), null, null));
			result.add(s);
		}
		SaveMonthlyWorkTimeSetWkpCommand command = new SaveMonthlyWorkTimeSetWkpCommand(result);
		this.saveWorkTime.handle(command);
	}
	
	@POST
	@Path("viewc/wkp/monthlyWorkTime/delete")
	public void deleteWorkTimes(InputDeleteWorkTimeViewC input) {

		DeleteMonthlyWorkTimeSetWkpCommand command = new DeleteMonthlyWorkTimeSetWkpCommand(input.workplaceId, 0,
				new YearMonthPeriodCommand(input.startMonth, input.endMonth));
		this.deleteWorkTime.handle(command);
	}

}
