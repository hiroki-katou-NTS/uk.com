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
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.affiliation.AffiliationInfoOfMonthly;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTime;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.mergetable.KrcdtMonMerge;
import nts.uk.ctx.at.record.infra.entity.monthly.mergetable.KrcdtMonMergePk;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

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
	private static final String FIND_BY_PERIOD_INTO_END = "SELECT a FROM KrcdtMonMerge a "
			+ "WHERE a.krcdtMonMergePk.employeeId = :employeeId "
			+ "AND a.endYmd >= :startDate "
			+ "AND a.endYmd <= :endDate "
			+ "ORDER BY a.startYmd ";

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
		return this.queryProxy().find(key, KrcdtMonMerge.class)
				.map(c -> c.toDomainAttendanceTimeOfMonthly());
	}

	/** 検索　（年月） */
	@Override
	public List<AttendanceTimeOfMonthly> findByYearMonthOrderByStartYmd(String employeeId, YearMonth yearMonth) {
		return this.queryProxy().query(FIND_BY_YEAR_MONTH, KrcdtMonMerge.class)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.getList( c -> c.toDomainAttendanceTimeOfMonthly());
	}
	
	/** 検索　（年月と締めID） */
	@Override
	public List<AttendanceTimeOfMonthly> findByYMAndClosureIdOrderByStartYmd(String employeeId, YearMonth yearMonth,
			ClosureId closureId) {
		return this.queryProxy().query(FIND_BY_YM_AND_CLOSURE_ID, KrcdtMonMerge.class)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.setParameter("closureId", closureId.value)
				.getList(c -> c.toDomainAttendanceTimeOfMonthly());
	}

	/** 検索　（社員IDリスト） */
	@Override
	public List<AttendanceTimeOfMonthly> findByEmployees(List<String> employeeIds, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate) {
		
		List<AttendanceTimeOfMonthly> results = new ArrayList<>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			results.addAll(this.queryProxy().query(FIND_BY_EMPLOYEES, KrcdtMonMerge.class)
					.setParameter("employeeIds", splitData)
					.setParameter("yearMonth", yearMonth.v())
					.setParameter("closureId", closureId.value)
					.setParameter("closureDay", closureDate.getClosureDay().v())
					.setParameter("isLastDay", (closureDate.getLastDayOfMonth() ? 1 : 0))
					.getList(c -> c.toDomainAttendanceTimeOfMonthly()));
		});
		return results;
	}
	
	/** 検索　（社員IDリストと年月リスト） */
	@Override
	public List<AttendanceTimeOfMonthly> findBySidsAndYearMonths(List<String> employeeIds, List<YearMonth> yearMonths) {
		
		val yearMonthValues = yearMonths.stream().map(c -> c.v()).collect(Collectors.toList());
		
		List<AttendanceTimeOfMonthly> results = new ArrayList<>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {			
			results.addAll(this.queryProxy().query(FIND_BY_SIDS_AND_YEARMONTHS, KrcdtMonMerge.class)
					.setParameter("employeeIds", splitData)
					.setParameter("yearMonths", yearMonthValues)
					.getList(c -> c.toDomainAttendanceTimeOfMonthly()));
		});
		return results;
	}
		
	/** 検索　（基準日） */
	@Override
	public List<AttendanceTimeOfMonthly> findByDate(String employeeId, GeneralDate criteriaDate) {
		return this.queryProxy().query(FIND_BY_PERIOD, KrcdtMonMerge.class)
				.setParameter("employeeId", employeeId)
				.setParameter("startDate", criteriaDate)
				.setParameter("endDate", criteriaDate)
				.getList(c -> c.toDomainAttendanceTimeOfMonthly());
	}
	
	/** 検索　（終了日を含む期間） */
	@Override
	public List<AttendanceTimeOfMonthly> findByPeriodIntoEndYmd(String employeeId, DatePeriod period) {
		
		return this.queryProxy().query(FIND_BY_PERIOD_INTO_END, KrcdtMonMerge.class)
				.setParameter("employeeId", employeeId)
				.setParameter("startDate", period.start())
				.setParameter("endDate", period.end())
				.getList(c -> c.toDomainAttendanceTimeOfMonthly());
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
		entity.toEntityAffiliationInfoOfMonthly(affiliation.isPresent() == true ? affiliation.get() : new AffiliationInfoOfMonthly(
						domain.getEmployeeId(), 
						domain.getYearMonth(), 
						domain.getClosureId(),
						closureDate));
		
		// 登録が必要な時、登録を実行
		if (isNeedPersist) {
			this.getEntityManager().persist(entity);
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
