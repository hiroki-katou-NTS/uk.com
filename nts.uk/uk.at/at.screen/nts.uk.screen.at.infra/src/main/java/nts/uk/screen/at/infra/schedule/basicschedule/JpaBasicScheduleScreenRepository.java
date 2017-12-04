package nts.uk.screen.at.infra.schedule.basicschedule;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KscdtBasicSchedule;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KscmtScheDispControl;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KscmtScheDispControlPK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KscmtWorkEmpCombine;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.workscheduletimezone.KscdtWorkScheduleTimeZone;
import nts.uk.screen.at.app.schedule.basicschedule.BasicScheduleScreenDto;
import nts.uk.screen.at.app.schedule.basicschedule.BasicScheduleScreenRepository;
import nts.uk.screen.at.app.schedule.basicschedule.ScheduleDisplayControlScreenDto;
import nts.uk.screen.at.app.schedule.basicschedule.WorkEmpCombineScreenDto;
import nts.uk.screen.at.app.schedule.basicschedule.WorkTimeScreenDto;
import nts.uk.screen.at.app.schedule.basicschedule.WorkTypeScreenDto;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class JpaBasicScheduleScreenRepository extends JpaRepository implements BasicScheduleScreenRepository {

	private static final String SEL_BY_LIST_SID_AND_DATE = "SELECT c FROM KscdtBasicSchedule c"
			+ " WHERE c.kscdpBSchedulePK.sId IN :sId AND c.kscdpBSchedulePK.date >= :startDate AND c.kscdpBSchedulePK.date <= :endDate";
	private static final String GET_WORK_TIME_AND_WT_DAY = "SELECT NEW " + WorkTimeScreenDto.class.getName()
			+ " (a.kwtmpWorkTimePK.siftCD, a.workTimeName, a.workTimeAbName, a.workTimeSymbol, a.workTimeDailyAtr, a.workTimeMethodSet, a.displayAtr, a.note,"
			+ " b.start, b.end, b.kwtdpWorkTimeDayPK.timeNumberCnt)"
			+ " FROM KwtmtWorkTime a JOIN KwtdtWorkTimeDay b ON a.kwtmpWorkTimePK.siftCD = b.kwtdpWorkTimeDayPK.siftCD"
			+ " JOIN KshmtWorkTimeOrder c ON a.kwtmpWorkTimePK.siftCD = c.kshmpWorkTimeOrderPK.workTimeCode"
			+ " WHERE a.kwtmpWorkTimePK.companyID = :companyId" + " AND a.displayAtr = :displayAtr"
			+ " ORDER BY c.dispOrder ASC";
	private static final String SELECT_BY_CID_DEPRECATE_CLS = "SELECT NEW " + WorkTypeScreenDto.class.getName()
			+ " (c.kshmtWorkTypePK.workTypeCode, c.name, c.abbreviationName, c.symbolicName, c.memo)"
			+ " FROM KshmtWorkType c" + " WHERE c.kshmtWorkTypePK.companyId = :companyId"
			+ " AND c.deprecateAtr = :deprecateClassification";
	private static final String GET_WORK_EMP_COMBINE = "SELECT c FROM KscmtWorkEmpCombine c"
			+ " WHERE c.kscmtWorkEmpCombinePK.companyId = :companyId " + " AND c.workTypeCode IN :workTypeCode"
			+ " AND c.workTimeCode IN :workTimeCode";
	private static final String GET_WORK_SCH_TIMEZONE = "SELECT a FROM KscdtWorkScheduleTimeZone a"
			+ " WHERE a.kscdtWorkScheduleTimeZonePk.sId IN :sId"
			+ " AND a.kscdtWorkScheduleTimeZonePk.date >= :startDate"
			+ " AND a.kscdtWorkScheduleTimeZonePk.date <= :endDate"
			+ " AND a.kscdtWorkScheduleTimeZonePk.scheduleCnt = 1";

	private static BasicScheduleScreenDto toDto(KscdtBasicSchedule entity) {
		return new BasicScheduleScreenDto(entity.kscdpBSchedulePK.sId, entity.kscdpBSchedulePK.date,
				entity.workTypeCode, entity.workTimeCode);
	}

	private static BasicScheduleScreenDto toBasicScheduleScreenDto(KscdtWorkScheduleTimeZone entity) {
		return new BasicScheduleScreenDto(entity.kscdtWorkScheduleTimeZonePk.sId,
				entity.kscdtWorkScheduleTimeZonePk.date, entity.kscdtWorkScheduleTimeZonePk.scheduleCnt,
				entity.scheduleStartClock, entity.scheduleEndClock, entity.bounceAtr);
	}

	private static ScheduleDisplayControlScreenDto toScheduleDisplayControlScreenDto(KscmtScheDispControl entity) {
		return new ScheduleDisplayControlScreenDto(entity.symbolAtr, entity.symbolHalfDayAtr, entity.symbolHalfDayName);
	}

	private static WorkEmpCombineScreenDto toWorkEmpCombineScreenDto(KscmtWorkEmpCombine entity) {
		return new WorkEmpCombineScreenDto(entity.workTypeCode, entity.workTimeCode, entity.symbolName);
	}

	/**
	 * get list BasicSchedule by list String and startDate and endDate
	 */
	@Override
	public List<BasicScheduleScreenDto> getByListSidAndDate(List<String> employeeId, GeneralDate startDate,
			GeneralDate endDate) {
		return this.queryProxy().query(SEL_BY_LIST_SID_AND_DATE, KscdtBasicSchedule.class)
				.setParameter("sId", employeeId).setParameter("startDate", startDate).setParameter("endDate", endDate)
				.getList(x -> toDto(x));
	}

	/**
	 * get list WorkTime by CompanyId and DisplayAtr = DISPLAY Join with table
	 * WorkTimeDay Join with table ORDER to sort workTimeCode
	 */
	@Override
	public List<WorkTimeScreenDto> getListWorkTime(String companyId, int displayAtr) {
		return this.queryProxy().query(GET_WORK_TIME_AND_WT_DAY, WorkTimeScreenDto.class)
				.setParameter("companyId", companyId).setParameter("displayAtr", displayAtr).getList();
	}

	/**
	 * Find by companyId and deprecateClassification
	 */
	@Override
	public List<WorkTypeScreenDto> findByCIdAndDeprecateCls(String companyId, int deprecateClassification) {
		return this.queryProxy().query(SELECT_BY_CID_DEPRECATE_CLS, WorkTypeScreenDto.class)
				.setParameter("companyId", companyId).setParameter("deprecateClassification", deprecateClassification)
				.getList();
	}

	/**
	 * Get data of table WorkEmpCombine
	 */
	@Override
	public List<WorkEmpCombineScreenDto> getListWorkEmpCobine(String companyId, List<String> lstWorkTypeCode,
			List<String> lstWorkTimeCode) {
		return this.queryProxy().query(GET_WORK_EMP_COMBINE, KscmtWorkEmpCombine.class)
				.setParameter("companyId", companyId).setParameter("workTypeCode", lstWorkTypeCode)
				.setParameter("workTimeCode", lstWorkTimeCode).getList(x -> toWorkEmpCombineScreenDto(x));
	}

	/**
	 * Get data of table ScheduleDisplayControl
	 */
	@Override
	public Optional<ScheduleDisplayControlScreenDto> getScheduleDisControl(String companyId) {
		KscmtScheDispControlPK pk = new KscmtScheDispControlPK(companyId);
		return this.queryProxy().find(pk, KscmtScheDispControl.class).map(x -> toScheduleDisplayControlScreenDto(x));
	}

	/**
	 * Get data of table WorkScheTimezone with scheduleCnt = 1
	 */
	@Override
	public List<BasicScheduleScreenDto> getDataWorkScheTimezone(List<String> sId, GeneralDate startDate,
			GeneralDate endDate) {
		return this.queryProxy().query(GET_WORK_SCH_TIMEZONE, KscdtWorkScheduleTimeZone.class).setParameter("sId", sId)
				.setParameter("startDate", startDate).setParameter("endDate", endDate)
				.getList(x -> toBasicScheduleScreenDto(x));
	}
}
