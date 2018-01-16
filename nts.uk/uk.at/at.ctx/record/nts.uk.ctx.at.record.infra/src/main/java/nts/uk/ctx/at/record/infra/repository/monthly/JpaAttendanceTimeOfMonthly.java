package nts.uk.ctx.at.record.infra.repository.monthly;

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
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTime;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.hdwkandcompleave.KrcdtMonAggrHdwkTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtime.KrcdtMonAggrOverTime;
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
			+ "WHERE a.PK.employeeID = :employeeID "
			+ "AND a.PK.yearMonth = :yearMonth ";

	private static final String DELETE_BY_YEAR_MONTH = "DELETE FROM KrcdtMonAttendanceTime a "
			+ "WHERE a.PK.employeeID = :employeeID "
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
				.setParameter("employeeID", employeeId)
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
		.setParameter("employeeID", employeeId)
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
		val domain = AttendanceTimeOfMonthly.of(
				entity.PK.employeeId,
				new YearMonth(entity.PK.yearMonth),
				ClosureId.valueOf(entity.PK.closureId),
				new ClosureDate(entity.PK.closureDay, (entity.PK.isLastDay != 0)),
				new DatePeriod(entity.startYmd, entity.endYmd),
				monthlyCalculation,
				new AttendanceDaysMonth(entity.aggregateDays));
		return domain;
	}
	
	/**
	 * エンティティ→ドメイン　（月別実績の通常変形時間）
	 * @param paretEntity エンティティ：月別実績の勤怠時間
	 * @return ドメイン：月別実績の通常変形時間
	 */
	private static RegularAndIrregularTimeOfMonthly toDomainRegularAndIrregularTimeOfMonthly(KrcdtMonAttendanceTime paretEntity){

		val entity = paretEntity.krcdtMonRegIrregTime;
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
}
