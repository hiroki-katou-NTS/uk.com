package nts.uk.ctx.at.record.infra.repository.monthly;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AffiliationInfoOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.totalcount.TotalCount;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.AggregateAbsenceDays;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTime;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.KrcdtMonAggrTotalSpt;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.KrcdtMonAgreementTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.actualworkingtime.KrcdtMonRegIrregTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.flex.KrcdtMonFlexTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.KrcdtMonAggrTotalWrk;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.hdwkandcompleave.KrcdtMonAggrHdwkTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.hdwkandcompleave.KrcdtMonHdwkTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtime.KrcdtMonAggrOverTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtime.KrcdtMonOverTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.vacationusetime.KrcdtMonVactUseTime;
import nts.uk.ctx.at.record.infra.entity.monthly.excessoutside.KrcdtMonExcessOutside;
import nts.uk.ctx.at.record.infra.entity.monthly.excessoutside.KrcdtMonExcoutTime;
import nts.uk.ctx.at.record.infra.entity.monthly.mergetable.KrcdtMonMerge;
import nts.uk.ctx.at.record.infra.entity.monthly.mergetable.KrcdtMonMergePk;
import nts.uk.ctx.at.record.infra.entity.monthly.totalcount.KrcdtMonTotalTimes;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.workclock.KrcdtMonWorkClock;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.workdays.KrcdtMonAggrAbsnDays;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.workdays.KrcdtMonAggrSpecDays;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.workdays.KrcdtMonAggrSpvcDays;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.workdays.KrcdtMonLeave;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.worktime.KrcdtMonAggrBnspyTime;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.worktime.KrcdtMonAggrDivgTime;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.worktime.KrcdtMonAggrGoout;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.worktime.KrcdtMonAggrPremTime;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.worktime.KrcdtMonMedicalTime;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * リポジトリ実装：月別実績の勤怠時間
 * @author shuichi_ishida
 */
@Stateless
public class JpaAttendanceTimeOfMonthly extends JpaRepository implements AttendanceTimeOfMonthlyRepository {

	private static final String SEL_NO_WHERE = "SELECT a FROM KrcdtMonMerge a";
	private static final String FIND_BY_YEAR_MONTH = String.join(" ", SEL_NO_WHERE,
			"WHERE a.krcdtMonMergePk.employeeId =:employeeId",
			"AND   a.krcdtMonMergePk.yearMonth =:yearMonth",
			"ORDER BY a.startYmd");
	private static final String FIND_BY_YM_AND_CLOSURE_ID = String.join(" ", SEL_NO_WHERE,
			"WHERE a.krcdtMonMergePk.employeeId =:employeeId",
			"AND   a.krcdtMonMergePk.yearMonth =:yearMonth",
			"AND   a.krcdtMonMergePk.closureId =:closureId",
			"ORDER BY a.startYmd");
	private static final String FIND_BY_EMPLOYEES = String.join(" ", SEL_NO_WHERE,
			"WHERE a.krcdtMonMergePk.employeeId IN :employeeIds",
			"AND   a.krcdtMonMergePk.yearMonth =:yearMonth",
			"AND   a.krcdtMonMergePk.closureId =:closureId",
			"AND   a.krcdtMonMergePk.closureDay =:closureDay",
			"AND   a.krcdtMonMergePk.isLastDay =:isLastDay",
			"ORDER BY a.krcdtMonMergePk.employeeId");
	private static final String FIND_BY_SIDS_AND_YEARMONTHS = String.join(" ", SEL_NO_WHERE,
			"WHERE a.krcdtMonMergePk.employeeId IN :employeeIds",
			"AND   a.krcdtMonMergePk.yearMonth IN :yearMonths",
			"ORDER BY a.krcdtMonMergePk.employeeId, a.krcdtMonMergePk.yearMonth, a.startYmd");
	private static final String FIND_BY_PERIOD = String.join(" ", SEL_NO_WHERE,
			 "WHERE a.krcdtMonMergePk.employeeId = :employeeId ",
			 "AND a.startYmd <= :endDate ",
			 "AND a.endYmd >= :startDate ");
	private static final String DELETE_BY_YEAR_MONTH = String.join(" ", "DELETE FROM KrcdtMonMerge a ",
			 "WHERE  a.krcdtMonMergePk.employeeId = :employeeId ",
			 "AND 	 a.krcdtMonMergePk.yearMonth = :yearMonth ");	
	
	private static final String SEL_NO_WHERE_TOTAL_TIMES = "SELECT a FROM KrcdtMonTotalTimes a";
	
	private static final String FIND_TOTAL_TIMES_BY_YEAR_MONTH = String.join(" ", SEL_NO_WHERE_TOTAL_TIMES,
			"WHERE a.PK.employeeID =:employeeId",
			"AND a.PK.yearMonth =:yearMonth");
	
	private static final String FIND_TOTAL_TIMES_BY_YM_AND_CLOSURE_ID = String.join(" ", SEL_NO_WHERE_TOTAL_TIMES,
			"WHERE a.PK.employeeID =:employeeId",
			"AND   a.PK.yearMonth =:yearMonth",
			"AND   a.PK.closureId =:closureId");

	private static final String FIND_TOTAL_TIMES_BY_EMPLOYEES = String.join(" ", SEL_NO_WHERE_TOTAL_TIMES,
			"WHERE a.PK.employeeID IN :employeeIds",
			"AND   a.PK.yearMonth =:yearMonth",
			"AND   a.PK.closureId =:closureId",
			"AND   a.PK.closureDay =:closureDay",
			"AND   a.PK.isLastDay =:isLastDay",
			"ORDER BY a.PK.employeeID");
	
	private static final String FIND_TOTAL_TIMES_BY_ONE_EMPLOYEE = String.join(" ", SEL_NO_WHERE_TOTAL_TIMES,
			"WHERE a.PK.employeeID =:employeeId",
			"AND   a.PK.yearMonth =:yearMonth",
			"AND   a.PK.closureId =:closureId",
			"AND   a.PK.closureDay =:closureDay",
			"AND   a.PK.isLastDay =:isLastDay",
			"ORDER BY a.PK.employeeID");
	
	private static final String FIND_TOTAL_TIMES_BY_SIDS_AND_YEARMONTHS = String.join(" ", SEL_NO_WHERE_TOTAL_TIMES,
			"WHERE a.PK.employeeID IN :employeeIds",
			"AND   a.PK.yearMonth IN :yearMonths",
			"ORDER BY a.PK.employeeID, a.PK.yearMonth");
	
	private static final String FIND_TOTAL_TIMES_BY_PERIOD = String.join(" ", SEL_NO_WHERE_TOTAL_TIMES,
			 "WHERE a.PK.employeeID = :employeeId ");
	
	
	private static final String SEL_WORK_CLOCK_NO_WHERE = "SELECT a FROM KrcdtMonWorkClock a";
	private static final String FIND_WORK_CLOCK_BY_YEAR_MONTH = String.join(" ", SEL_WORK_CLOCK_NO_WHERE,
			"WHERE a.PK.employeeId =:employeeId",
			"AND   a.PK.yearMonth =:yearMonth");
	private static final String FIND_WORK_CLOCK_BY_YM_AND_CLOSURE_ID = String.join(" ", SEL_WORK_CLOCK_NO_WHERE,
			"WHERE a.PK.employeeId =:employeeId",
			"AND   a.PK.yearMonth =:yearMonth",
			"AND   a.PK.closureId =:closureId");
	private static final String FIND_WORK_CLOCK_BY_EMPLOYEES = String.join(" ", SEL_WORK_CLOCK_NO_WHERE,
			"WHERE a.PK.employeeId IN :employeeIds",
			"AND   a.PK.yearMonth =:yearMonth",
			"AND   a.PK.closureId =:closureId",
			"AND   a.PK.closureDay =:closureDay",
			"AND   a.PK.isLastDay =:isLastDay",
			"ORDER BY a.PK.employeeId");
	private static final String FIND_WORK_CLOCK_BY_SIDS_AND_YEARMONTHS = String.join(" ", SEL_WORK_CLOCK_NO_WHERE,
			"WHERE a.PK.employeeId IN :employeeIds",
			"AND   a.PK.yearMonth IN :yearMonths",
			"ORDER BY a.PK.employeeId, a.PK.yearMonth");
	private static final String FIND_WORK_CLOCK_BY_PERIOD = String.join(" ", SEL_WORK_CLOCK_NO_WHERE,
			 "WHERE a.PK.employeeId = :employeeId ");
	/** 検索 */
	@Override
	public Optional<AttendanceTimeOfMonthly> find(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate) {
		val key = new KrcdtMonMergePk(
				employeeId,
				yearMonth.v(),
				closureId.value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));
	 
		List<TotalCount> totalCountLst = this.queryProxy().query(FIND_TOTAL_TIMES_BY_ONE_EMPLOYEE, KrcdtMonTotalTimes.class)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.setParameter("closureId", closureId.value)
				.setParameter("closureDay", closureDate.getClosureDay().v())
				.setParameter("isLastDay", (closureDate.getLastDayOfMonth() ? 1 : 0))
				.getList( c -> c.toDomain());
		Optional<KrcdtMonWorkClock> workClock = this.queryProxy().find(new KrcdtMonAttendanceTimePK(
				employeeId,
				yearMonth.v(),
				closureId.value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0)), KrcdtMonWorkClock.class);

		return this.queryProxy().find(key, KrcdtMonMerge.class)
				.map(c -> c.toDomainAttendanceTimeOfMonthly(totalCountLst, workClock));
	}

	/** 検索　（年月） */
	@Override
	public List<AttendanceTimeOfMonthly> findByYearMonthOrderByStartYmd(String employeeId, YearMonth yearMonth) {
		List<TotalCount> totalCountLst = this.queryProxy().query(FIND_TOTAL_TIMES_BY_YEAR_MONTH, KrcdtMonTotalTimes.class)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.getList( c -> c.toDomain());
		
		List<KrcdtMonWorkClock> workClockLst = this.queryProxy().query(FIND_WORK_CLOCK_BY_YEAR_MONTH, KrcdtMonWorkClock.class)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.getList();
		return this.queryProxy().query(FIND_BY_YEAR_MONTH, KrcdtMonMerge.class)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.getList().stream().map(c -> {
					Optional<KrcdtMonWorkClock> monWorkClock =
							workClockLst.stream().filter(a -> a.PK.closureDay == c.krcdtMonMergePk.getClosureDay()
											&& a.PK.isLastDay == c.krcdtMonMergePk.getIsLastDay()
											&& a.PK.closureId == c.krcdtMonMergePk.getClosureId())
											.findFirst();
					return c.toDomainAttendanceTimeOfMonthly(totalCountLst, monWorkClock);
				}).collect(Collectors.toList());
	}
	
	/** 検索　（年月と締めID） */
	@Override
	public List<AttendanceTimeOfMonthly> findByYMAndClosureIdOrderByStartYmd(String employeeId, YearMonth yearMonth,
			ClosureId closureId) {
		List<TotalCount> totalCountLst = this.queryProxy().query(FIND_TOTAL_TIMES_BY_YM_AND_CLOSURE_ID, KrcdtMonTotalTimes.class)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.setParameter("closureId", closureId.value)
				.getList( c -> c.toDomain());
		
		List<KrcdtMonWorkClock> workClockLst = this.queryProxy().query(FIND_WORK_CLOCK_BY_YM_AND_CLOSURE_ID, KrcdtMonWorkClock.class)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.setParameter("closureId", closureId.value)
				.getList();
		return this.queryProxy().query(FIND_BY_YM_AND_CLOSURE_ID, KrcdtMonMerge.class)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.setParameter("closureId", closureId.value)
				.getList().stream().map(c -> {
					Optional<KrcdtMonWorkClock> monWorkClock =
							workClockLst.stream().filter(a -> a.PK.closureDay == c.krcdtMonMergePk.getClosureDay()
											&& a.PK.isLastDay == c.krcdtMonMergePk.getIsLastDay())
											.findFirst();
					return c.toDomainAttendanceTimeOfMonthly(totalCountLst, monWorkClock);
				}).collect(Collectors.toList());
	}

	/** 検索　（社員IDリスト） */
	@Override
	public List<AttendanceTimeOfMonthly> findByEmployees(List<String> employeeIds, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate) {
		
		List<AttendanceTimeOfMonthly> results = new ArrayList<>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			List<TotalCount> totalCountLst = this.queryProxy().query(FIND_TOTAL_TIMES_BY_EMPLOYEES, KrcdtMonTotalTimes.class)
					.setParameter("employeeIds", splitData)
					.setParameter("yearMonth", yearMonth.v())
					.setParameter("closureId", closureId.value)
					.setParameter("closureDay", closureDate.getClosureDay().v())
					.setParameter("isLastDay", (closureDate.getLastDayOfMonth() ? 1 : 0))
					.getList( c -> c.toDomain());
			Optional<KrcdtMonWorkClock> workClockLst = this.queryProxy().query(FIND_WORK_CLOCK_BY_EMPLOYEES, KrcdtMonWorkClock.class)
					.setParameter("employeeIds", splitData)
					.setParameter("yearMonth", yearMonth.v())
					.setParameter("closureId", closureId.value)
					.setParameter("closureDay", closureDate.getClosureDay().v())
					.setParameter("isLastDay", (closureDate.getLastDayOfMonth() ? 1 : 0))
					.getSingle();
			
			results.addAll(this.queryProxy().query(FIND_BY_EMPLOYEES, KrcdtMonMerge.class)
					.setParameter("employeeIds", splitData)
					.setParameter("yearMonth", yearMonth.v())
					.setParameter("closureId", closureId.value)
					.setParameter("closureDay", closureDate.getClosureDay().v())
					.setParameter("isLastDay", (closureDate.getLastDayOfMonth() ? 1 : 0))
					.getList(c -> c.toDomainAttendanceTimeOfMonthly(totalCountLst, workClockLst)));
		});
		return results;
	}
	
	/** 検索　（社員IDリストと年月リスト） */
	@Override
	public List<AttendanceTimeOfMonthly> findBySidsAndYearMonths(List<String> employeeIds, List<YearMonth> yearMonths) {
		
		val yearMonthValues = yearMonths.stream().map(c -> c.v()).collect(Collectors.toList());
		
		List<AttendanceTimeOfMonthly> results = new ArrayList<>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			List<TotalCount> totalCountLst = this.queryProxy().query(FIND_TOTAL_TIMES_BY_SIDS_AND_YEARMONTHS, KrcdtMonTotalTimes.class)
					.setParameter("employeeIds", splitData)
					.setParameter("yearMonths", yearMonthValues)
					.getList( c -> c.toDomain());
			
			List<KrcdtMonWorkClock> workClockLst = this.queryProxy().query(FIND_WORK_CLOCK_BY_SIDS_AND_YEARMONTHS, KrcdtMonWorkClock.class)
					.setParameter("employeeIds", splitData)
					.setParameter("yearMonths", yearMonthValues)
					.getList();
			
			results.addAll(this.queryProxy().query(FIND_BY_SIDS_AND_YEARMONTHS, KrcdtMonMerge.class)
					.setParameter("employeeIds", splitData)
					.setParameter("yearMonths", yearMonthValues)
					.getList().stream().map(c -> {
						Optional<KrcdtMonWorkClock> monWorkClock =
								workClockLst.stream().filter(a -> a.PK.closureDay == c.krcdtMonMergePk.getClosureDay()
												&& a.PK.isLastDay == c.krcdtMonMergePk.getIsLastDay()
												&& a.PK.closureId == c.krcdtMonMergePk.getClosureId())
												.findFirst();
						return c.toDomainAttendanceTimeOfMonthly(totalCountLst, monWorkClock);
					}).collect(Collectors.toList()));
		});
		return results;
	}
		
	/** 検索　（基準日） */
	@Override
	public List<AttendanceTimeOfMonthly> findByDate(String employeeId, GeneralDate criteriaDate) {
		List<TotalCount> totalCountLst = this.queryProxy().query(FIND_TOTAL_TIMES_BY_PERIOD, KrcdtMonTotalTimes.class)
				.setParameter("employeeId", employeeId)
				.getList( c -> c.toDomain());
		
		List<KrcdtMonWorkClock> workClockLst = this.queryProxy().query(FIND_WORK_CLOCK_BY_PERIOD, KrcdtMonWorkClock.class)
				.setParameter("employeeId", employeeId)
				.getList();
		return this.queryProxy().query(FIND_BY_PERIOD, KrcdtMonMerge.class)
				.setParameter("employeeId", employeeId)
				.setParameter("startDate", criteriaDate)
				.setParameter("endDate", criteriaDate)
				.getList().stream().map(c -> {
					Optional<KrcdtMonWorkClock> monWorkClock =
							workClockLst.stream().filter(a -> a.PK.closureDay == c.krcdtMonMergePk.getClosureDay()
											&& a.PK.isLastDay == c.krcdtMonMergePk.getIsLastDay()
											&& a.PK.closureId == c.krcdtMonMergePk.getClosureId()
											&& a.PK.yearMonth == c.krcdtMonMergePk.getYearMonth())
											.findFirst();
					return c.toDomainAttendanceTimeOfMonthly(totalCountLst, monWorkClock);
				}).collect(Collectors.toList());
	}
			
	/** 登録および更新 */
	@Override
	public void persistAndUpdate(AttendanceTimeOfMonthly domain, Optional<AffiliationInfoOfMonthly> affiliation){

		// 締め日付
		val closureDate = domain.getClosureDate();
		
		// キー
		val key = new KrcdtMonMergePk(
				domain.getEmployeeId(),
				domain.getYearMonth().v(),
				domain.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));
		val domainKey = new AttendanceTimeOfMonthlyKey(
				domain.getEmployeeId(),
				domain.getYearMonth(),
				domain.getClosureId(),
				domain.getClosureDate());

		// 月別実績の月の計算
		val monthlyCalculation = domain.getMonthlyCalculation();
		
		// 登録・更新を判断　および　キー値設定
		boolean isNeedPersist = false;
		KrcdtMonMerge entity = this.getEntityManager().find(KrcdtMonMerge.class, key);
		if (entity == null){
			isNeedPersist = true;
			entity = new KrcdtMonMerge();
			entity.krcdtMonMergePk = new KrcdtMonMergePk(domain.getEmployeeId(), domain.getYearMonth().v(),
					domain.getClosureId().value, closureDate.getClosureDay().v(),
					(closureDate.getLastDayOfMonth() ? 1 : 0));
			entity.toEntityAttendanceTimeOfMonthly(domain);
		}
		else entity.toEntityAttendanceTimeOfMonthly(domain);

		// 実働時間：月別実績の通常変形時間
		val actualWorkingTime = monthlyCalculation.getActualWorkingTime();
			entity.toEntityRegAndIrreTimeOfMonth(actualWorkingTime);
		
		// 月別実績のフレックス時間 -KRCDT_MON_FLEX_TIME
		val flexTimeOfMonthly = monthlyCalculation.getFlexTime();
			entity.toEntityFlexTimeOfMonthly(flexTimeOfMonthly);
		
		// 集計時間：月別実績の休暇使用時間 - KRCDT_MON_AGGR_TOTAL_WRK
		val aggregateTime = monthlyCalculation.getAggregateTime();
		val vacationUseTime = aggregateTime.getVacationUseTime();
			entity.toEntityVacationUseTimeOfMonth(vacationUseTime);
		
		// 集計時間：集計総労働時間
			entity.toEntityTotalWorkingTime(aggregateTime);
		
		// 集計時間：残業時間：月別実績の残業時間
		val overTime = aggregateTime.getOverTime();
		entity.toEntityOverTimeOfMonthly(overTime);

		// 集計時間：残業時間：集計残業時間
		val aggrOverTimeMap = overTime.getAggregateOverTimeMap(); 
		int i = 1;
		for(val aggrOverTime : aggrOverTimeMap.values()) {
			switch(i) {
			case 1 :
				entity.toEntityOverTime1(aggrOverTime);
				break;
			case 2:
				entity.toEntityOverTime2(aggrOverTime);
				break;
			case 3:
				entity.toEntityOverTime3(aggrOverTime);
				break;
			case 4:
				entity.toEntityOverTime4(aggrOverTime);
				break;
			case 5:
				entity.toEntityOverTime5(aggrOverTime);
				break;
			case 6:
				entity.toEntityOverTime6(aggrOverTime);
				break;
			case 7:
				entity.toEntityOverTime7(aggrOverTime);
				break;
			case 8:
				entity.toEntityOverTime8(aggrOverTime);
				break;
			case 9:
				entity.toEntityOverTime9(aggrOverTime);
				break;
			case 10:
				entity.toEntityOverTime10(aggrOverTime);
				break;
			}
			i++;
		}

		
		// 集計時間：休出・代休：月別実績の休出時間
		val holidayWorkTime = aggregateTime.getHolidayWorkTime();
		entity.toEntityHolidayWorkTimeOfMonthly(holidayWorkTime);
		
		// 集計時間：休出・代休：集計休出時間
		val aggrHolidayWorkTimeMap = holidayWorkTime.getAggregateHolidayWorkTimeMap();
		i = 1;
		for (val aggrHolidayWorkTime : aggrHolidayWorkTimeMap.values()){

			switch(i) {
			case 1 :
				entity.toEntityHolidayWorkTime1(aggrHolidayWorkTime);
				break;
			case 2:
				entity.toEntityHolidayWorkTime2(aggrHolidayWorkTime);
				break;
			case 3:
				entity.toEntityHolidayWorkTime3(aggrHolidayWorkTime);
				break;
			case 4:
				entity.toEntityHolidayWorkTime4(aggrHolidayWorkTime);
				break;
			case 5:
				entity.toEntityHolidayWorkTime5(aggrHolidayWorkTime);
				break;
			case 6:
				entity.toEntityHolidayWorkTime6(aggrHolidayWorkTime);
				break;
			case 7:
				entity.toEntityHolidayWorkTime7(aggrHolidayWorkTime);
				break;
			case 8:
				entity.toEntityHolidayWorkTime8(aggrHolidayWorkTime);
				break;
			case 9:
				entity.toEntityHolidayWorkTime9(aggrHolidayWorkTime);
				break;
			case 10:
				entity.toEntityHolidayWorkTime10(aggrHolidayWorkTime);
				break;
			}
			i++;
			
		}
		
		// 集計総拘束時間
		val totalTimeSpentAtWork = monthlyCalculation.getTotalTimeSpentAtWork();
		entity.toEntityTotalTimeSpentAtWork(totalTimeSpentAtWork);
		
		// 時間外超過
		val excessOutsideWork = domain.getExcessOutsideWork();
		entity.toEntityExcessOutsideWorkOfMonthly(excessOutsideWork);
		
		// 時間外超過：時間
		val excessOutsideTimeMap = excessOutsideWork.getTime();
		i = 1;
		for (val breakdowns : excessOutsideTimeMap.values()){
			for (val excessOutsideTime : breakdowns.getBreakdown().values()) {
				switch(i) {
				case 1 :
					entity.toEntityExcessOutsideWork1(excessOutsideTime);
					break;
				case 2:
					entity.toEntityExcessOutsideWork2(excessOutsideTime);
					break;
				case 3:
					entity.toEntityExcessOutsideWork3(excessOutsideTime);
					break;
				case 4:
					entity.toEntityExcessOutsideWork4(excessOutsideTime);
					break;
				case 5:
					entity.toEntityExcessOutsideWork5(excessOutsideTime);
					break;
				case 6:
					entity.toEntityExcessOutsideWork6(excessOutsideTime);
					break;
				case 7:
					entity.toEntityExcessOutsideWork7(excessOutsideTime);
					break;
				case 8:
					entity.toEntityExcessOutsideWork8(excessOutsideTime);
					break;
				case 9:
					entity.toEntityExcessOutsideWork9(excessOutsideTime);
					break;
				case 10:
					entity.toEntityExcessOutsideWork10(excessOutsideTime);
					break;
				case 11 :
					entity.toEntityExcessOutsideWork11(excessOutsideTime);
					break;
				case 12:
					entity.toEntityExcessOutsideWork12(excessOutsideTime);
					break;
				case 13:
					entity.toEntityExcessOutsideWork13(excessOutsideTime);
					break;
				case 14:
					entity.toEntityExcessOutsideWork14(excessOutsideTime);
					break;
				case 15:
					entity.toEntityExcessOutsideWork15(excessOutsideTime);
					break;
				case 16:
					entity.toEntityExcessOutsideWork16(excessOutsideTime);
					break;
				case 17:
					entity.toEntityExcessOutsideWork17(excessOutsideTime);
					break;
				case 18:
					entity.toEntityExcessOutsideWork18(excessOutsideTime);
					break;
				case 19:
					entity.toEntityExcessOutsideWork19(excessOutsideTime);
					break;
				case 20:
					entity.toEntityExcessOutsideWork20(excessOutsideTime);
					break;
				case 21 :
					entity.toEntityExcessOutsideWork21(excessOutsideTime);
					break;
				case 22:
					entity.toEntityExcessOutsideWork22(excessOutsideTime);
					break;
				case 23:
					entity.toEntityExcessOutsideWork23(excessOutsideTime);
					break;
				case 24:
					entity.toEntityExcessOutsideWork24(excessOutsideTime);
					break;
				case 25:
					entity.toEntityExcessOutsideWork25(excessOutsideTime);
					break;
				case 26:
					entity.toEntityExcessOutsideWork26(excessOutsideTime);
					break;
				case 27:
					entity.toEntityExcessOutsideWork27(excessOutsideTime);
					break;
				case 28:
					entity.toEntityExcessOutsideWork28(excessOutsideTime);
					break;
				case 29:
					entity.toEntityExcessOutsideWork29(excessOutsideTime);
					break;
				case 30:
					entity.toEntityExcessOutsideWork30(excessOutsideTime);
					break;
				case 31 :
					entity.toEntityExcessOutsideWork31(excessOutsideTime);
					break;
				case 32:
					entity.toEntityExcessOutsideWork32(excessOutsideTime);
					break;
				case 33:
					entity.toEntityExcessOutsideWork33(excessOutsideTime);
					break;
				case 34:
					entity.toEntityExcessOutsideWork34(excessOutsideTime);
					break;
				case 35:
					entity.toEntityExcessOutsideWork35(excessOutsideTime);
					break;
				case 36:
					entity.toEntityExcessOutsideWork36(excessOutsideTime);
					break;
				case 37:
					entity.toEntityExcessOutsideWork37(excessOutsideTime);
					break;
				case 38:
					entity.toEntityExcessOutsideWork38(excessOutsideTime);
					break;
				case 39:
					entity.toEntityExcessOutsideWork39(excessOutsideTime);
					break;
				case 40:
					entity.toEntityExcessOutsideWork40(excessOutsideTime);
					break;				
				case 41 :
					entity.toEntityExcessOutsideWork41(excessOutsideTime);
					break;
				case 42:
					entity.toEntityExcessOutsideWork42(excessOutsideTime);
					break;
				case 43:
					entity.toEntityExcessOutsideWork43(excessOutsideTime);
					break;
				case 44:
					entity.toEntityExcessOutsideWork44(excessOutsideTime);
					break;
				case 45:
					entity.toEntityExcessOutsideWork45(excessOutsideTime);
					break;
				default: break;
				}
				i++;
			}
		}
		
		// 36協定時間
		val agreementTime = monthlyCalculation.getAgreementTime();
		entity.toEntityAgreementTimeOfMonthly(agreementTime);
		
		// 縦計
		val verticalTotal = domain.getVerticalTotal();
		val vtWorkDays = verticalTotal.getWorkDays();
		val vtWorkTime = verticalTotal.getWorkTime(); 
		entity.toEntityVerticalTotalOfMonthly(verticalTotal);
		
		i = 1;
		// 縦計：勤務日数：集計欠勤日数
		val absenceDaysMap = vtWorkDays.getAbsenceDays().getAbsenceDaysList();
		for (AggregateAbsenceDays absenceDays : absenceDaysMap.values()){
			switch (i) {
			case 1:
				entity.toEntityAbsenceDays1(absenceDays);
				break;
			case 2:
				entity.toEntityAbsenceDays2(absenceDays);
				break;
			case 3:
				entity.toEntityAbsenceDays3(absenceDays);
				break;
			case 4:
				entity.toEntityAbsenceDays4(absenceDays);
				break;
			case 5:
				entity.toEntityAbsenceDays5(absenceDays);
				break;
			case 6:
				entity.toEntityAbsenceDays6(absenceDays);
				break;
			case 7:
				entity.toEntityAbsenceDays7(absenceDays);
				break;
			case 8:
				entity.toEntityAbsenceDays8(absenceDays);
				break;
			case 9:
				entity.toEntityAbsenceDays9(absenceDays);
				break;
			case 10:
				entity.toEntityAbsenceDays10(absenceDays);
				break;
			case 11:
				entity.toEntityAbsenceDays11(absenceDays);
				break;
			case 12:
				entity.toEntityAbsenceDays12(absenceDays);
				break;
			case 13:
				entity.toEntityAbsenceDays13(absenceDays);
				break;
			case 14:
				entity.toEntityAbsenceDays14(absenceDays);
				break;
			case 15:
				entity.toEntityAbsenceDays15(absenceDays);
				break;
			case 16:
				entity.toEntityAbsenceDays16(absenceDays);
				break;
			case 17:
				entity.toEntityAbsenceDays17(absenceDays);
				break;
			case 18:
				entity.toEntityAbsenceDays18(absenceDays);
				break;
			case 19:
				entity.toEntityAbsenceDays19(absenceDays);
				break;
			case 20:
				entity.toEntityAbsenceDays20(absenceDays);
				break;
			case 21:
				entity.toEntityAbsenceDays21(absenceDays);
				break;
			case 22:
				entity.toEntityAbsenceDays22(absenceDays);
				break;
			case 23:
				entity.toEntityAbsenceDays23(absenceDays);
				break;
			case 24:
				entity.toEntityAbsenceDays24(absenceDays);
				break;
			case 25:
				entity.toEntityAbsenceDays25(absenceDays);
				break;
			case 26:
				entity.toEntityAbsenceDays26(absenceDays);
				break;
			case 27:
				entity.toEntityAbsenceDays27(absenceDays);
				break;
			case 28:
				entity.toEntityAbsenceDays28(absenceDays);
				break;
			case 29:
				entity.toEntityAbsenceDays29(absenceDays);
				break;
			case 30:
				entity.toEntityAbsenceDays30(absenceDays);
				break;
			}
			i++;
		}
		
		// 縦計：勤務日数：集計特定日数
		val specificDaysMap = vtWorkDays.getSpecificDays().getSpecificDays();
		i = 1;
		for (val specificDays : specificDaysMap.values()) {
			switch (i) {
			case 1:
				entity.toEntitySpecificDays1(specificDays);
				break;
			case 2:
				entity.toEntitySpecificDays2(specificDays);
				break;
			case 3:
				entity.toEntitySpecificDays3(specificDays);
				break;
			case 4:
				entity.toEntitySpecificDays4(specificDays);
				break;
			case 5:
				entity.toEntitySpecificDays5(specificDays);
				break;
			case 6:
				entity.toEntitySpecificDays6(specificDays);
				break;
			case 7:
				entity.toEntitySpecificDays7(specificDays);
				break;
			case 8:
				entity.toEntitySpecificDays8(specificDays);
				break;
			case 9:
				entity.toEntitySpecificDays9(specificDays);
				break;
			case 10:
				entity.toEntitySpecificDays10(specificDays);
				break;
			}
			i++;
		}
		
		// 縦計：勤務日数：集計特別休暇日数
		val spcVactDaysMap = vtWorkDays.getSpecialVacationDays().getSpcVacationDaysList();
		// avoid compile error
//		if (entity.krcdtMonAggrSpvcDays == null) entity.krcdtMonAggrSpvcDays = new ArrayList<>();
//		val entityAggrSpvcDaysList = entity.krcdtMonAggrSpvcDays;
//		entityAggrSpvcDaysList.removeIf(a -> {return !spcVactDaysMap.containsKey(a.PK.specialVacationFrameNo);} );
//		for (val spcVactDays : spcVactDaysMap.values()){
//			KrcdtMonAggrSpvcDays entityAggrSpvcDays = new KrcdtMonAggrSpvcDays();
//			val entityAggrSpvcDaysOpt = entityAggrSpvcDaysList.stream()
//					.filter(c -> c.PK.specialVacationFrameNo == spcVactDays.getSpcVacationFrameNo()).findFirst();
//			if (entityAggrSpvcDaysOpt.isPresent()){
//				entityAggrSpvcDays = entityAggrSpvcDaysOpt.get();
//				entityAggrSpvcDays.fromDomainForUpdate(spcVactDays);
//			}
//			else {
//				entityAggrSpvcDays.fromDomainForPersist(domainKey, spcVactDays);
//				entityAggrSpvcDaysList.add(entityAggrSpvcDays);
//			}
//		}
		
		// 縦計：勤務日数：月別実績の休業
		val vtLeave = vtWorkDays.getLeave();
		entity.toEntityLeaveOfMonthly(vtLeave);
		
		// 縦計：勤務時間：集計加給時間 - 10
		val bonusPayTimeMap = vtWorkTime.getBonusPayTime().getBonusPayTime();
		i = 1;
		for (val bonusPayTime : bonusPayTimeMap.values()){
			switch(i) {
			case 1 :
				entity.toEntityBonusPayTime1(bonusPayTime);
				break;
			case 2:
				entity.toEntityBonusPayTime2(bonusPayTime);
				break;
			case 3:
				entity.toEntityBonusPayTime3(bonusPayTime);
				break;
			case 4:
				entity.toEntityBonusPayTime4(bonusPayTime);
				break;
			case 5:
				entity.toEntityBonusPayTime5(bonusPayTime);
				break;
			case 6:
				entity.toEntityBonusPayTime6(bonusPayTime);
				break;
			case 7:
				entity.toEntityBonusPayTime7(bonusPayTime);
				break;
			case 8:
				entity.toEntityBonusPayTime8(bonusPayTime);
				break;
			case 9:
				entity.toEntityBonusPayTime9(bonusPayTime);
				break;
			case 10:
				entity.toEntityBonusPayTime10(bonusPayTime);
				break;
			}
			i++;
		}
		
		// 縦計：勤務時間：集計乖離時間 - 10
		val divergenceTimeMap = vtWorkTime.getDivergenceTime().getDivergenceTimeList();
		i = 1;
		for (val divergenceTime : divergenceTimeMap.values()){
			switch (i) {
			case 1:
				entity.toEntityDivergenceTime1(divergenceTime);
				break;
			case 2:
				entity.toEntityDivergenceTime2(divergenceTime);
				break;
			case 3:
				entity.toEntityDivergenceTime3(divergenceTime);
				break;
			case 4:
				entity.toEntityDivergenceTime4(divergenceTime);
				break;
			case 5:
				entity.toEntityDivergenceTime5(divergenceTime);
				break;
			case 6:
				entity.toEntityDivergenceTime6(divergenceTime);
				break;
			case 7:
				entity.toEntityDivergenceTime7(divergenceTime);
				break;
			case 8:
				entity.toEntityDivergenceTime8(divergenceTime);
				break;
			case 9:
				entity.toEntityDivergenceTime9(divergenceTime);
				break;
			case 10:
				entity.toEntityDivergenceTime10(divergenceTime);
				break;
			}

			i++;		
		}
		
		// 縦計：勤務時間：集計外出
		val goOutMap = vtWorkTime.getGoOut().getGoOuts();
		i = 1;
		for (val goOut : goOutMap.values()){
			switch (i) {
			case 1:
				entity.toEntityGoOut1(goOut);
				break;
			case 2:
				entity.toEntityGoOut2(goOut);
				break;
			case 3:
				entity.toEntityGoOut3(goOut);
				break;
			case 4:
				entity.toEntityGoOut4(goOut);
				break;
			}

			i++;
		}
		
		// 縦計：勤務時間：集計割増時間
		val premiumTimeMap = vtWorkTime.getPremiumTime().getPremiumTime();
		i = 1;
		for (val premiumTime : premiumTimeMap.values()){
			switch(i){
			case 1:
				entity.toEntityPremiumTime1(premiumTime);
				break;
			case 2:
				entity.toEntityPremiumTime2(premiumTime);
				break;
			case 3:
				entity.toEntityPremiumTime3(premiumTime);
				break;
			case 4:
				entity.toEntityPremiumTime4(premiumTime);
				break;
			case 5:
				entity.toEntityPremiumTime5(premiumTime);
				break;
			case 6:
				entity.toEntityPremiumTime6(premiumTime);
				break;
			case 7:
				entity.toEntityPremiumTime7(premiumTime);
				break;
			case 8:
				entity.toEntityPremiumTime8(premiumTime);
				break;
			case 9:
				entity.toEntityPremiumTime9(premiumTime);
				break;
			case 10:
				entity.toEntityPremiumTime10(premiumTime);
				break;
			}

			i++;
		}
		
		// 縦計：勤務時間：月別実績の医療時間
		val medicalTimeMap = vtWorkTime.getMedicalTime();
		for (val medicalTime : medicalTimeMap.values()){
			entity.toEntityMedicalTimeOfMonthly(medicalTime);
		}
		
		// 縦計：勤務時刻
		val workclock = verticalTotal.getWorkClock();
		KrcdtMonWorkClock krcdtMonWorkClock;
		val keyMonWorkClock = new KrcdtMonAttendanceTimePK(
				domain.getEmployeeId(),
				domain.getYearMonth().v(),
				domain.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));
	 
		Optional<KrcdtMonWorkClock> monWorkClock = this.queryProxy().find(keyMonWorkClock, KrcdtMonWorkClock.class);
		
		if (!monWorkClock.isPresent()){
			krcdtMonWorkClock = new KrcdtMonWorkClock();
			krcdtMonWorkClock.fromDomainForPersist(domainKey, workclock);
		}
		else {
			krcdtMonWorkClock = monWorkClock.get();
			krcdtMonWorkClock.fromDomainForUpdate(workclock);
		}
		
		// 回数集計 
		//TODO - chua co entity trong bang merge
		val totalCountMap = domain.getTotalCount().getTotalCountList();
		
		//TODO KRCDT_MON_AFFILIATION bảng này insert kiểu gì?
		entity.toEntityAffiliationInfoOfMonthly(affiliation.isPresent() == true ? affiliation.get() : new AffiliationInfoOfMonthly(
						domain.getEmployeeId(), 
						domain.getYearMonth(), 
						domain.getClosureId(),
						closureDate));

		List<KrcdtMonTotalTimes> totalCountLst = this.queryProxy().query(FIND_TOTAL_TIMES_BY_ONE_EMPLOYEE, KrcdtMonTotalTimes.class)
				.setParameter("employeeId", domain.getEmployeeId())
				.setParameter("yearMonth", domain.getYearMonth().v())
				.setParameter("closureId", domain.getClosureId().value)
				.setParameter("closureDay", closureDate.getClosureDay().v())
				.setParameter("isLastDay", (closureDate.getLastDayOfMonth() ? 1 : 0))
				.getList();
		
		//if (totalCountLst.isEmpty()) entity.krcdtMonTotalTimes = new ArrayList<>();
		
		val entityTotalTimesList = totalCountLst;
		entityTotalTimesList.removeIf(a -> {return !totalCountMap.containsKey(a.PK.totalTimesNo);} );
		for (val totalCount : totalCountMap.values()){
			KrcdtMonTotalTimes entityTotalTimes = new KrcdtMonTotalTimes();
			val entityTotalTimesOpt = entityTotalTimesList.stream()
					.filter(c -> c.PK.totalTimesNo == totalCount.getTotalCountNo()).findFirst();
			if (entityTotalTimesOpt.isPresent()){
				entityTotalTimes = entityTotalTimesOpt.get();
				entityTotalTimes.fromDomainForUpdate(totalCount);
			}
			else {
				entityTotalTimes.fromDomainForPersist(domainKey, totalCount);
				//entityTotalTimesList.add(entityTotalTimes);
			}
			
			this.getEntityManager().persist(entityTotalTimes);
		}
		
		// 登録が必要な時、登録を実行
		if (isNeedPersist) {
			this.getEntityManager().persist(entity);
			this.getEntityManager().persist(krcdtMonWorkClock);
		}
	}
	
	/** 削除 */
	@Override
	public void remove(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate) {
		
		this.commandProxy().remove(KrcdtMonAttendanceTime.class,
				new KrcdtMonAttendanceTimePK(
						employeeId,
						yearMonth.v(),
						closureId.value,
						closureDate.getClosureDay().v(),
						(closureDate.getLastDayOfMonth() ? 1 : 0)));
	}
		
	/** 削除　（年月） */
	@Override
	public void removeByYearMonth(String employeeId, YearMonth yearMonth) {
		
		this.getEntityManager().createQuery(DELETE_BY_YEAR_MONTH)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.executeUpdate();
	}
	
	/*----------------------これより下はテーブル結合用のソース------------------------------*/
	
	//以下のソースを適用する場合は↑のコメントアウトよりも上のソースを全てコメントアウトする事
	
//	//テーブル結合用
//	private static final String FIND_BY_YEAR_MONTH = "SELECT a FROM KrcdtMonTime a "
//			+ "WHERE a.PK.employeeId = :employeeId "
//			+ "AND a.PK.yearMonth = :yearMonth "
//			+ "ORDER BY a.startYmd ";
//
//	private static final String FIND_BY_YM_AND_CLOSURE_ID = "SELECT a FROM KrcdtMonTime a "
//			+ "WHERE a.PK.employeeId = :employeeId "
//			+ "AND a.PK.yearMonth = :yearMonth "
//			+ "AND a.PK.closureId = :closureId "
//			+ "ORDER BY a.startYmd ";
//
//	private static final String FIND_BY_EMPLOYEES = "SELECT a FROM KrcdtMonTime a "
//			+ "WHERE a.PK.employeeId IN :employeeIds "
//			+ "AND a.PK.yearMonth = :yearMonth "
//			+ "AND a.PK.closureId = :closureId "
//			+ "AND a.PK.closureDay = :closureDay "
//			+ "AND a.PK.isLastDay = :isLastDay "
//			+ "ORDER BY a.PK.employeeId ";
//
//	private static final String FIND_BY_SIDS_AND_YEARMONTHS = "SELECT a FROM KrcdtMonTime a "
//			+ "WHERE a.PK.employeeId IN :employeeIds "
//			+ "AND a.PK.yearMonth IN :yearMonths "
//			+ "ORDER BY a.PK.employeeId, a.PK.yearMonth, a.startYmd ";
//	
//	private static final String FIND_BY_PERIOD = "SELECT a FROM KrcdtMonTime a "
//			+ "WHERE a.PK.employeeId = :employeeId "
//			+ "AND a.startYmd <= :endDate "
//			+ "AND a.endYmd >= :startDate ";
//	
//	private static final String DELETE_BY_YEAR_MONTH = "DELETE FROM KrcdtMonTime a "
//			+ "WHERE a.PK.employeeId = :employeeId "
//			+ "AND a.PK.yearMonth = :yearMonth ";
//	
//	/** 検索 (テーブル結合用)*/
//	@Override
//	public Optional<AttendanceTimeOfMonthly> find(String employeeId, YearMonth yearMonth,
//			ClosureId closureId, ClosureDate closureDate) {
//		
//		return this.queryProxy()
//				.find(new KrcdtMonTimePK(
//						employeeId,
//						yearMonth.v(),
//						closureId.value,
//						closureDate.getClosureDay().v(),
//						(closureDate.getLastDayOfMonth() ? 1 : 0)),
//						KrcdtMonTime.class)
//				.map(c -> c.toDomain());
//	}
//	
//	/** 検索　（年月） （テーブル結合用）*/
//	@Override
//	public List<AttendanceTimeOfMonthly> findByYearMonthOrderByStartYmd(String employeeId, YearMonth yearMonth) {
//		
//		return this.queryProxy().query(FIND_BY_YEAR_MONTH, KrcdtMonTime.class)
//				.setParameter("employeeId", employeeId)
//				.setParameter("yearMonth", yearMonth.v())
//				.getList(c -> c.toDomain());
//	}
//	
//	/** 検索　（年月と締めID）  （テーブル結合用）*/
//	@Override
//	public List<AttendanceTimeOfMonthly> findByYMAndClosureIdOrderByStartYmd(String employeeId, YearMonth yearMonth,
//			ClosureId closureId) {
//		
//		return this.queryProxy().query(FIND_BY_YM_AND_CLOSURE_ID, KrcdtMonTime.class)
//				.setParameter("employeeId", employeeId)
//				.setParameter("yearMonth", yearMonth.v())
//				.setParameter("closureId", closureId.value)
//				.getList(c -> c.toDomain());
//	}
//	
//	/** 検索　（社員IDリスト） （テーブル結合用）*/
//	@Override
//	public List<AttendanceTimeOfMonthly> findByEmployees(List<String> employeeIds, YearMonth yearMonth,
//			ClosureId closureId, ClosureDate closureDate) {
//		
//		List<AttendanceTimeOfMonthly> results = new ArrayList<>();
//		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
//			results.addAll(this.queryProxy().query(FIND_BY_EMPLOYEES, KrcdtMonTime.class)
//					.setParameter("employeeIds", splitData)
//					.setParameter("yearMonth", yearMonth.v())
//					.setParameter("closureId", closureId.value)
//					.setParameter("closureDay", closureDate.getClosureDay().v())
//					.setParameter("isLastDay", (closureDate.getLastDayOfMonth() ? 1 : 0))
//					.getList(c -> c.toDomain()));
//		});
//		return results;
//	}
//	
//	/** 検索　（社員IDリストと年月リスト） （テーブル結合用）*/
//	@Override
//	public List<AttendanceTimeOfMonthly> findBySidsAndYearMonths(List<String> employeeIds, List<YearMonth> yearMonths) {
//		
//		val yearMonthValues = yearMonths.stream().map(c -> c.v()).collect(Collectors.toList());
//		
//		List<AttendanceTimeOfMonthly> results = new ArrayList<>();
//		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
//			results.addAll(this.queryProxy().query(FIND_BY_SIDS_AND_YEARMONTHS, KrcdtMonTime.class)
//					.setParameter("employeeIds", splitData)
//					.setParameter("yearMonths", yearMonthValues)
//					.getList(c -> c.toDomain()));
//		});
//		return results;
//	}
//	
//	/** 検索　（基準日）（テーブル結合用） */
//	@Override
//	public List<AttendanceTimeOfMonthly> findByDate(String employeeId, GeneralDate criteriaDate) {
//		
//		return this.queryProxy().query(FIND_BY_PERIOD, KrcdtMonTime.class)
//				.setParameter("employeeId", employeeId)
//				.setParameter("startDate", criteriaDate)
//				.setParameter("endDate", criteriaDate)
//				.getList(c -> c.toDomain());
//	}
//	
//	/** 登録および更新 （テーブル結合用）*/
//	@Override
//	public void persistAndUpdate(AttendanceTimeOfMonthly domain){
//
//		// 締め日付
//		val closureDate = domain.getClosureDate();
//		
//		// キー
//		val key = new KrcdtMonTimePK(
//				domain.getEmployeeId(),
//				domain.getYearMonth().v(),
//				domain.getClosureId().value,
//				closureDate.getClosureDay().v(),
//				(closureDate.getLastDayOfMonth() ? 1 : 0));
//		val domainKey = new AttendanceTimeOfMonthlyKey(
//				domain.getEmployeeId(),
//				domain.getYearMonth(),
//				domain.getClosureId(),
//				domain.getClosureDate());
//
//		// 月別実績の月の計算
//		val monthlyCalculation = domain.getMonthlyCalculation();
//		
//		// 登録・更新を判断　および　キー値設定
//		boolean isNeedPersist = false;
//		KrcdtMonTime entity = this.getEntityManager().find(KrcdtMonTime.class, key);
//		if (entity == null){
//			isNeedPersist = true;
//			entity = new KrcdtMonTime();
//			entity.fromDomainForPersist(domain);
//		}
//		else entity.fromDomainForUpdate(domain);
//
//		
//		// 集計時間
//		val aggregateTime = monthlyCalculation.getAggregateTime();
//		
//		// 集計時間：残業時間：月別実績の残業時間
//		val overTime = aggregateTime.getOverTime();
//
//		// 集計時間：残業時間：集計残業時間
//		val aggrOverTimeMap = overTime.getAggregateOverTimeMap();
//		if (entity.krcdtMonAggrOverTimes == null) entity.krcdtMonAggrOverTimes = new ArrayList<>();
//		val entityAggrOverTimeList = entity.krcdtMonAggrOverTimes;
//		entityAggrOverTimeList.removeIf(
//				a -> {return !aggrOverTimeMap.containsKey(new OverTimeFrameNo(a.PK.overTimeFrameNo));} );
//		for (val aggrOverTime : aggrOverTimeMap.values()){
//			KrcdtMonAggrOverTime entityAggrOverTime = new KrcdtMonAggrOverTime();
//			val entityAggrOverTimeOpt = entityAggrOverTimeList.stream()
//					.filter(c -> c.PK.overTimeFrameNo == aggrOverTime.getOverTimeFrameNo().v()).findFirst();
//			if (entityAggrOverTimeOpt.isPresent()){
//				entityAggrOverTime = entityAggrOverTimeOpt.get();
//				entityAggrOverTime.fromDomainForUpdate(aggrOverTime);
//			}
//			else {
//				entityAggrOverTime.fromDomainForPersist(domainKey, aggrOverTime);
//				entityAggrOverTimeList.add(entityAggrOverTime);
//			}
//		}
//		
//		// 集計時間：休出・代休：月別実績の休出時間
//		val holidayWorkTime = aggregateTime.getHolidayWorkTime();
//		
//		// 集計時間：休出・代休：集計休出時間
//		val aggrHolidayWorkTimeMap = holidayWorkTime.getAggregateHolidayWorkTimeMap();
//		if (entity.krcdtMonAggrHdwkTimes == null) entity.krcdtMonAggrHdwkTimes = new ArrayList<>();
//		val entityAggrHdwkTimeList = entity.krcdtMonAggrHdwkTimes;
//		entityAggrHdwkTimeList.removeIf(
//				a -> {return !aggrHolidayWorkTimeMap.containsKey(new HolidayWorkFrameNo(a.PK.holidayWorkFrameNo));} );
//		for (val aggrHolidayWorkTime : aggrHolidayWorkTimeMap.values()){
//			KrcdtMonAggrHdwkTime entityAggrHdwkTime = new KrcdtMonAggrHdwkTime();
//			val entityAggrHdwkTimeOpt = entityAggrHdwkTimeList.stream()
//					.filter(c -> c.PK.holidayWorkFrameNo == aggrHolidayWorkTime.getHolidayWorkFrameNo().v()).findFirst();
//			if (entityAggrHdwkTimeOpt.isPresent()){
//				entityAggrHdwkTime = entityAggrHdwkTimeOpt.get();
//				entityAggrHdwkTime.fromDomainForUpdate(aggrHolidayWorkTime);
//			}
//			else {
//				entityAggrHdwkTime.fromDomainForPersist(domainKey, aggrHolidayWorkTime);
//				entityAggrHdwkTimeList.add(entityAggrHdwkTime);
//			}
//		}
//		
//		// 時間外超過
//		val excessOutsideWork = domain.getExcessOutsideWork();
//		if (entity.krcdtMonExcessOutside == null){
//			entity.krcdtMonExcessOutside = new KrcdtMonExcessOutside();
//			entity.krcdtMonExcessOutside.fromDomainForPersist(domainKey, excessOutsideWork);
//		}
//		else entity.krcdtMonExcessOutside.fromDomainForUpdate(excessOutsideWork);
//		
//		// 時間外超過：時間
//		val excessOutsideTimeMap = excessOutsideWork.getTime();
//		if (entity.krcdtMonExcoutTime == null) entity.krcdtMonExcoutTime = new ArrayList<>();
//		val entityExcoutTimeList = entity.krcdtMonExcoutTime;
//		entityExcoutTimeList.removeIf(
//				a -> {return !excessOutsideWork.containsTime(a.PK.breakdownNo, a.PK.excessNo);} );
//		for (val breakdowns : excessOutsideTimeMap.values()){
//			for (val excessOutsideTime : breakdowns.getBreakdown().values()) {
//				KrcdtMonExcoutTime entityExcoutTime = new KrcdtMonExcoutTime();
//				val entityExcoutTimeOpt = entityExcoutTimeList.stream()
//						.filter(c -> (c.PK.breakdownNo == excessOutsideTime.getBreakdownNo() &&
//									c.PK.excessNo == excessOutsideTime.getExcessNo())).findFirst();
//				if (entityExcoutTimeOpt.isPresent()){
//					entityExcoutTime = entityExcoutTimeOpt.get();
//					entityExcoutTime.fromDomainForUpdate(excessOutsideTime);
//				}
//				else {
//					entityExcoutTime.fromDomainForPersist(domainKey, excessOutsideTime);
//					entityExcoutTimeList.add(entityExcoutTime);
//				}
//			}
//		}
//		
//		// 縦計
//		val verticalTotal = domain.getVerticalTotal();
//		val vtWorkDays = verticalTotal.getWorkDays();
//		val vtWorkTime = verticalTotal.getWorkTime();
//		
//		// 縦計：勤務日数：集計欠勤日数
//		val absenceDaysMap = vtWorkDays.getAbsenceDays().getAbsenceDaysList();
//		if (entity.krcdtMonAggrAbsnDays == null) entity.krcdtMonAggrAbsnDays = new ArrayList<>();
//		val entityAggrAbsnDaysList = entity.krcdtMonAggrAbsnDays;
//		entityAggrAbsnDaysList.removeIf(a -> {return !absenceDaysMap.containsKey(a.PK.absenceFrameNo);} );
//		for (val absenceDays : absenceDaysMap.values()){
//			KrcdtMonAggrAbsnDays entityAggrAbsnDays = new KrcdtMonAggrAbsnDays();
//			val entityAggrAbsnDaysOpt = entityAggrAbsnDaysList.stream()
//					.filter(c -> c.PK.absenceFrameNo == absenceDays.getAbsenceFrameNo()).findFirst();
//			if (entityAggrAbsnDaysOpt.isPresent()){
//				entityAggrAbsnDays = entityAggrAbsnDaysOpt.get();
//				entityAggrAbsnDays.fromDomainForUpdate(absenceDays);
//			}
//			else {
//				entityAggrAbsnDays.fromDomainForPersist(domainKey, absenceDays);
//				entityAggrAbsnDaysList.add(entityAggrAbsnDays);
//			}
//		}
//		
//		// 縦計：勤務日数：集計特定日数
//		val specificDaysMap = vtWorkDays.getSpecificDays().getSpecificDays();
//		if (entity.krcdtMonAggrSpecDays == null) entity.krcdtMonAggrSpecDays = new ArrayList<>();
//		val entityAggrSpecDaysList = entity.krcdtMonAggrSpecDays;
//		entityAggrSpecDaysList.removeIf(
//				a -> {return !specificDaysMap.containsKey(new SpecificDateItemNo(a.PK.specificDayItemNo));} );
//		for (val specificDays : specificDaysMap.values()){
//			KrcdtMonAggrSpecDays entityAggrSpecDays = new KrcdtMonAggrSpecDays();
//			val entityAggrSpecDaysOpt = entityAggrSpecDaysList.stream()
//					.filter(c -> c.PK.specificDayItemNo == specificDays.getSpecificDayItemNo().v()).findFirst();
//			if (entityAggrSpecDaysOpt.isPresent()){
//				entityAggrSpecDays = entityAggrSpecDaysOpt.get();
//				entityAggrSpecDays.fromDomainForUpdate(specificDays);
//			}
//			else {
//				entityAggrSpecDays.fromDomainForPersist(domainKey, specificDays);
//				entityAggrSpecDaysList.add(entityAggrSpecDays);
//			}
//		}
//		
//		// 縦計：勤務時間：集計加給時間
//		val bonusPayTimeMap = vtWorkTime.getBonusPayTime().getBonusPayTime();
//		if (entity.krcdtMonAggrBnspyTime == null) entity.krcdtMonAggrBnspyTime = new ArrayList<>();
//		val entityAggrBnspyTimeList = entity.krcdtMonAggrBnspyTime;
//		entityAggrBnspyTimeList.removeIf(a -> {return !bonusPayTimeMap.containsKey(a.PK.bonusPayFrameNo);} );
//		for (val bonusPayTime : bonusPayTimeMap.values()){
//			KrcdtMonAggrBnspyTime entityAggrBnspyTime = new KrcdtMonAggrBnspyTime();
//			val entityAggrBnspyTimeOpt = entityAggrBnspyTimeList.stream()
//					.filter(c -> c.PK.bonusPayFrameNo == bonusPayTime.getBonusPayFrameNo()).findFirst();
//			if (entityAggrBnspyTimeOpt.isPresent()){
//				entityAggrBnspyTime = entityAggrBnspyTimeOpt.get();
//				entityAggrBnspyTime.fromDomainForUpdate(bonusPayTime);
//			}
//			else {
//				entityAggrBnspyTime.fromDomainForPersist(domainKey, bonusPayTime);
//				entityAggrBnspyTimeList.add(entityAggrBnspyTime);
//			}
//		}
//		
//		// 縦計：勤務時間：集計乖離時間
//		val divergenceTimeMap = vtWorkTime.getDivergenceTime().getDivergenceTimeList();
//		if (entity.krcdtMonAggrDivgTime == null) entity.krcdtMonAggrDivgTime = new ArrayList<>();
//		val entityAggrDivgTimeList = entity.krcdtMonAggrDivgTime;
//		entityAggrDivgTimeList.removeIf(a -> {return !divergenceTimeMap.containsKey(a.PK.divergenceTimeNo);} );
//		for (val divergenceTime : divergenceTimeMap.values()){
//			KrcdtMonAggrDivgTime entityAggrDivgTime = new KrcdtMonAggrDivgTime();
//			val entityAggrDivgTimeOpt = entityAggrDivgTimeList.stream()
//					.filter(c -> c.PK.divergenceTimeNo == divergenceTime.getDivergenceTimeNo()).findFirst();
//			if (entityAggrDivgTimeOpt.isPresent()){
//				entityAggrDivgTime = entityAggrDivgTimeOpt.get();
//				entityAggrDivgTime.fromDomainForUpdate(divergenceTime);
//			}
//			else {
//				entityAggrDivgTime.fromDomainForPersist(domainKey, divergenceTime);
//				entityAggrDivgTimeList.add(entityAggrDivgTime);
//			}
//		}
//		
//		// 縦計：勤務時間：集計外出
//		val goOutMap = vtWorkTime.getGoOut().getGoOuts();
//		if (entity.krcdtMonAggrGoout == null) entity.krcdtMonAggrGoout = new ArrayList<>();
//		val entityAggrGooutList = entity.krcdtMonAggrGoout;
//		entityAggrGooutList.removeIf(
//				a -> {return !goOutMap.containsKey(EnumAdaptor.valueOf(a.PK.goOutReason, GoingOutReason.class));} );
//		for (val goOut : goOutMap.values()){
//			KrcdtMonAggrGoout entityAggrGoout = new KrcdtMonAggrGoout();
//			val entityAggrGooutOpt = entityAggrGooutList.stream()
//					.filter(c -> c.PK.goOutReason == goOut.getGoOutReason().value).findFirst();
//			if (entityAggrGooutOpt.isPresent()){
//				entityAggrGoout = entityAggrGooutOpt.get();
//				entityAggrGoout.fromDomainForUpdate(goOut);
//			}
//			else {
//				entityAggrGoout.fromDomainForPersist(domainKey, goOut);
//				entityAggrGooutList.add(entityAggrGoout);
//			}
//		}
//		
//		// 縦計：勤務時間：集計割増時間
//		val premiumTimeMap = vtWorkTime.getPremiumTime().getPremiumTime();
//		if (entity.krcdtMonAggrPremTime == null) entity.krcdtMonAggrPremTime = new ArrayList<>();
//		val entityAggrPremTimeList = entity.krcdtMonAggrPremTime;
//		entityAggrPremTimeList.removeIf(a -> {return !premiumTimeMap.containsKey(a.PK.premiumTimeItemNo);} );
//		for (val premiumTime : premiumTimeMap.values()){
//			KrcdtMonAggrPremTime entityAggrPremTime = new KrcdtMonAggrPremTime();
//			val entityAggrPremTimeOpt = entityAggrPremTimeList.stream()
//					.filter(c -> c.PK.premiumTimeItemNo == premiumTime.getPremiumTimeItemNo()).findFirst();
//			if (entityAggrPremTimeOpt.isPresent()){
//				entityAggrPremTime = entityAggrPremTimeOpt.get();
//				entityAggrPremTime.fromDomainForUpdate(premiumTime);
//			}
//			else {
//				entityAggrPremTime.fromDomainForPersist(domainKey, premiumTime);
//				entityAggrPremTimeList.add(entityAggrPremTime);
//			}
//		}
//		
//		// 縦計：勤務時間：月別実績の医療時間
//		val medicalTimeMap = vtWorkTime.getMedicalTime();
//		if (entity.krcdtMonMedicalTime == null) entity.krcdtMonMedicalTime = new ArrayList<>();
//		val entityMedicalTimeList = entity.krcdtMonMedicalTime;
//		entityMedicalTimeList.removeIf(
//				a -> {return !medicalTimeMap.containsKey(EnumAdaptor.valueOf(a.PK.dayNightAtr, WorkTimeNightShift.class));} );
//		for (val medicalTime : medicalTimeMap.values()){
//			KrcdtMonMedicalTime entityMedicalTime = new KrcdtMonMedicalTime();
//			val entityMedicalTimeOpt = entityMedicalTimeList.stream()
//					.filter(c -> c.PK.dayNightAtr == medicalTime.getDayNightAtr().value).findFirst();
//			if (entityMedicalTimeOpt.isPresent()){
//				entityMedicalTime = entityMedicalTimeOpt.get();
//				entityMedicalTime.fromDomainForUpdate(medicalTime);
//			}
//			else {
//				entityMedicalTime.fromDomainForPersist(domainKey, medicalTime);
//				entityMedicalTimeList.add(entityMedicalTime);
//			}
//		}
//		
//		// 縦計：勤務時刻
//		val workclock = verticalTotal.getWorkClock();
//		if (entity.krcdtMonWorkClock == null){
//			entity.krcdtMonWorkClock = new KrcdtMonWorkClock();
//			entity.krcdtMonWorkClock.fromDomainForPersist(domainKey, workclock);
//		}
//		else entity.krcdtMonWorkClock.fromDomainForUpdate(workclock);
//		
//		// 回数集計
//		val totalCountMap = domain.getTotalCount().getTotalCountList();
//		if (entity.krcdtMonTotalTimes == null) entity.krcdtMonTotalTimes = new ArrayList<>();
//		val entityTotalTimesList = entity.krcdtMonTotalTimes;
//		entityTotalTimesList.removeIf(a -> {return !totalCountMap.containsKey(a.PK.totalTimesNo);} );
//		for (val totalCount : totalCountMap.values()){
//			KrcdtMonTotalTimes entityTotalTimes = new KrcdtMonTotalTimes();
//			val entityTotalTimesOpt = entityTotalTimesList.stream()
//					.filter(c -> c.PK.totalTimesNo == totalCount.getTotalCountNo()).findFirst();
//			if (entityTotalTimesOpt.isPresent()){
//				entityTotalTimes = entityTotalTimesOpt.get();
//				entityTotalTimes.fromDomainForUpdate(totalCount);
//			}
//			else {
//				entityTotalTimes.fromDomainForPersist(domainKey, totalCount);
//				entityTotalTimesList.add(entityTotalTimes);
//			}
//		}
//		
//		// 登録が必要な時、登録を実行
//		if (isNeedPersist) this.getEntityManager().persist(entity);
//	}
//	
//	/** 削除（テーブル結合用） */
//	@Override
//	public void remove(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate) {
//		
//		this.commandProxy().remove(KrcdtMonTime.class,
//				new KrcdtMonTimePK(
//						employeeId,
//						yearMonth.v(),
//						closureId.value,
//						closureDate.getClosureDay().v(),
//						(closureDate.getLastDayOfMonth() ? 1 : 0)));
//	}
//	
//	/** 削除　（年月）（テーブル結合用）  */
//	@Override
//	public void removeByYearMonth(String employeeId, YearMonth yearMonth) {
//		
//		this.getEntityManager().createQuery(DELETE_BY_YEAR_MONTH)
//				.setParameter("employeeId", employeeId)
//				.setParameter("yearMonth", yearMonth.v())
//				.executeUpdate();
//	}
}
