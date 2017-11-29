package nts.uk.ctx.at.record.infra.repository.monthly;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonthly;
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
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime.AnnualLeaveUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime.CompensatoryLeaveUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime.HolidayUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime.RetentionYearlyUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime.SpecialHolidayUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave.AggregateHolidayWorkTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave.HolidayWorkTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.AggregateOverTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.OverTimeOfMonthly;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTime;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.KrcdtMonAggrTotalSpt;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.actualworkingtime.KrcdtMonRegIrregTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.flex.KrcdtMonFlexTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.KrcdtMonAggrTotalWrk;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave.KrcdtMonAggrHdwkTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave.KrcdtMonHdwkTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtime.KrcdtMonAggrOverTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtime.KrcdtMonOverTime;
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
		this.commandProxy().insert(toEntity(attendanceTimeOfMonthly));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthly attendanceTimeOfMonthly){

		// 締め日付
		ClosureDate closureDate = attendanceTimeOfMonthly.getClosureDate();
		
		// キー
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthly.getEmployeeId(),
				attendanceTimeOfMonthly.getYearMonth().v(),
				attendanceTimeOfMonthly.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));

		// 月別実績の月の計算
		MonthlyCalculation monthlyCalculation = attendanceTimeOfMonthly.getMonthlyCalculation();
		
		KrcdtMonAttendanceTime entity = this.queryProxy().find(key, KrcdtMonAttendanceTime.class).get();
		entity.aggregateDays = attendanceTimeOfMonthly.getAggregateDays().v();
		entity.statutoryWorkingTime = monthlyCalculation.getStatutoryWorkingTime().v();
		this.commandProxy().update(entity);
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
		MonthlyCalculation monthlyCalculation = MonthlyCalculation.of(
				toDomainRegularAndIrregularTimeOfMonthly(entity),
				toDomainFlexTimeOfMonthly(entity),
				new AttendanceTimeMonth(entity.statutoryWorkingTime),
				toDomainAggregateTotalWorkingTime(entity),
				toDomainAggregateTotalTimeSpentAtWork(entity));
		
		// 月別実績の勤怠時間
		AttendanceTimeOfMonthly domain = AttendanceTimeOfMonthly.of(
				entity.PK.employeeId,
				new YearMonth(entity.PK.yearMonth),
				ClosureId.valueOf(entity.PK.closureId),
				new ClosureDate(entity.PK.closureDay, (entity.PK.isLastDay != 0)),
				new DatePeriod(entity.startYmd, entity.endYmd),
				monthlyCalculation,
				new AttendanceDaysMonthly(entity.aggregateDays));
		return domain;
	}
	
	/**
	 * エンティティ→ドメイン　（月別実績の通常変形時間）
	 * @param entity エンティティ：月別実績の勤怠時間
	 * @return ドメイン：月別実績の通常変形時間
	 */
	private static RegularAndIrregularTimeOfMonthly toDomainRegularAndIrregularTimeOfMonthly(KrcdtMonAttendanceTime entity){

		KrcdtMonRegIrregTime targetEntity = entity.krcdtMonRegIrregTime;

		// 月別実績の変形労働時間
		IrregularWorkingTimeOfMonthly irregularWorkingTime = IrregularWorkingTimeOfMonthly.of(
				new AttendanceTimeMonth(targetEntity.multiMonthIrregularMiddleTime),
				new AttendanceTimeMonth(targetEntity.irregularPeriodCarryforwardTime),
				new AttendanceTimeMonth(targetEntity.irregularWorkingShortageTime),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(targetEntity.irregularLegalOverTime),
						new AttendanceTimeMonth(targetEntity.calcIrregularLegalOverTime))
				);
		
		// 月別実績の通常変形時間
		RegularAndIrregularTimeOfMonthly domain = RegularAndIrregularTimeOfMonthly.of(
				new AttendanceTimeMonth(targetEntity.monthlyTotalPremiumTime),
				new AttendanceTimeMonth(targetEntity.weeklyTotalPremiumTime),
				irregularWorkingTime);
		return domain;
	}

	/**
	 * エンティティ→ドメイン　（月別実績のフレックス時間）
	 * @param entity エンティティ：月別実績の勤怠時間
	 * @return ドメイン：月別実績のフレックス時間
	 */
	private static FlexTimeOfMonthly toDomainFlexTimeOfMonthly(KrcdtMonAttendanceTime entity){
		
		KrcdtMonFlexTime targetEntity = entity.krcdtMonFlexTime;
		
		FlexTimeOfMonthly domain = FlexTimeOfMonthly.of(
				FlexTime.of(
						new TimeMonthWithCalculationAndMinus(
								new AttendanceTimeMonthWithMinus(targetEntity.flexTime),
								new AttendanceTimeMonthWithMinus(targetEntity.calcFlexTime)),
						new AttendanceTimeMonthWithMinus(targetEntity.beforeFlexTime),
						new AttendanceTimeMonthWithMinus(targetEntity.legalFlexTime),
						new AttendanceTimeMonthWithMinus(targetEntity.illegalFlexTime)),
				new AttendanceTimeMonth(targetEntity.flexExcessTime),
				new AttendanceTimeMonth(targetEntity.flexShortageTime),
				new AttendanceTimeMonth(targetEntity.beforeFlexTime),
				FlexCarryforwardTime.of(
						new AttendanceTimeMonth(targetEntity.flexCarryforwardWorkTime),
						new AttendanceTimeMonth(targetEntity.flexCarryforwardTime),
						new AttendanceTimeMonth(targetEntity.flexCarryforwardShortageTime)),
				FlexTimeOfExcessOutsideTime.of(
						EnumAdaptor.valueOf(targetEntity.excessFlexAtr, ExcessFlexAtr.class),
						new AttendanceTimeMonth(targetEntity.principleTime),
						new AttendanceTimeMonth(targetEntity.forConvenienceTime)));
		return domain;
	}
	
	/**
	 * エンティティ→ドメイン　（集計総労働時間）
	 * @param entity エンティティ：月別実績の勤怠時間
	 * @return ドメイン：集計総労働時間
	 */
	private static AggregateTotalWorkingTime toDomainAggregateTotalWorkingTime(KrcdtMonAttendanceTime entity){

		KrcdtMonAggrTotalWrk targetEntity = entity.krcdtMonAggrTotalWrk;

		// 月別実績の就業時間
		WorkTimeOfMonthly workTime = WorkTimeOfMonthly.of(
				new AttendanceTimeMonth(targetEntity.workTime),
				new AttendanceTimeMonth(targetEntity.withinPrescribedPremiumTime));

		// 月別実績の所定労働時間
		PrescribedWorkingTimeOfMonthly prescribedWorkingTime = PrescribedWorkingTimeOfMonthly.of(
				new AttendanceTimeMonth(targetEntity.schedulePrescribedWorkingTime),
				new AttendanceTimeMonth(targetEntity.recordPrescribedWorkingTime));
		
		// 集計総労働時間
		AggregateTotalWorkingTime domain = AggregateTotalWorkingTime.of(
				workTime,
				toDomainOverTimeOfMonthly(entity),
				toDomainHolidayUseTimeOfMonthly(entity),
				toDomainHolidayWorkTimeOfMonthly(entity),
				new AttendanceTimeMonth(targetEntity.totalWorkingTime),
				prescribedWorkingTime);
		return domain;
	}
	
	/**
	 * エンティティ→ドメイン　（月別実績の残業時間）
	 * @param entity エンティティ：月別実績の勤怠時間
	 * @return ドメイン：月別実績の残業時間
	 */
	private static OverTimeOfMonthly toDomainOverTimeOfMonthly(KrcdtMonAttendanceTime entity){
		
		KrcdtMonOverTime targetEntity = entity.krcdtMonOverTime;
		
		OverTimeOfMonthly domain = OverTimeOfMonthly.of(
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(targetEntity.totalOverTime),
						new AttendanceTimeMonth(targetEntity.calcTotalOverTime)),
				new AttendanceTimeMonth(targetEntity.beforeOverTime),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(targetEntity.totalTransferOverTime),
						new AttendanceTimeMonth(targetEntity.calcTotalTransferOverTime)),
				entity.krcdtMonAggrOverTimes.stream()
					.map(c -> toDomainAggregateOverTime(c)).collect(Collectors.toList()));
		return domain;
	}
	
	/**
	 * エンティティ→ドメイン　（集計残業時間）
	 * @param entity エンティティ：集計残業時間
	 * @return ドメイン：集計残業時間
	 */
	private static AggregateOverTime toDomainAggregateOverTime(KrcdtMonAggrOverTime entity){
		
		AggregateOverTime domain = AggregateOverTime.of(
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
	 * @param entity エンティティ：月別実績の勤怠時間
	 * @return ドメイン：月別実績の休出時間
	 */
	private static HolidayWorkTimeOfMonthly toDomainHolidayWorkTimeOfMonthly(KrcdtMonAttendanceTime entity){
		
		KrcdtMonHdwkTime targetEntity = entity.krcdtMonHdwkTime;
		
		HolidayWorkTimeOfMonthly domain = HolidayWorkTimeOfMonthly.of(
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(targetEntity.totalHolidayWorkTime),
						new AttendanceTimeMonth(targetEntity.calcTotalHolidayWorkTime)),
				new AttendanceTimeMonth(targetEntity.beforeHolidayWorkTime),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(targetEntity.totalTransferTime),
						new AttendanceTimeMonth(targetEntity.calcTotalTransferTime)),
				entity.krcdtMonAggrHdwkTimes.stream()
					.map(c -> toDomainAggregateHolidayWorkTime(c)).collect(Collectors.toList()));
		return domain;
	}
	
	/**
	 * エンティティ→ドメイン　（集計休出時間）
	 * @param entity エンティティ：集計休出時間
	 * @return ドメイン：集計休出時間
	 */
	private static AggregateHolidayWorkTime toDomainAggregateHolidayWorkTime(KrcdtMonAggrHdwkTime entity){
		
		AggregateHolidayWorkTime domain = AggregateHolidayWorkTime.of(
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
	 * @param entity エンティティ：月別実績の勤怠時間
	 * @return ドメイン：月別実績の休暇使用時間
	 */
	private static HolidayUseTimeOfMonthly toDomainHolidayUseTimeOfMonthly(KrcdtMonAttendanceTime entity){
		HolidayUseTimeOfMonthly domain = HolidayUseTimeOfMonthly.of(
				AnnualLeaveUseTimeOfMonthly.of(
						new AttendanceTimeMonth(entity.krcdtMonHldyUseTime.annualLeaveUseTime)),
				RetentionYearlyUseTimeOfMonthly.of(
						new AttendanceTimeMonth(entity.krcdtMonHldyUseTime.retentionYearlyUseTime)),
				SpecialHolidayUseTimeOfMonthly.of(
						new AttendanceTimeMonth(entity.krcdtMonHldyUseTime.specialHolidayUseTime)),
				CompensatoryLeaveUseTimeOfMonthly.of(
						new AttendanceTimeMonth(entity.krcdtMonHldyUseTime.compensatoryLeaveUseTime)));
		return domain;
	}
	
	/**
	 * エンティティ→ドメイン　（集計総拘束時間）
	 * @param entity エンティティ：月別実績の勤怠時間
	 * @return ドメイン：集計総拘束時間
	 */
	private static AggregateTotalTimeSpentAtWork toDomainAggregateTotalTimeSpentAtWork(KrcdtMonAttendanceTime entity){
		KrcdtMonAggrTotalSpt targetEntity = entity.krcdtMonAggrTotalSpt;
		AggregateTotalTimeSpentAtWork domain = AggregateTotalTimeSpentAtWork.of(
				new AttendanceTimeMonth(targetEntity.overTimeSpentAtWork),
				new AttendanceTimeMonth(targetEntity.midnightTimeSpentAtWork),
				new AttendanceTimeMonth(targetEntity.holidayTimeSpentAtWork),
				new AttendanceTimeMonth(targetEntity.varienceTimeSpentAtWork),
				new AttendanceTimeMonth(targetEntity.totalTimeSpentAtWork));
		return domain;
	}

	/**
	 * ドメイン→エンティティ
	 * @param attendanceTimeOfMonthly ドメイン：月別実績の勤怠時間
	 * @return エンティティ：月別実績の勤怠時間
	 */
	private static KrcdtMonAttendanceTime toEntity(AttendanceTimeOfMonthly attendanceTimeOfMonthly){

		// 締め日付
		ClosureDate closureDate = attendanceTimeOfMonthly.getClosureDate();
		
		// キー
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthly.getEmployeeId(),
				attendanceTimeOfMonthly.getYearMonth().v(),
				attendanceTimeOfMonthly.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));

		// 月別実績の月の計算
		MonthlyCalculation monthlyCalculation = attendanceTimeOfMonthly.getMonthlyCalculation();
		
		KrcdtMonAttendanceTime entity = new KrcdtMonAttendanceTime();
		entity.PK = key;
		entity.aggregateDays = attendanceTimeOfMonthly.getAggregateDays().v();
		entity.statutoryWorkingTime = monthlyCalculation.getStatutoryWorkingTime().v();
		return entity;
	}
}
