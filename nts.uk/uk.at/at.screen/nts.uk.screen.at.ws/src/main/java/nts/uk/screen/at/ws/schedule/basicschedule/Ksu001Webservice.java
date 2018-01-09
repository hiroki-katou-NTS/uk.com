package nts.uk.screen.at.ws.schedule.basicschedule;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.pub.workrule.closure.PresentClosingPeriodExport;
import nts.uk.screen.at.app.schedule.basicschedule.BasicScheduleScreenParams;
import nts.uk.screen.at.app.schedule.basicschedule.BasicScheduleScreenProcessor;
import nts.uk.screen.at.app.schedule.basicschedule.ScheduleDisplayControlScreenDto;
import nts.uk.screen.at.app.schedule.basicschedule.ScheduleScreenSymbolParams;
import nts.uk.screen.at.app.schedule.basicschedule.StateWorkTypeCodeDto;
import nts.uk.screen.at.app.schedule.basicschedule.WorkEmpCombineScreenDto;
import nts.uk.screen.at.app.schedule.workschedulestate.WorkScheduleStateScreenDto;
import nts.uk.screen.at.app.schedule.workschedulestate.WorkScheduleStateScreenParams;
import nts.uk.screen.at.app.schedule.workschedulestate.WorkScheduleStateScreenProcessor;
import nts.uk.screen.at.app.shift.businesscalendar.holiday.PublicHolidayScreenProcessor;
import nts.uk.screen.at.app.shift.specificdayset.company.ComSpecificDateSetScreenProcessor;
import nts.uk.screen.at.app.shift.specificdayset.workplace.WorkplaceIdAndDateScreenParams;
import nts.uk.screen.at.app.shift.specificdayset.workplace.WorkplaceSpecificDateSetScreenProcessor;
import nts.uk.screen.at.app.shift.workpairpattern.ComPatternScreenDto;
import nts.uk.screen.at.app.shift.workpairpattern.WkpPatternScreenDto;

/**
 * 
 * @author sonnh1
 *
 */
@Path("screen/at/schedule/basicschedule")
@Produces("application/json")
public class Ksu001Webservice extends WebService {

	@Inject
	private BasicScheduleScreenProcessor bScheduleScreenProces;

	@Inject
	private WorkScheduleStateScreenProcessor workScheduleStateScreenProces;

	@Inject
	private WorkplaceSpecificDateSetScreenProcessor workplaceSpecificDateSetScreenProcessor;

	@Inject
	private ComSpecificDateSetScreenProcessor comSpecificDateSetScreenProcessor;

	@Inject
	private PublicHolidayScreenProcessor publicHolidayScreenProcessor;

	/**
	 * Get list data of workType and workTime for combo-box
	 * 
	 * @return List WorkType WorkTime
	 */
	@POST
	public DataInitScreenDto init() {
		PresentClosingPeriodExport obj = this.bScheduleScreenProces.getPresentClosingPeriodExport();
		DataInitScreenDto result = new DataInitScreenDto(this.bScheduleScreenProces.findByCIdAndDeprecateCls(),
				this.bScheduleScreenProces.getListWorkTime(), obj.getClosureStartDate(), obj.getClosureEndDate());
		return result;
	}

	@POST
	@Path("getDataComPattern")
	public List<ComPatternScreenDto> getDataComPattern() {
		return this.bScheduleScreenProces.getDataComPattern();
	}

	@POST
	@Path("getDataWkpPattern")
	public List<WkpPatternScreenDto> getDataWkpPattern(String workplaceId) {
		return this.bScheduleScreenProces.getDataWkpPattern(workplaceId);
	}

	@POST
	@Path("getDataBasicSchedule")
	public BasicScheduleScreenAtDto getDataBasicScheduleScreenAtDto(BasicScheduleScreenParams params) {
		BasicScheduleScreenAtDto result = new BasicScheduleScreenAtDto(
				this.bScheduleScreenProces.getByListSidAndDate(params),
				this.bScheduleScreenProces.getDataWorkScheTimezone(params));
		return result;
	}

	@POST
	@Path("getDataWorkScheduleState")
	public List<WorkScheduleStateScreenDto> getDataWorkScheduleState(WorkScheduleStateScreenParams params) {
		return this.workScheduleStateScreenProces.getByListSidAndDateAndScheId(params);
	}

	@POST
	@Path("checkStateWorkTypeCode")
	public List<StateWorkTypeCodeDto> checkStateWorkTypeCode(List<String> lstWorkTypeCode) {
		return this.bScheduleScreenProces.checkStateWorkTypeCode(lstWorkTypeCode);
	}

	@POST
	@Path("getDataSpecDateAndHoliday")
	public SpecificDateAndPublicHolidayScreenDto getDataSpecDateAndHoliday(WorkplaceIdAndDateScreenParams params) {
		return new SpecificDateAndPublicHolidayScreenDto(
				this.workplaceSpecificDateSetScreenProcessor.findDataWkpSpecificDateSet(params),
				this.comSpecificDateSetScreenProcessor.findDataComSpecificDateSet(params),
				this.publicHolidayScreenProcessor.findDataPublicHoliday(params));
	}

	@POST
	@Path("getWorkEmpCombine")
	public List<WorkEmpCombineScreenDto> getWorkEmpCombines(ScheduleScreenSymbolParams params) {
		return this.bScheduleScreenProces.getListWorkEmpCombine(params);
	}

	@POST
	@Path("getScheduleDisplayControl")
	public ScheduleDisplayControlScreenDto getScheduleDisplayControl() {
		return this.bScheduleScreenProces.getScheduleDisplayControl();
	}
}
