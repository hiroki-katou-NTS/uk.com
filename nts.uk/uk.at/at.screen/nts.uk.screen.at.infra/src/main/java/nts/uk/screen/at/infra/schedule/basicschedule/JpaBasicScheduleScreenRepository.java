package nts.uk.screen.at.infra.schedule.basicschedule;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KscdtBasicSchedule;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KscmtScheDispControl;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KscstScheQualifySet;
import nts.uk.screen.at.app.schedule.basicschedule.BasicScheduleScreenDto;
import nts.uk.screen.at.app.schedule.basicschedule.BasicScheduleScreenRepository;
import nts.uk.screen.at.app.schedule.basicschedule.ScheduleDisplayControlDto;
import nts.uk.screen.at.app.schedule.basicschedule.WorkEmpCombineDto;
import nts.uk.screen.at.app.schedule.basicschedule.WorkTimeScreenDto;
import nts.uk.screen.at.app.schedule.basicschedule.WorkTypeScreenDto;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class JpaBasicScheduleScreenRepository extends JpaRepository implements BasicScheduleScreenRepository {

	private static final String SEL = "SELECT c FROM KscdtBasicSchedule c ";
	private static final String SEL_BY_LIST_SID_AND_DATE = SEL
			+ "WHERE c.kscdpBSchedulePK.sId IN :sId AND c.kscdpBSchedulePK.date >= :startDate AND c.kscdpBSchedulePK.date <= :endDate";

	private static final String GET_WORK_TIME_AND_WT_DAY = "SELECT NEW " + WorkTimeScreenDto.class.getName()
			+ " (a.kwtmpWorkTimePK.siftCD, a.workTimeName, a.workTimeAbName, a.workTimeSymbol, a.workTimeDailyAtr, a.workTimeMethodSet, a.displayAtr, a.note,"
			+ " b.start, b.end, b.kwtdpWorkTimeDayPK.timeNumberCnt)"
			+ " FROM KwtmtWorkTime a JOIN KwtdtWorkTimeDay b ON a.kwtmpWorkTimePK.siftCD = b.kwtdpWorkTimeDayPK.siftCD"
			+ " JOIN KshmtWorkTimeOrder c ON a.kwtmpWorkTimePK.siftCD = c.kshmpWorkTimeOrderPK.workTimeCode"
			+ " WHERE a.kwtmpWorkTimePK.companyID = :companyId" + " AND a.displayAtr = :displayAtr"
			+ " ORDER BY c.dispOrder ASC";

	private final String SELECT_BY_CID_DEPRECATE_CLS = "SELECT NEW " + WorkTypeScreenDto.class.getName()
			+ " (c.kshmtWorkTypePK.workTypeCode, c.name, c.abbreviationName, c.symbolicName, c.memo)"
			+ " FROM KshmtWorkType c" + " WHERE c.kshmtWorkTypePK.companyId = :companyId"
			+ " AND c.deprecateAtr = :deprecateClassification";
	private static final String GET_WORK_EMP_COMBINE = "SELECT c FROM KscmtWorkEmpCombine c WHERE "
			+ " c.kscmtWorkEmpCombinePK.companyId = :companyId " + " AND c.workTypeCode =:workTypeCode "
			+ " AND c.workTimeCode =:workTimeCode";

	private static BasicScheduleScreenDto toDto(KscdtBasicSchedule entity) {
		return new BasicScheduleScreenDto(entity.kscdpBSchedulePK.sId, entity.kscdpBSchedulePK.date,
				entity.workTypeCode, entity.workTimeCode);
	}

	private static final String GET_SCHEDULE_DIS_CONTROL = "SELECT c FROM KscmtScheDispControl c WHERE "
			+ " c.kscmtScheDispControlPK.companyId = :companyId ";
	private static final String GET_SCHEDULE_QUALIFY_SET = "SELECT c FROM KscstScheQualifySet c WHERE "
			+ "c.kscstScheQualifySetPK.companyId = :companyId";

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

	@Override
	public WorkEmpCombineDto getListWorkEmpCobine(String companyId, String workTypeCode, String workTimeCode) {
		return this.queryProxy().query(GET_WORK_EMP_COMBINE, WorkEmpCombineDto.class)
				.setParameter("companyId", companyId).setParameter("workTypeCode", workTypeCode)
				.setParameter("workTimeCode", workTimeCode).getSingleOrNull();
	}

	@Override
	public ScheduleDisplayControlDto getListScheduleDisControl(String companyId) {
		List<String> qualifyCodes = this.queryProxy().query(GET_SCHEDULE_QUALIFY_SET, KscstScheQualifySet.class)
				.setParameter("companyId", companyId).getList(x -> x.kscstScheQualifySetPK.qualifyCode);
		KscmtScheDispControl scheDispControl = this.queryProxy()
				.query(GET_SCHEDULE_DIS_CONTROL, KscmtScheDispControl.class).setParameter("companyId", companyId)
				.getSingleOrNull();
		if (scheDispControl == null) {
			return null;
		}
		return new ScheduleDisplayControlDto(companyId, scheDispControl.personInforAtr,
				scheDispControl.personDisplayAtr, scheDispControl.personSyQualify,
				scheDispControl.pubHolidayShortageAtr, scheDispControl.pubHolidayExcessAtr, scheDispControl.symbolAtr,
				scheDispControl.symbolHalfDayAtr, scheDispControl.symbolHalfDayName, qualifyCodes);
	}
}
