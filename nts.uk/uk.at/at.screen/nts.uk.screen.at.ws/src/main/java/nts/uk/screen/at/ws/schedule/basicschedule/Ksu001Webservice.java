package nts.uk.screen.at.ws.schedule.basicschedule;

import java.util.List;
import java.util.stream.Collectors;

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
import nts.uk.screen.at.app.schedule.basicschedule.WorkTimeScreenDto;
import nts.uk.screen.at.app.schedule.basicschedule.WorkTypeScreenDto;
import nts.uk.screen.at.app.schedule.workschedulestate.WorkScheduleStateScreenProcessor;
import nts.uk.screen.at.app.shift.businesscalendar.holiday.PublicHolidayScreenProcessor;
import nts.uk.screen.at.app.shift.specificdayset.company.ComSpecificDateSetScreenProcessor;
import nts.uk.screen.at.app.shift.specificdayset.workplace.WorkplaceIdAndDateScreenParams;
import nts.uk.screen.at.app.shift.specificdayset.workplace.WorkplaceSpecificDateSetScreenProcessor;
import nts.uk.screen.at.app.shift.workpairpattern.ComPatternScreenDto;
import nts.uk.screen.at.app.shift.workpairpattern.WkpPatternScreenDto;
import nts.uk.shr.com.context.AppContexts;

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
		// get work type
		List<WorkTypeScreenDto> workTypeList = this.bScheduleScreenProces.findByCIdAndDeprecateCls();
		List<String> workTypeCodeList = workTypeList.stream().map(x -> x.getWorkTypeCode())
				.collect(Collectors.toList());
		// get work time
		List<WorkTimeScreenDto> workTimeList = this.bScheduleScreenProces.getListWorkTime();
		List<String> workTimeCodeList = workTimeList.stream().map(x -> x.getWorkTimeCode())
				.collect(Collectors.toList());

		return new DataInitScreenDto(workTypeList, workTimeList, obj.getClosureStartDate(), obj.getClosureEndDate(),
				this.bScheduleScreenProces.checkStateWorkTypeCode(workTypeCodeList),
				this.bScheduleScreenProces.checkNeededOfWorkTimeSetting(workTypeCodeList),
				this.bScheduleScreenProces
						.getListWorkEmpCombine(new ScheduleScreenSymbolParams(workTypeCodeList, workTimeCodeList)),
				AppContexts.user().employeeId());
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
				this.bScheduleScreenProces.getDataWorkScheTimezone(params),
				this.workScheduleStateScreenProces.getByListSidAndDateAndScheId(params));
		return result;
	}

	/*
	 * @POST
	 * 
	 * @Path("getDataWorkScheduleState") public List<WorkScheduleStateScreenDto>
	 * getDataWorkScheduleState(WorkScheduleStateScreenParams params) { return
	 * this.workScheduleStateScreenProces.getByListSidAndDateAndScheId(params);
	 * }
	 */

	@POST
	@Path("checkStateWorkTypeCode")
	public List<StateWorkTypeCodeDto> checkStateWorkTypeCode(List<String> lstWorkTypeCode) {
		return this.bScheduleScreenProces.checkStateWorkTypeCode(lstWorkTypeCode);
	}

	@POST
	@Path("checkNeededOfWorkTimeSetting")
	public List<StateWorkTypeCodeDto> checkNeededOfWorkTimeSetting(List<String> lstWorkTypeCode) {
		return this.bScheduleScreenProces.checkNeededOfWorkTimeSetting(lstWorkTypeCode);
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
