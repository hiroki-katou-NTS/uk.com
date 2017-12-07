package nts.uk.screen.at.ws.schedule.basicschedule;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.schedule.basicschedule.BasicScheduleScreenDto;
import nts.uk.screen.at.app.schedule.basicschedule.BasicScheduleScreenParams;
import nts.uk.screen.at.app.schedule.basicschedule.BasicScheduleScreenProcessor;
import nts.uk.screen.at.app.schedule.basicschedule.ScheduleDisplayControlScreenDto;
import nts.uk.screen.at.app.schedule.basicschedule.ScheduleScreenSymbolParams;
import nts.uk.screen.at.app.schedule.basicschedule.StateWorkTypeCodeDto;
import nts.uk.screen.at.app.schedule.basicschedule.WorkEmpCombineScreenDto;
import nts.uk.screen.at.app.schedule.basicschedule.WorkTimeScreenDto;
import nts.uk.screen.at.app.schedule.basicschedule.WorkTypeScreenDto;
import nts.uk.screen.at.app.schedule.workschedulestate.WorkScheduleStateScreenDto;
import nts.uk.screen.at.app.schedule.workschedulestate.WorkScheduleStateScreenParams;
import nts.uk.screen.at.app.schedule.workschedulestate.WorkScheduleStateScreenProcessor;
import nts.uk.screen.at.app.shift.businesscalendar.holiday.PublicHolidayScreenProcessor;
import nts.uk.screen.at.app.shift.specificdayset.company.ComSpecificDateSetScreenProcessor;
import nts.uk.screen.at.app.shift.specificdayset.company.StartDateEndDateScreenParams;
import nts.uk.screen.at.app.shift.specificdayset.workplace.WorkplaceSpecificDateSetScreenParams;
import nts.uk.screen.at.app.shift.specificdayset.workplace.WorkplaceSpecificDateSetScreenProcessor;

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

	@POST
	@Path("getDataBasicSchedule")
	public List<BasicScheduleScreenDto> getData(BasicScheduleScreenParams params) {
		return this.bScheduleScreenProces.getByListSidAndDate(params);
	}

	@POST
	@Path("getDataWorkScheTimezone")
	public List<BasicScheduleScreenDto> getDataWorkScheTimezone(BasicScheduleScreenParams params) {
		return this.bScheduleScreenProces.getDataWorkScheTimezone(params);
	}

	@POST
	@Path("getListWorkTime")
	public List<WorkTimeScreenDto> getWorkTime() {
		return this.bScheduleScreenProces.getListWorkTime();
	}

	/**
	 * Gets worktype base on Cid and deprecateCls
	 *
	 * @return the by Cid and deprecateCls
	 */
	@POST
	@Path("getListWorkType")
	public List<WorkTypeScreenDto> getByCIdAndDeprecateCls() {
		return this.bScheduleScreenProces.findByCIdAndDeprecateCls();
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
	@Path("getDataWkpSpecificDate")
	public List<GeneralDate> getDataWkpSpecificDate(WorkplaceSpecificDateSetScreenParams params) {
		return this.workplaceSpecificDateSetScreenProcessor.findDataWkpSpecificDateSet(params);
	}

	@POST
	@Path("getDataComSpecificDate")
	public List<GeneralDate> getDataComSpecificDate(StartDateEndDateScreenParams params) {
		return this.comSpecificDateSetScreenProcessor.findDataComSpecificDateSet(params);
	}

	@POST
	@Path("getDataPublicHoliday")
	public List<GeneralDate> getDataPublicHoliday(StartDateEndDateScreenParams params) {
		return this.publicHolidayScreenProcessor.findDataPublicHoliday(params);
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
