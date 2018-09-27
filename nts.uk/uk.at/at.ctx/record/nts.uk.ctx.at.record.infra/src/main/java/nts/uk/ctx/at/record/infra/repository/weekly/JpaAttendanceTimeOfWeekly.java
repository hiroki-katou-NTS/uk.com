package nts.uk.ctx.at.record.infra.repository.weekly;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.GoingOutReason;
import nts.uk.ctx.at.record.dom.raisesalarytime.primitivevalue.SpecificDateItemNo;
import nts.uk.ctx.at.record.dom.weekly.AttendanceTimeOfWeekly;
import nts.uk.ctx.at.record.dom.weekly.AttendanceTimeOfWeeklyKey;
import nts.uk.ctx.at.record.dom.weekly.AttendanceTimeOfWeeklyRepository;
import nts.uk.ctx.at.record.infra.entity.weekly.KrcdtWekAggrHdwkTime;
import nts.uk.ctx.at.record.infra.entity.weekly.KrcdtWekAggrOverTime;
import nts.uk.ctx.at.record.infra.entity.weekly.KrcdtWekAnyItemValue;
import nts.uk.ctx.at.record.infra.entity.weekly.KrcdtWekAttendanceTime;
import nts.uk.ctx.at.record.infra.entity.weekly.KrcdtWekAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.weekly.KrcdtWekExcoutTime;
import nts.uk.ctx.at.record.infra.entity.weekly.KrcdtWekTotalTimes;
import nts.uk.ctx.at.record.infra.entity.weekly.verticaltotal.workdays.KrcdtWekAggrAbsnDays;
import nts.uk.ctx.at.record.infra.entity.weekly.verticaltotal.workdays.KrcdtWekAggrSpecDays;
import nts.uk.ctx.at.record.infra.entity.weekly.verticaltotal.workdays.KrcdtWekAggrSpvcDays;
import nts.uk.ctx.at.record.infra.entity.weekly.verticaltotal.worktime.KrcdtWekAggrBnspyTime;
import nts.uk.ctx.at.record.infra.entity.weekly.verticaltotal.worktime.KrcdtWekAggrDivgTime;
import nts.uk.ctx.at.record.infra.entity.weekly.verticaltotal.worktime.KrcdtWekAggrGoout;
import nts.uk.ctx.at.record.infra.entity.weekly.verticaltotal.worktime.KrcdtWekAggrPremTime;
import nts.uk.ctx.at.record.infra.entity.weekly.verticaltotal.worktime.KrcdtWekMedicalTime;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkTimeNightShift;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * リポジトリ実装：週別実績の勤怠時間
 * @author shuichi_ishida
 */
@Stateless
public class JpaAttendanceTimeOfWeekly extends JpaRepository implements AttendanceTimeOfWeeklyRepository {

	private static final String FIND_BY_CLOSURE = "SELECT a FROM KrcdtWekAttendanceTime a "
			+ "WHERE a.PK.employeeId = :employeeId "
			+ "AND a.PK.yearMonth = :yearMonth "
			+ "AND a.PK.closureId = :closureId "
			+ "AND a.PK.closureDay = :closureDay "
			+ "AND a.PK.isLastDay = :isLastDay "
			+ "ORDER BY a.startYmd ";

	private static final String FIND_BY_YEARMONTH = "SELECT a FROM KrcdtWekAttendanceTime a "
			+ "WHERE a.PK.employeeId = :employeeId "
			+ "AND a.PK.yearMonth = :yearMonth "
			+ "ORDER BY a.startYmd ";

	private static final String FIND_BY_EMPLOYEES = "SELECT a FROM KrcdtWekAttendanceTime a "
			+ "WHERE a.PK.employeeId IN :employeeIds "
			+ "AND a.PK.yearMonth = :yearMonth "
			+ "AND a.PK.closureId = :closureId "
			+ "AND a.PK.closureDay = :closureDay "
			+ "AND a.PK.isLastDay = :isLastDay "
			+ "ORDER BY a.startYmd ";

	private static final String FIND_BY_SIDS_AND_YEARMONTHS = "SELECT a FROM KrcdtWekAttendanceTime a "
			+ "WHERE a.PK.employeeId IN :employeeIds "
			+ "AND a.PK.yearMonth IN :yearMonths "
			+ "ORDER BY a.PK.employeeId, a.PK.yearMonth, a.startYmd ";
	
	private static final String FIND_BY_PERIOD = "SELECT a FROM KrcdtWekAttendanceTime a "
			+ "WHERE a.PK.employeeId = :employeeId "
			+ "AND a.startYmd <= :endDate "
			+ "AND a.endYmd >= :startDate ";
	
	private static final String DELETE_BY_CLOSURE = "DELETE FROM KrcdtWekAttendanceTime a "
			+ "WHERE a.PK.employeeId = :employeeId "
			+ "AND a.PK.yearMonth = :yearMonth "
			+ "AND a.PK.closureId = :closureId "
			+ "AND a.PK.closureDay = :closureDay "
			+ "AND a.PK.isLastDay = :isLastDay ";
	
	private static final String DELETE_BY_YEARMONTH = "DELETE FROM KrcdtWekAttendanceTime a "
			+ "WHERE a.PK.employeeId = :employeeId "
			+ "AND a.PK.yearMonth = :yearMonth ";

	/** 検索 */
	@Override
	public Optional<AttendanceTimeOfWeekly> find(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate, int weekNo) {
		
		return this.queryProxy()
				.find(new KrcdtWekAttendanceTimePK(
						employeeId,
						yearMonth.v(),
						closureId.value,
						closureDate.getClosureDay().v(),
						(closureDate.getLastDayOfMonth() ? 1 : 0),
						weekNo),
					KrcdtWekAttendanceTime.class)
				.map(c -> c.toDomain());
	}

	/** 検索　（締め） */
	@Override
	public List<AttendanceTimeOfWeekly> findByClosure(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		
		return this.queryProxy().query(FIND_BY_CLOSURE, KrcdtWekAttendanceTime.class)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.setParameter("closureId", closureId.value)
				.setParameter("closureDay", closureDate.getClosureDay().v())
				.setParameter("isLastDay", (closureDate.getLastDayOfMonth() ? 1 : 0))
				.getList(c -> c.toDomain());
	}

	/** 検索　（年月） */
	@Override
	public List<AttendanceTimeOfWeekly> findByYearMonth(String employeeId, YearMonth yearMonth) {
		
		return this.queryProxy().query(FIND_BY_YEARMONTH, KrcdtWekAttendanceTime.class)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.getList(c -> c.toDomain());
	}
	
	/** 検索　（社員IDリストと締め） */
	@Override
	public List<AttendanceTimeOfWeekly> findBySids(List<String> employeeIds, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		
		List<AttendanceTimeOfWeekly> results = new ArrayList<>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			results.addAll(this.queryProxy().query(FIND_BY_EMPLOYEES, KrcdtWekAttendanceTime.class)
					.setParameter("employeeIds", splitData)
					.setParameter("yearMonth", yearMonth.v())
					.setParameter("closureId", closureId.value)
					.setParameter("closureDay", closureDate.getClosureDay().v())
					.setParameter("isLastDay", (closureDate.getLastDayOfMonth() ? 1 : 0))
					.getList(c -> c.toDomain()));
		});
		return results;
	}

	/** 検索　（社員IDリストと年月リスト） */
	@Override
	public List<AttendanceTimeOfWeekly> findBySidsAndYearMonths(List<String> employeeIds, List<YearMonth> yearMonths) {
		
		val yearMonthValues = yearMonths.stream().map(c -> c.v()).collect(Collectors.toList());
		
		List<AttendanceTimeOfWeekly> results = new ArrayList<>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			results.addAll(this.queryProxy().query(FIND_BY_SIDS_AND_YEARMONTHS, KrcdtWekAttendanceTime.class)
					.setParameter("employeeIds", splitData)
					.setParameter("yearMonths", yearMonthValues)
					.getList(c -> c.toDomain()));
		});
		return results;
	}
	
	/** 検索　（基準日） */
	@Override
	public List<AttendanceTimeOfWeekly> findByDate(String employeeId, GeneralDate criteriaDate) {
		
		return this.queryProxy().query(FIND_BY_PERIOD, KrcdtWekAttendanceTime.class)
				.setParameter("employeeId", employeeId)
				.setParameter("startDate", criteriaDate)
				.setParameter("endDate", criteriaDate)
				.getList(c -> c.toDomain());
	}
	
	/** 登録および更新 */
	@Override
	public void persistAndUpdate(AttendanceTimeOfWeekly domain){

		// 締め日付
		val closureDate = domain.getClosureDate();
		
		// キー
		val key = new KrcdtWekAttendanceTimePK(
				domain.getEmployeeId(),
				domain.getYearMonth().v(),
				domain.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0),
				domain.getWeekNo());
		val domainKey = new AttendanceTimeOfWeeklyKey(
				domain.getEmployeeId(),
				domain.getYearMonth(),
				domain.getClosureId(),
				domain.getClosureDate(),
				domain.getWeekNo());

		// 週別の計算
		val weeklyCalculation = domain.getWeeklyCalculation();
		
		// 登録・更新を判断　および　キー値設定
		boolean isNeedPersist = false;
		KrcdtWekAttendanceTime entity = this.getEntityManager().find(KrcdtWekAttendanceTime.class, key);
		if (entity == null){
			isNeedPersist = true;
			entity = new KrcdtWekAttendanceTime();
			entity.fromDomainForPersist(domain);
		}
		else entity.fromDomainForUpdate(domain);

		// 集計時間：残業時間：集計残業時間
		val totalWorkingTime = weeklyCalculation.getTotalWorkingTime();
		val aggrOverTimeMap = totalWorkingTime.getOverTime().getAggregateOverTimeMap();
		if (entity.krcdtWekAggrOverTimes == null) entity.krcdtWekAggrOverTimes = new ArrayList<>();
		val entityAggrOverTimeList = entity.krcdtWekAggrOverTimes;
		entityAggrOverTimeList.removeIf(
				a -> {return !aggrOverTimeMap.containsKey(new OverTimeFrameNo(a.PK.overTimeFrameNo));} );
		for (val aggrOverTime : aggrOverTimeMap.values()){
			KrcdtWekAggrOverTime entityAggrOverTime = new KrcdtWekAggrOverTime();
			val entityAggrOverTimeOpt = entityAggrOverTimeList.stream()
					.filter(c -> c.PK.overTimeFrameNo == aggrOverTime.getOverTimeFrameNo().v()).findFirst();
			if (entityAggrOverTimeOpt.isPresent()){
				entityAggrOverTime = entityAggrOverTimeOpt.get();
				entityAggrOverTime.fromDomainForUpdate(aggrOverTime);
			}
			else {
				entityAggrOverTime.fromDomainForPersist(domainKey, aggrOverTime);
				entityAggrOverTimeList.add(entityAggrOverTime);
			}
		}
		
		// 集計時間：休出・代休：集計休出時間
		val aggrHolidayWorkTimeMap = totalWorkingTime.getHolidayWorkTime().getAggregateHolidayWorkTimeMap();
		if (entity.krcdtWekAggrHdwkTimes == null) entity.krcdtWekAggrHdwkTimes = new ArrayList<>();
		val entityAggrHdwkTimeList = entity.krcdtWekAggrHdwkTimes;
		entityAggrHdwkTimeList.removeIf(
				a -> {return !aggrHolidayWorkTimeMap.containsKey(new HolidayWorkFrameNo(a.PK.holidayWorkFrameNo));} );
		for (val aggrHolidayWorkTime : aggrHolidayWorkTimeMap.values()){
			KrcdtWekAggrHdwkTime entityAggrHdwkTime = new KrcdtWekAggrHdwkTime();
			val entityAggrHdwkTimeOpt = entityAggrHdwkTimeList.stream()
					.filter(c -> c.PK.holidayWorkFrameNo == aggrHolidayWorkTime.getHolidayWorkFrameNo().v()).findFirst();
			if (entityAggrHdwkTimeOpt.isPresent()){
				entityAggrHdwkTime = entityAggrHdwkTimeOpt.get();
				entityAggrHdwkTime.fromDomainForUpdate(aggrHolidayWorkTime);
			}
			else {
				entityAggrHdwkTime.fromDomainForPersist(domainKey, aggrHolidayWorkTime);
				entityAggrHdwkTimeList.add(entityAggrHdwkTime);
			}
		}
		
		// 時間外超過：時間外超過
		val excessOutsideTimeMap = domain.getExcessOutside().getExcessOutsideItems();
		if (entity.krcdtWekExcoutTime == null) entity.krcdtWekExcoutTime = new ArrayList<>();
		val entityExcoutTimeList = entity.krcdtWekExcoutTime;
		entityExcoutTimeList.removeIf(
				a -> {return !excessOutsideTimeMap.containsKey(a.PK.breakdownNo);} );
		for (val breakdown : excessOutsideTimeMap.values()){
			KrcdtWekExcoutTime entityExcoutTime = new KrcdtWekExcoutTime();
			val entityExcoutTimeOpt = entityExcoutTimeList.stream()
					.filter(c -> c.PK.breakdownNo == breakdown.getBreakdownNo()).findFirst();
			if (entityExcoutTimeOpt.isPresent()){
				entityExcoutTime = entityExcoutTimeOpt.get();
				entityExcoutTime.fromDomainForUpdate(breakdown);
			}
			else {
				entityExcoutTime.fromDomainForPersist(domainKey, breakdown);
				entityExcoutTimeList.add(entityExcoutTime);
			}
		}
		
		// 縦計：勤務日数：集計欠勤日数
		val vtWorkDays = domain.getVerticalTotal().getWorkDays();
		val absenceDaysMap = vtWorkDays.getAbsenceDays().getAbsenceDaysList();
		if (entity.krcdtWekAggrAbsnDays == null) entity.krcdtWekAggrAbsnDays = new ArrayList<>();
		val entityAggrAbsnDaysList = entity.krcdtWekAggrAbsnDays;
		entityAggrAbsnDaysList.removeIf(a -> {return !absenceDaysMap.containsKey(a.PK.absenceFrameNo);} );
		for (val absenceDays : absenceDaysMap.values()){
			KrcdtWekAggrAbsnDays entityAggrAbsnDays = new KrcdtWekAggrAbsnDays();
			val entityAggrAbsnDaysOpt = entityAggrAbsnDaysList.stream()
					.filter(c -> c.PK.absenceFrameNo == absenceDays.getAbsenceFrameNo()).findFirst();
			if (entityAggrAbsnDaysOpt.isPresent()){
				entityAggrAbsnDays = entityAggrAbsnDaysOpt.get();
				entityAggrAbsnDays.fromDomainForUpdate(absenceDays);
			}
			else {
				entityAggrAbsnDays.fromDomainForPersist(domainKey, absenceDays);
				entityAggrAbsnDaysList.add(entityAggrAbsnDays);
			}
		}
		
		// 縦計：勤務日数：集計特定日数
		val specificDaysMap = vtWorkDays.getSpecificDays().getSpecificDays();
		if (entity.krcdtWekAggrSpecDays == null) entity.krcdtWekAggrSpecDays = new ArrayList<>();
		val entityAggrSpecDaysList = entity.krcdtWekAggrSpecDays;
		entityAggrSpecDaysList.removeIf(
				a -> {return !specificDaysMap.containsKey(new SpecificDateItemNo(a.PK.specificDayItemNo));} );
		for (val specificDays : specificDaysMap.values()){
			KrcdtWekAggrSpecDays entityAggrSpecDays = new KrcdtWekAggrSpecDays();
			val entityAggrSpecDaysOpt = entityAggrSpecDaysList.stream()
					.filter(c -> c.PK.specificDayItemNo == specificDays.getSpecificDayItemNo().v()).findFirst();
			if (entityAggrSpecDaysOpt.isPresent()){
				entityAggrSpecDays = entityAggrSpecDaysOpt.get();
				entityAggrSpecDays.fromDomainForUpdate(specificDays);
			}
			else {
				entityAggrSpecDays.fromDomainForPersist(domainKey, specificDays);
				entityAggrSpecDaysList.add(entityAggrSpecDays);
			}
		}
		
		// 縦計：勤務日数：集計特別休暇日数
		val spcVactDaysMap = vtWorkDays.getSpecialVacationDays().getSpcVacationDaysList();
		if (entity.krcdtWekAggrSpvcDays == null) entity.krcdtWekAggrSpvcDays = new ArrayList<>();
		val entityAggrSpvcDaysList = entity.krcdtWekAggrSpvcDays;
		entityAggrSpvcDaysList.removeIf(a -> {return !spcVactDaysMap.containsKey(a.PK.specialVacationFrameNo);} );
		for (val spcVactDays : spcVactDaysMap.values()){
			KrcdtWekAggrSpvcDays entityAggrSpvcDays = new KrcdtWekAggrSpvcDays();
			val entityAggrSpvcDaysOpt = entityAggrSpvcDaysList.stream()
					.filter(c -> c.PK.specialVacationFrameNo == spcVactDays.getSpcVacationFrameNo()).findFirst();
			if (entityAggrSpvcDaysOpt.isPresent()){
				entityAggrSpvcDays = entityAggrSpvcDaysOpt.get();
				entityAggrSpvcDays.fromDomainForUpdate(spcVactDays);
			}
			else {
				entityAggrSpvcDays.fromDomainForPersist(domainKey, spcVactDays);
				entityAggrSpvcDaysList.add(entityAggrSpvcDays);
			}
		}
		
		// 縦計：勤務時間：集計加給時間
		val vtWorkTime = domain.getVerticalTotal().getWorkTime();
		val bonusPayTimeMap = vtWorkTime.getBonusPayTime().getBonusPayTime();
		if (entity.krcdtWekAggrBnspyTime == null) entity.krcdtWekAggrBnspyTime = new ArrayList<>();
		val entityAggrBnspyTimeList = entity.krcdtWekAggrBnspyTime;
		entityAggrBnspyTimeList.removeIf(a -> {return !bonusPayTimeMap.containsKey(a.PK.bonusPayFrameNo);} );
		for (val bonusPayTime : bonusPayTimeMap.values()){
			KrcdtWekAggrBnspyTime entityAggrBnspyTime = new KrcdtWekAggrBnspyTime();
			val entityAggrBnspyTimeOpt = entityAggrBnspyTimeList.stream()
					.filter(c -> c.PK.bonusPayFrameNo == bonusPayTime.getBonusPayFrameNo()).findFirst();
			if (entityAggrBnspyTimeOpt.isPresent()){
				entityAggrBnspyTime = entityAggrBnspyTimeOpt.get();
				entityAggrBnspyTime.fromDomainForUpdate(bonusPayTime);
			}
			else {
				entityAggrBnspyTime.fromDomainForPersist(domainKey, bonusPayTime);
				entityAggrBnspyTimeList.add(entityAggrBnspyTime);
			}
		}
		
		// 縦計：勤務時間：集計乖離時間
		val divergenceTimeMap = vtWorkTime.getDivergenceTime().getDivergenceTimeList();
		if (entity.krcdtWekAggrDivgTime == null) entity.krcdtWekAggrDivgTime = new ArrayList<>();
		val entityAggrDivgTimeList = entity.krcdtWekAggrDivgTime;
		entityAggrDivgTimeList.removeIf(a -> {return !divergenceTimeMap.containsKey(a.PK.divergenceTimeNo);} );
		for (val divergenceTime : divergenceTimeMap.values()){
			KrcdtWekAggrDivgTime entityAggrDivgTime = new KrcdtWekAggrDivgTime();
			val entityAggrDivgTimeOpt = entityAggrDivgTimeList.stream()
					.filter(c -> c.PK.divergenceTimeNo == divergenceTime.getDivergenceTimeNo()).findFirst();
			if (entityAggrDivgTimeOpt.isPresent()){
				entityAggrDivgTime = entityAggrDivgTimeOpt.get();
				entityAggrDivgTime.fromDomainForUpdate(divergenceTime);
			}
			else {
				entityAggrDivgTime.fromDomainForPersist(domainKey, divergenceTime);
				entityAggrDivgTimeList.add(entityAggrDivgTime);
			}
		}
		
		// 縦計：勤務時間：集計外出
		val goOutMap = vtWorkTime.getGoOut().getGoOuts();
		if (entity.krcdtWekAggrGoout == null) entity.krcdtWekAggrGoout = new ArrayList<>();
		val entityAggrGooutList = entity.krcdtWekAggrGoout;
		entityAggrGooutList.removeIf(
				a -> {return !goOutMap.containsKey(EnumAdaptor.valueOf(a.PK.goOutReason, GoingOutReason.class));} );
		for (val goOut : goOutMap.values()){
			KrcdtWekAggrGoout entityAggrGoout = new KrcdtWekAggrGoout();
			val entityAggrGooutOpt = entityAggrGooutList.stream()
					.filter(c -> c.PK.goOutReason == goOut.getGoOutReason().value).findFirst();
			if (entityAggrGooutOpt.isPresent()){
				entityAggrGoout = entityAggrGooutOpt.get();
				entityAggrGoout.fromDomainForUpdate(goOut);
			}
			else {
				entityAggrGoout.fromDomainForPersist(domainKey, goOut);
				entityAggrGooutList.add(entityAggrGoout);
			}
		}
		
		// 縦計：勤務時間：集計割増時間
		val premiumTimeMap = vtWorkTime.getPremiumTime().getPremiumTime();
		if (entity.krcdtWekAggrPremTime == null) entity.krcdtWekAggrPremTime = new ArrayList<>();
		val entityAggrPremTimeList = entity.krcdtWekAggrPremTime;
		entityAggrPremTimeList.removeIf(a -> {return !premiumTimeMap.containsKey(a.PK.premiumTimeItemNo);} );
		for (val premiumTime : premiumTimeMap.values()){
			KrcdtWekAggrPremTime entityAggrPremTime = new KrcdtWekAggrPremTime();
			val entityAggrPremTimeOpt = entityAggrPremTimeList.stream()
					.filter(c -> c.PK.premiumTimeItemNo == premiumTime.getPremiumTimeItemNo()).findFirst();
			if (entityAggrPremTimeOpt.isPresent()){
				entityAggrPremTime = entityAggrPremTimeOpt.get();
				entityAggrPremTime.fromDomainForUpdate(premiumTime);
			}
			else {
				entityAggrPremTime.fromDomainForPersist(domainKey, premiumTime);
				entityAggrPremTimeList.add(entityAggrPremTime);
			}
		}
		
		// 縦計：勤務時間：月別実績の医療時間
		val medicalTimeMap = vtWorkTime.getMedicalTime();
		if (entity.krcdtWekMedicalTime == null) entity.krcdtWekMedicalTime = new ArrayList<>();
		val entityMedicalTimeList = entity.krcdtWekMedicalTime;
		entityMedicalTimeList.removeIf(
				a -> {return !medicalTimeMap.containsKey(EnumAdaptor.valueOf(a.PK.dayNightAtr, WorkTimeNightShift.class));} );
		for (val medicalTime : medicalTimeMap.values()){
			KrcdtWekMedicalTime entityMedicalTime = new KrcdtWekMedicalTime();
			val entityMedicalTimeOpt = entityMedicalTimeList.stream()
					.filter(c -> c.PK.dayNightAtr == medicalTime.getDayNightAtr().value).findFirst();
			if (entityMedicalTimeOpt.isPresent()){
				entityMedicalTime = entityMedicalTimeOpt.get();
				entityMedicalTime.fromDomainForUpdate(medicalTime);
			}
			else {
				entityMedicalTime.fromDomainForPersist(domainKey, medicalTime);
				entityMedicalTimeList.add(entityMedicalTime);
			}
		}
		
		// 回数集計：回数集計
		val totalCountMap = domain.getTotalCount().getTotalCountList();
		if (entity.krcdtWekTotalTimes == null) entity.krcdtWekTotalTimes = new ArrayList<>();
		val entityTotalTimesList = entity.krcdtWekTotalTimes;
		entityTotalTimesList.removeIf(a -> {return !totalCountMap.containsKey(a.PK.totalTimesNo);} );
		for (val totalCount : totalCountMap.values()){
			KrcdtWekTotalTimes entityTotalTimes = new KrcdtWekTotalTimes();
			val entityTotalTimesOpt = entityTotalTimesList.stream()
					.filter(c -> c.PK.totalTimesNo == totalCount.getTotalCountNo()).findFirst();
			if (entityTotalTimesOpt.isPresent()){
				entityTotalTimes = entityTotalTimesOpt.get();
				entityTotalTimes.fromDomainForUpdate(totalCount);
			}
			else {
				entityTotalTimes.fromDomainForPersist(domainKey, totalCount);
				entityTotalTimesList.add(entityTotalTimes);
			}
		}
		
		// 任意項目：任意項目値
		val anyItemValueMap = domain.getAnyItem().getAnyItemValues();
		if (entity.krcdtWekAnyItemValue == null) entity.krcdtWekAnyItemValue = new ArrayList<>();
		val entityAnyItemValueList = entity.krcdtWekAnyItemValue;
		entityAnyItemValueList.removeIf(a -> {return !anyItemValueMap.containsKey(a.PK.anyItemId);} );
		for (val anyItemValue : anyItemValueMap.values()){
			KrcdtWekAnyItemValue entityAnyItemValue = new KrcdtWekAnyItemValue();
			val entityAnyItemValueOpt = entityAnyItemValueList.stream()
					.filter(c -> c.PK.anyItemId == anyItemValue.getAnyItemNo()).findFirst();
			if (entityAnyItemValueOpt.isPresent()){
				entityAnyItemValue = entityAnyItemValueOpt.get();
				entityAnyItemValue.fromDomainForUpdate(anyItemValue);
			}
			else {
				entityAnyItemValue.fromDomainForPersist(domainKey, anyItemValue);
				entityAnyItemValueList.add(entityAnyItemValue);
			}
		}
		
		// 登録が必要な時、登録を実行
		if (isNeedPersist) this.getEntityManager().persist(entity);
	}
	
	/** 削除 */
	@Override
	public void remove(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate,
			int weekNo) {
		
		this.commandProxy().remove(KrcdtWekAttendanceTime.class,
				new KrcdtWekAttendanceTimePK(
						employeeId,
						yearMonth.v(),
						closureId.value,
						closureDate.getClosureDay().v(),
						(closureDate.getLastDayOfMonth() ? 1 : 0),
						weekNo));
	}

	/** 削除　（締め） */
	@Override
	public void removeByClosure(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		
		this.getEntityManager().createQuery(DELETE_BY_CLOSURE)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.setParameter("closureId", closureId.value)
				.setParameter("closureDay", closureDate.getClosureDay().v())
				.setParameter("isLastDay", (closureDate.getLastDayOfMonth() ? 1 : 0))
				.executeUpdate();
	}
	
	/** 削除　（年月） */
	@Override
	public void removeByYearMonth(String employeeId, YearMonth yearMonth) {
		
		this.getEntityManager().createQuery(DELETE_BY_YEARMONTH)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.executeUpdate();
	}
}
