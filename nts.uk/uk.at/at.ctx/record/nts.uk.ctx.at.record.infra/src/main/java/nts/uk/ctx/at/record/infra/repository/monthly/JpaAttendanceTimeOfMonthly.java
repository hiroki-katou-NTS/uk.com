package nts.uk.ctx.at.record.infra.repository.monthly;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculation;
import nts.uk.ctx.at.record.dom.monthly.TimeMonthWithCalculationAndMinus;
import nts.uk.ctx.at.record.dom.monthly.calc.AggregateTotalTimeSpentAtWork;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.IrregularWorkingTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.ExcessFlexAtr;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexCarryforwardTime;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTime;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfExcessOutsideTime;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.PrescribedWorkingTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.WorkTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave.AggregateHolidayWorkTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave.HolidayWorkTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.AggregateOverTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.OverTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.vacationusetime.AnnualLeaveUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.vacationusetime.CompensatoryLeaveUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.vacationusetime.RetentionYearlyUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.vacationusetime.SpecialHolidayUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.vacationusetime.VacationUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.VerticalTotalOfMonthly;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTime;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.KrcdtMonAggrTotalSpt;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.actualworkingtime.KrcdtMonRegIrregTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.flex.KrcdtMonFlexTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.KrcdtMonAggrTotalWrk;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.hdwkandcompleave.KrcdtMonAggrHdwkTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.hdwkandcompleave.KrcdtMonAggrHdwkTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.hdwkandcompleave.KrcdtMonHdwkTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtime.KrcdtMonAggrOverTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtime.KrcdtMonAggrOverTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtime.KrcdtMonOverTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.vacationusetime.KrcdtMonVactUseTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * リポジトリ実装：月別実績の勤怠時間
 * @author shuichi_ishida
 */
@Stateless
public class JpaAttendanceTimeOfMonthly extends JpaRepository implements AttendanceTimeOfMonthlyRepository {

	private static final String FIND_BY_YEAR_MONTH = "SELECT a FROM KrcdtMonAttendanceTime a "
			+ "WHERE a.PK.employeeId = :employeeId "
			+ "AND a.PK.yearMonth = :yearMonth "
			+ "ORDER BY a.startYmd";

	private static final String DELETE_BY_YEAR_MONTH = "DELETE FROM KrcdtMonAttendanceTime a "
			+ "WHERE a.PK.employeeId = :employeeId "
			+ "AND a.PK.yearMonth = :yearMonth ";

	/** 検索 */
	@Override
	public Optional<AttendanceTimeOfMonthly> find(String employeeId, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate) {
		
		return this.queryProxy()
				.find(new KrcdtMonAttendanceTimePK(
						employeeId,
						yearMonth.v(),
						closureId.value,
						closureDate.getClosureDay().v(),
						(closureDate.getLastDayOfMonth() ? 1 : 0)),
					KrcdtMonAttendanceTime.class)
				.map(c -> toDomain(c));
	}

	/** 検索　（年月） */
	@Override
	public List<AttendanceTimeOfMonthly> findByYearMonth(String employeeId, YearMonth yearMonth) {
		
		return this.queryProxy().query(FIND_BY_YEAR_MONTH, KrcdtMonAttendanceTime.class)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.getList(c -> toDomain(c));
	}
	
	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthly attendanceTimeOfMonthly){
		this.commandProxy().insert(toEntity(attendanceTimeOfMonthly, false));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthly attendanceTimeOfMonthly){
		this.toEntity(attendanceTimeOfMonthly, true);
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

	/**
	 * エンティティ→ドメイン
	 * @param entity エンティティ：月別実績の勤怠時間
	 * @return ドメイン：月別実績の勤怠時間
	 */
	private static AttendanceTimeOfMonthly toDomain(KrcdtMonAttendanceTime entity){

		// 月別実績の月の計算
		val monthlyCalculation = MonthlyCalculation.of(
				toDomainRegularAndIrregularTimeOfMonthly(entity),
				toDomainFlexTimeOfMonthly(entity),
				new AttendanceTimeMonth(entity.statutoryWorkingTime),
				toDomainAggregateTotalWorkingTime(entity),
				toDomainAggregateTotalTimeSpentAtWork(entity));
		
		// 月別実績の勤怠時間
		//*****（未）　縦計は、永続化未実装。(2018/1/22 shuichi_ishida)
		val domain = AttendanceTimeOfMonthly.of(
				entity.PK.employeeId,
				new YearMonth(entity.PK.yearMonth),
				ClosureId.valueOf(entity.PK.closureId),
				new ClosureDate(entity.PK.closureDay, (entity.PK.isLastDay != 0)),
				new DatePeriod(entity.startYmd, entity.endYmd),
				monthlyCalculation,
				new VerticalTotalOfMonthly(),
				new AttendanceDaysMonth(entity.aggregateDays));
		return domain;
	}
	
	/**
	 * エンティティ→ドメイン　（月別実績の通常変形時間）
	 * @param parentEntity エンティティ：月別実績の勤怠時間
	 * @return ドメイン：月別実績の通常変形時間
	 */
	private static RegularAndIrregularTimeOfMonthly toDomainRegularAndIrregularTimeOfMonthly(KrcdtMonAttendanceTime parentEntity){

		val entity = parentEntity.krcdtMonRegIrregTime;
		if (entity == null) return new RegularAndIrregularTimeOfMonthly();

		// 月別実績の変形労働時間
		val irregularWorkingTime = IrregularWorkingTimeOfMonthly.of(
				new AttendanceTimeMonth(entity.multiMonthIrregularMiddleTime),
				new AttendanceTimeMonth(entity.irregularPeriodCarryforwardTime),
				new AttendanceTimeMonth(entity.irregularWorkingShortageTime),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(entity.irregularLegalOverTime),
						new AttendanceTimeMonth(entity.calcIrregularLegalOverTime))
				);
		
		// 月別実績の通常変形時間
		val domain = RegularAndIrregularTimeOfMonthly.of(
				new AttendanceTimeMonth(entity.monthlyTotalPremiumTime),
				new AttendanceTimeMonth(entity.weeklyTotalPremiumTime),
				irregularWorkingTime);
		return domain;
	}

	/**
	 * エンティティ→ドメイン　（月別実績のフレックス時間）
	 * @param parentEntity エンティティ：月別実績の勤怠時間
	 * @return ドメイン：月別実績のフレックス時間
	 */
	private static FlexTimeOfMonthly toDomainFlexTimeOfMonthly(KrcdtMonAttendanceTime parentEntity){
		
		val entity = parentEntity.krcdtMonFlexTime;
		if (entity == null) return new FlexTimeOfMonthly();
		
		val domain = FlexTimeOfMonthly.of(
				FlexTime.of(
						new TimeMonthWithCalculationAndMinus(
								new AttendanceTimeMonthWithMinus(entity.flexTime),
								new AttendanceTimeMonthWithMinus(entity.calcFlexTime)),
						new AttendanceTimeMonth(entity.beforeFlexTime),
						new AttendanceTimeMonthWithMinus(entity.legalFlexTime),
						new AttendanceTimeMonthWithMinus(entity.illegalFlexTime)),
				new AttendanceTimeMonth(entity.flexExcessTime),
				new AttendanceTimeMonth(entity.flexShortageTime),
				FlexCarryforwardTime.of(
						new AttendanceTimeMonth(entity.flexCarryforwardWorkTime),
						new AttendanceTimeMonth(entity.flexCarryforwardTime),
						new AttendanceTimeMonth(entity.flexCarryforwardShortageTime)),
				FlexTimeOfExcessOutsideTime.of(
						EnumAdaptor.valueOf(entity.excessFlexAtr, ExcessFlexAtr.class),
						new AttendanceTimeMonth(entity.principleTime),
						new AttendanceTimeMonth(entity.forConvenienceTime)));
		return domain;
	}
	
	/**
	 * エンティティ→ドメイン　（集計総労働時間）
	 * @param parentEntity エンティティ：月別実績の勤怠時間
	 * @return ドメイン：集計総労働時間
	 */
	private static AggregateTotalWorkingTime toDomainAggregateTotalWorkingTime(KrcdtMonAttendanceTime parentEntity){

		val entity = parentEntity.krcdtMonAggrTotalWrk;
		if (entity == null) return new AggregateTotalWorkingTime();

		// 月別実績の就業時間
		val workTime = WorkTimeOfMonthly.of(
				new AttendanceTimeMonth(entity.workTime),
				new AttendanceTimeMonth(entity.withinPrescribedPremiumTime));

		// 月別実績の所定労働時間
		val prescribedWorkingTime = PrescribedWorkingTimeOfMonthly.of(
				new AttendanceTimeMonth(entity.schedulePrescribedWorkingTime),
				new AttendanceTimeMonth(entity.recordPrescribedWorkingTime));
		
		// 集計総労働時間
		val domain = AggregateTotalWorkingTime.of(
				workTime,
				toDomainOverTimeOfMonthly(parentEntity),
				toDomainHolidayWorkTimeOfMonthly(parentEntity),
				toDomainVacationUseTimeOfMonthly(parentEntity),
				new AttendanceTimeMonth(entity.totalWorkingTime),
				prescribedWorkingTime);
		return domain;
	}
	
	/**
	 * エンティティ→ドメイン　（月別実績の残業時間）
	 * @param entity エンティティ：月別実績の勤怠時間
	 * @param entityAggrs エンティティ：集計残業時間
	 * @return ドメイン：月別実績の残業時間
	 */
	private static OverTimeOfMonthly toDomainOverTimeOfMonthly(KrcdtMonAttendanceTime parentEntity){
		
		val entity = parentEntity.krcdtMonOverTime;
		if (entity == null) return new OverTimeOfMonthly();
		
		val domain = OverTimeOfMonthly.of(
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(entity.totalOverTime),
						new AttendanceTimeMonth(entity.calcTotalOverTime)),
				new AttendanceTimeMonth(entity.beforeOverTime),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(entity.totalTransferOverTime),
						new AttendanceTimeMonth(entity.calcTotalTransferOverTime)),
				parentEntity.krcdtMonAggrOverTimes.stream()
					.map(c -> toDomainAggregateOverTime(c)).collect(Collectors.toList()));
		return domain;
	}
	
	/**
	 * エンティティ→ドメイン　（集計残業時間）
	 * @param entity エンティティ：集計残業時間
	 * @return ドメイン：集計残業時間
	 */
	private static AggregateOverTime toDomainAggregateOverTime(KrcdtMonAggrOverTime entity){
		
		val domain = AggregateOverTime.of(
				new OverTimeFrameNo(entity.PK.overTimeFrameNo),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(entity.overTime),
						new AttendanceTimeMonth(entity.calcOverTime)),
				new AttendanceTimeMonth(entity.beforeOverTime),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(entity.transferOverTime),
						new AttendanceTimeMonth(entity.calcTransferOverTime)),
				new AttendanceTimeMonth(entity.legalOverTime),
				new AttendanceTimeMonth(entity.legalTransferOverTime));
		return domain;
	}
	
	/**
	 * エンティティ→ドメイン　（月別実績の休出時間）
	 * @param parentEntity エンティティ：月別実績の勤怠時間
	 * @return ドメイン：月別実績の休出時間
	 */
	private static HolidayWorkTimeOfMonthly toDomainHolidayWorkTimeOfMonthly(KrcdtMonAttendanceTime parentEntity){
		
		val entity = parentEntity.krcdtMonHdwkTime;
		if (entity == null) return new HolidayWorkTimeOfMonthly();
		
		val domain = HolidayWorkTimeOfMonthly.of(
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(entity.totalHolidayWorkTime),
						new AttendanceTimeMonth(entity.calcTotalHolidayWorkTime)),
				new AttendanceTimeMonth(entity.beforeHolidayWorkTime),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(entity.totalTransferTime),
						new AttendanceTimeMonth(entity.calcTotalTransferTime)),
				parentEntity.krcdtMonAggrHdwkTimes.stream()
					.map(c -> toDomainAggregateHolidayWorkTime(c)).collect(Collectors.toList()));
		return domain;
	}
	
	/**
	 * エンティティ→ドメイン　（集計休出時間）
	 * @param entity エンティティ：集計休出時間
	 * @return ドメイン：集計休出時間
	 */
	private static AggregateHolidayWorkTime toDomainAggregateHolidayWorkTime(KrcdtMonAggrHdwkTime entity){
		
		val domain = AggregateHolidayWorkTime.of(
				new HolidayWorkFrameNo(entity.PK.holidayWorkFrameNo),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(entity.holidayWorkTime),
						new AttendanceTimeMonth(entity.calcHolidayWorkTime)),
				new AttendanceTimeMonth(entity.beforeHolidayWorkTime),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(entity.transferTime),
						new AttendanceTimeMonth(entity.calcTransferTime)),
				new AttendanceTimeMonth(entity.legalHolidayWorkTime),
				new AttendanceTimeMonth(entity.legalTransferHolidayWorkTime));
		return domain;
	}
	
	/**
	 * エンティティ→ドメイン　（月別実績の休暇使用時間）
	 * @param parentEntity エンティティ：月別実績の勤怠時間
	 * @return ドメイン：月別実績の休暇使用時間
	 */
	private static VacationUseTimeOfMonthly toDomainVacationUseTimeOfMonthly(KrcdtMonAttendanceTime parentEntity){
		
		val entity = parentEntity.krcdtMonVactUseTime;
		if (entity == null) return new VacationUseTimeOfMonthly();
		
		val domain = VacationUseTimeOfMonthly.of(
				AnnualLeaveUseTimeOfMonthly.of(
						new AttendanceTimeMonth(entity.annualLeaveUseTime)),
				RetentionYearlyUseTimeOfMonthly.of(
						new AttendanceTimeMonth(entity.retentionYearlyUseTime)),
				SpecialHolidayUseTimeOfMonthly.of(
						new AttendanceTimeMonth(entity.specialHolidayUseTime)),
				CompensatoryLeaveUseTimeOfMonthly.of(
						new AttendanceTimeMonth(entity.compensatoryLeaveUseTime)));
		return domain;
	}
	
	/**
	 * エンティティ→ドメイン　（集計総拘束時間）
	 * @param parentEntity エンティティ：月別実績の勤怠時間
	 * @return ドメイン：集計総拘束時間
	 */
	private static AggregateTotalTimeSpentAtWork toDomainAggregateTotalTimeSpentAtWork(KrcdtMonAttendanceTime parentEntity){
		
		val entity = parentEntity.krcdtMonAggrTotalSpt;
		if (entity == null) return new AggregateTotalTimeSpentAtWork();
		
		val domain = AggregateTotalTimeSpentAtWork.of(
				new AttendanceTimeMonth(entity.overTimeSpentAtWork),
				new AttendanceTimeMonth(entity.midnightTimeSpentAtWork),
				new AttendanceTimeMonth(entity.holidayTimeSpentAtWork),
				new AttendanceTimeMonth(entity.varienceTimeSpentAtWork),
				new AttendanceTimeMonth(entity.totalTimeSpentAtWork));
		return domain;
	}

	/**
	 * ドメイン→エンティティ
	 * @param domain ドメイン：月別実績の勤怠時間
	 * @param execUpdate 更新を実行する
	 * @return エンティティ：月別実績の勤怠時間
	 */
	private KrcdtMonAttendanceTime toEntity(AttendanceTimeOfMonthly domain, boolean execUpdate){

		// 締め日付
		val closureDate = domain.getClosureDate();
		
		// キー
		val key = new KrcdtMonAttendanceTimePK(
				domain.getEmployeeId(),
				domain.getYearMonth().v(),
				domain.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));

		// 月別実績の月の計算
		val monthlyCalculation = domain.getMonthlyCalculation();
		
		KrcdtMonAttendanceTime entity;
		if (execUpdate){
			entity = this.queryProxy().find(key, KrcdtMonAttendanceTime.class).get();
		}
		else {
			entity = new KrcdtMonAttendanceTime();
			entity.PK = key;
		}
		entity.startYmd = domain.getDatePeriod().start();
		entity.endYmd = domain.getDatePeriod().end();
		entity.aggregateDays = domain.getAggregateDays().v();
		entity.statutoryWorkingTime = monthlyCalculation.getStatutoryWorkingTime().v();
		if (execUpdate) this.commandProxy().update(entity);
		return entity;
	}

	/**
	 * 登録および更新
	 * @param domain ドメイン：月別実績の勤怠時間
	 */
	@Override
	public void persistAndUpdate(AttendanceTimeOfMonthly domain){

		// 締め日付
		val closureDate = domain.getClosureDate();
		
		// キー
		val key = new KrcdtMonAttendanceTimePK(
				domain.getEmployeeId(),
				domain.getYearMonth().v(),
				domain.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));

		// 月別実績の月の計算
		val monthlyCalculation = domain.getMonthlyCalculation();
		
		// 登録・更新を判断　および　キー設定・子初期設定
		boolean isNeedPersist = false;
		KrcdtMonAttendanceTime entity;
		entity = this.getEntityManager().find(KrcdtMonAttendanceTime.class, key);
		if (entity == null){
			isNeedPersist = true;
			entity = new KrcdtMonAttendanceTime();
			entity.PK = key;
			entity.krcdtMonRegIrregTime = new KrcdtMonRegIrregTime();
			entity.krcdtMonFlexTime = new KrcdtMonFlexTime();
			entity.krcdtMonVactUseTime = new KrcdtMonVactUseTime();
			entity.krcdtMonAggrTotalWrk = new KrcdtMonAggrTotalWrk();
			entity.krcdtMonOverTime = new KrcdtMonOverTime();
			entity.krcdtMonAggrOverTimes = new ArrayList<>();
			entity.krcdtMonHdwkTime = new KrcdtMonHdwkTime();
			entity.krcdtMonAggrHdwkTimes = new ArrayList<>();
			entity.krcdtMonAggrTotalSpt = new KrcdtMonAggrTotalSpt();
		}
		
		// 登録・更新値の設定
		entity.startYmd = domain.getDatePeriod().start();
		entity.endYmd = domain.getDatePeriod().end();
		entity.aggregateDays = domain.getAggregateDays().v();
		entity.statutoryWorkingTime = monthlyCalculation.getStatutoryWorkingTime().v();

		// 実働時間：月別実績の通常変形時間　値設定
		val actualWorkingTime = monthlyCalculation.getActualWorkingTime();
		val irregularWorkingTime = actualWorkingTime.getIrregularWorkingTime();
		val entityRegIrregTime = entity.krcdtMonRegIrregTime;
		if (isNeedPersist) entityRegIrregTime.PK = key;
		entityRegIrregTime.weeklyTotalPremiumTime = actualWorkingTime.getWeeklyTotalPremiumTime().v();
		entityRegIrregTime.monthlyTotalPremiumTime = actualWorkingTime.getMonthlyTotalPremiumTime().v();
		entityRegIrregTime.multiMonthIrregularMiddleTime = irregularWorkingTime.getMultiMonthIrregularMiddleTime().v();
		entityRegIrregTime.irregularPeriodCarryforwardTime = irregularWorkingTime.getIrregularPeriodCarryforwardTime().v();
		entityRegIrregTime.irregularWorkingShortageTime = irregularWorkingTime.getIrregularWorkingShortageTime().v();
		entityRegIrregTime.irregularLegalOverTime = irregularWorkingTime.getIrregularLegalOverTime().getTime().v();
		entityRegIrregTime.calcIrregularLegalOverTime = irregularWorkingTime.getIrregularLegalOverTime().getCalcTime().v();
		
		// 月別実績のフレックス時間
		val flexTimeOfMonthly = monthlyCalculation.getFlexTime();
		val flexTime = flexTimeOfMonthly.getFlexTime();
		val flexCarryForwardTime = flexTimeOfMonthly.getFlexCarryforwardTime();
		val flexTimeOfExcessOutsideTime = flexTimeOfMonthly.getFlexTimeOfExcessOutsideTime();
		val entityFlexTime = entity.krcdtMonFlexTime;
		if (isNeedPersist) entityFlexTime.PK = key;
		entityFlexTime.flexTime = flexTime.getFlexTime().getTime().v();
		entityFlexTime.calcFlexTime = flexTime.getFlexTime().getCalcTime().v();
		entityFlexTime.beforeFlexTime = flexTime.getBeforeFlexTime().v();
		entityFlexTime.legalFlexTime = flexTime.getLegalFlexTime().v();
		entityFlexTime.illegalFlexTime = flexTime.getIllegalFlexTime().v();
		entityFlexTime.flexExcessTime = flexTimeOfMonthly.getFlexExcessTime().v();
		entityFlexTime.flexShortageTime = flexTimeOfMonthly.getFlexShortageTime().v();
		entityFlexTime.flexCarryforwardWorkTime = flexCarryForwardTime.getFlexCarryforwardWorkTime().v();
		entityFlexTime.flexCarryforwardTime = flexCarryForwardTime.getFlexCarryforwardTime().v();
		entityFlexTime.flexCarryforwardShortageTime = flexCarryForwardTime.getFlexCarryforwardShortageTime().v();
		entityFlexTime.excessFlexAtr = flexTimeOfExcessOutsideTime.getExcessFlexAtr().value;
		entityFlexTime.principleTime = flexTimeOfExcessOutsideTime.getPrincipleTime().v();
		entityFlexTime.forConvenienceTime = flexTimeOfExcessOutsideTime.getForConvenienceTime().v();
		
		// 総労働時間：月別実績の休暇使用時間
		val totalWorkingTime = monthlyCalculation.getTotalWorkingTime();
		val vacationUseTime = totalWorkingTime.getVacationUseTime();
		val annualLeaveUseTime = vacationUseTime.getAnnualLeave();
		val retentionYearlyUseTime = vacationUseTime.getRetentionYearly();
		val specialHolidayUseTime = vacationUseTime.getSpecialHoliday();
		val compensatoryLeaveUseTime = vacationUseTime.getCompensatoryLeave();
		val entityVactUseTime = entity.krcdtMonVactUseTime;
		if (isNeedPersist) entityVactUseTime.PK = key;
		entityVactUseTime.annualLeaveUseTime = annualLeaveUseTime.getUseTime().v();
		entityVactUseTime.retentionYearlyUseTime = retentionYearlyUseTime.getUseTime().v();
		entityVactUseTime.specialHolidayUseTime = specialHolidayUseTime.getUseTime().v();
		entityVactUseTime.compensatoryLeaveUseTime = compensatoryLeaveUseTime.getUseTime().v();
		
		// 総労働時間：集計総労働時間
		val workTime = totalWorkingTime.getWorkTime();
		val prescribedWorkingTime = totalWorkingTime.getPrescribedWorkingTime();
		val entityAggrTotalWrk = entity.krcdtMonAggrTotalWrk;
		if (isNeedPersist) entityAggrTotalWrk.PK = key;
		entityAggrTotalWrk.totalWorkingTime = totalWorkingTime.getTotalWorkingTime().v();
		entityAggrTotalWrk.workTime = workTime.getWorkTime().v();
		entityAggrTotalWrk.withinPrescribedPremiumTime = workTime.getWithinPrescribedPremiumTime().v();
		entityAggrTotalWrk.schedulePrescribedWorkingTime = prescribedWorkingTime.getSchedulePrescribedWorkingTime().v();
		entityAggrTotalWrk.recordPrescribedWorkingTime = prescribedWorkingTime.getRecordPrescribedWorkingTime().v();
		
		// 総労働時間：残業時間：月別実績の残業時間
		val overTime = totalWorkingTime.getOverTime();
		val entityOverTime = entity.krcdtMonOverTime;
		if (isNeedPersist) entityOverTime.PK = key;
		entityOverTime.totalOverTime = overTime.getTotalOverTime().getTime().v();
		entityOverTime.calcTotalOverTime = overTime.getTotalOverTime().getCalcTime().v();
		entityOverTime.beforeOverTime = overTime.getBeforeOverTime().v();
		entityOverTime.totalTransferOverTime = overTime.getTotalTransferOverTime().getTime().v();
		entityOverTime.calcTotalTransferOverTime = overTime.getTotalTransferOverTime().getCalcTime().v();

		// 総労働時間：残業時間：集計残業時間
		val aggrOverTimeMap = overTime.getAggregateOverTimeMap();
		val entityOverTimes = entity.krcdtMonAggrOverTimes;
		val itrOverTimes = entityOverTimes.iterator();
		while (itrOverTimes.hasNext()){
			val aggrOverTime = itrOverTimes.next();
			if (!aggrOverTimeMap.containsKey(new OverTimeFrameNo(aggrOverTime.PK.overTimeFrameNo))){
				itrOverTimes.remove();
			}
		}
		for (val aggrOverTime : aggrOverTimeMap.values()){
			KrcdtMonAggrOverTime entityAggrOverTime = new KrcdtMonAggrOverTime();
			boolean isAddAggrOverTime = false;
			val entityAggrOverTimeOpt = entityOverTimes.stream()
					.filter(c -> c.PK.overTimeFrameNo == aggrOverTime.getOverTimeFrameNo().v()).findFirst();
			if (entityAggrOverTimeOpt.isPresent()){
				entityAggrOverTime = entityAggrOverTimeOpt.get();
			}
			else {
				isAddAggrOverTime = true;
				entityAggrOverTime.PK = new KrcdtMonAggrOverTimePK(
						domain.getEmployeeId(),
						domain.getYearMonth().v(),
						domain.getClosureId().value,
						closureDate.getClosureDay().v(),
						(closureDate.getLastDayOfMonth() ? 1 : 0),
						aggrOverTime.getOverTimeFrameNo().v());
			}
			entityAggrOverTime.overTime = aggrOverTime.getOverTime().getTime().v();
			entityAggrOverTime.calcOverTime = aggrOverTime.getOverTime().getCalcTime().v();
			entityAggrOverTime.beforeOverTime = aggrOverTime.getBeforeOverTime().v();
			entityAggrOverTime.transferOverTime = aggrOverTime.getTransferOverTime().getTime().v();
			entityAggrOverTime.calcTransferOverTime = aggrOverTime.getTransferOverTime().getCalcTime().v();
			entityAggrOverTime.legalOverTime = aggrOverTime.getLegalOverTime().v();
			entityAggrOverTime.legalTransferOverTime = aggrOverTime.getLegalTransferOverTime().v();
			if (isAddAggrOverTime) entityOverTimes.add(entityAggrOverTime);
		}
		
		// 総労働時間：休出・代休：月別実績の休出時間
		val holidayWorkTime = totalWorkingTime.getHolidayWorkTime();
		val entityHdwkTime = entity.krcdtMonHdwkTime;
		if (isNeedPersist) entityHdwkTime.PK = key;
		entityHdwkTime.totalHolidayWorkTime = holidayWorkTime.getTotalHolidayWorkTime().getTime().v();
		entityHdwkTime.calcTotalHolidayWorkTime = holidayWorkTime.getTotalHolidayWorkTime().getCalcTime().v();
		entityHdwkTime.beforeHolidayWorkTime = holidayWorkTime.getBeforeHolidayWorkTime().v();
		entityHdwkTime.totalTransferTime = holidayWorkTime.getTotalTransferTime().getTime().v();
		entityHdwkTime.calcTotalTransferTime = holidayWorkTime.getTotalTransferTime().getCalcTime().v();
		
		// 総労働時間：休出・代休：集計休出時間
		val aggrHolidayWorkTimeMap = holidayWorkTime.getAggregateHolidayWorkTimeMap();
		val entityHdwkTimes = entity.krcdtMonAggrHdwkTimes;
		val itrHdwkTimes = entityHdwkTimes.iterator();
		while (itrHdwkTimes.hasNext()){
			val aggrHdwkTime = itrHdwkTimes.next();
			if (!aggrHolidayWorkTimeMap.containsKey(new HolidayWorkFrameNo(aggrHdwkTime.PK.holidayWorkFrameNo))){
				itrHdwkTimes.remove();
			}
		}
		for (val aggrHolidayWorkTime : aggrHolidayWorkTimeMap.values()){
			KrcdtMonAggrHdwkTime entityAggrHdwkTime = new KrcdtMonAggrHdwkTime();
			boolean isAddAggrHdwkTime = false;
			val entityAggrHdwkTimeOpt = entityHdwkTimes.stream()
					.filter(c -> c.PK.holidayWorkFrameNo == aggrHolidayWorkTime.getHolidayWorkFrameNo().v()).findFirst();
			if (entityAggrHdwkTimeOpt.isPresent()){
				entityAggrHdwkTime = entityAggrHdwkTimeOpt.get();
			}
			else {
				isAddAggrHdwkTime = true;
				entityAggrHdwkTime.PK = new KrcdtMonAggrHdwkTimePK(
						domain.getEmployeeId(),
						domain.getYearMonth().v(),
						domain.getClosureId().value,
						closureDate.getClosureDay().v(),
						(closureDate.getLastDayOfMonth() ? 1 : 0),
						aggrHolidayWorkTime.getHolidayWorkFrameNo().v());
			}
			entityAggrHdwkTime.holidayWorkTime = aggrHolidayWorkTime.getHolidayWorkTime().getTime().v();
			entityAggrHdwkTime.calcHolidayWorkTime = aggrHolidayWorkTime.getHolidayWorkTime().getCalcTime().v();
			entityAggrHdwkTime.beforeHolidayWorkTime = aggrHolidayWorkTime.getBeforeHolidayWorkTime().v();
			entityAggrHdwkTime.transferTime = aggrHolidayWorkTime.getTransferTime().getTime().v();
			entityAggrHdwkTime.calcTransferTime = aggrHolidayWorkTime.getTransferTime().getCalcTime().v();
			entityAggrHdwkTime.legalHolidayWorkTime = aggrHolidayWorkTime.getLegalHolidayWorkTime().v();
			entityAggrHdwkTime.legalTransferHolidayWorkTime = aggrHolidayWorkTime.getLegalTransferHolidayWorkTime().v();
			if (isAddAggrHdwkTime) entityHdwkTimes.add(entityAggrHdwkTime);
		}
		
		// 集計総拘束時間
		val totalTimeSpentAtWork = monthlyCalculation.getTotalTimeSpentAtWork();
		val entityAggrTotalSpt = entity.krcdtMonAggrTotalSpt;
		if (isNeedPersist) entityAggrTotalSpt.PK = key;
		entityAggrTotalSpt.overTimeSpentAtWork = totalTimeSpentAtWork.getOverTimeSpentAtWork().v();
		entityAggrTotalSpt.midnightTimeSpentAtWork = totalTimeSpentAtWork.getMidnightTimeSpentAtWork().v();
		entityAggrTotalSpt.holidayTimeSpentAtWork = totalTimeSpentAtWork.getHolidayTimeSpentAtWork().v();
		entityAggrTotalSpt.varienceTimeSpentAtWork = totalTimeSpentAtWork.getVarienceTimeSpentAtWork().v();
		entityAggrTotalSpt.totalTimeSpentAtWork = totalTimeSpentAtWork.getTotalTimeSpentAtWork().v();
		
		// 登録が必要な時、登録を実行
		if (isNeedPersist) this.getEntityManager().persist(entity);
	}
}
