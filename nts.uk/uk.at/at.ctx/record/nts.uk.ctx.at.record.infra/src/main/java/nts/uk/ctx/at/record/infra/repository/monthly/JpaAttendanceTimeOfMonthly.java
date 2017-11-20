package nts.uk.ctx.at.record.infra.repository.monthly;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
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
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfExcessOutsideWork;
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
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtimework.AggregateOverTimeWork;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtimework.OverTimeWorkOfMonthly;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTime;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.KrcdtAggrTotalTmSpent;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.KrcdtMonthlyCalculation;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.actualworkingtime.KrcdtIrregWrkTimeMon;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.actualworkingtime.KrcdtRegIrregTimeMon;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.flex.KrcdtFlexTimeMon;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.KrcdtAggrTotalWrkTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.KrcdtPrescrWrkTimeMon;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.KrcdtWorkTimeOfMonth;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave.KrcdtAggrHolidayWrkTm;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave.KrcdtHolidayWorkTmMon;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtimework.KrcdtAggrOverTimeWork;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtimework.KrcdtOverTimeWorkMon;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * リポジトリ実装：月別実績の勤怠時間
 * @author shuichi_ishida
 */
@Stateless
public class JpaAttendanceTimeOfMonthly extends JpaRepository implements AttendanceTimeOfMonthlyRepository {

	private static final String FIND_BY_PERIOD = "SELECT a FROM KrcdtMonAttendanceTime a "
			+ "WHERE a.PK.employeeID = :employeeID "
			+ "AND a.PK.startYmd <= :startYmd "
			+ "AND a.PK.endYmd >= :endYmd ";

	private static final String DELETE_BY_PERIOD = "DELETE FROM KrcdtMonAttendanceTime a "
			+ "WHERE a.PK.employeeID = :employeeID "
			+ "AND a.PK.startYmd <= :startYmd "
			+ "AND a.PK.endYmd >= :endYmd ";
	
	/** 検索　（期間） */
	@Override
	public List<AttendanceTimeOfMonthly> findByPeriod(String employeeID, DatePeriod datePeriod){
		return this.queryProxy().query(FIND_BY_PERIOD, KrcdtMonAttendanceTime.class)
				.setParameter("emmployeeID", employeeID)
				.setParameter("startYmd", datePeriod.start())
				.setParameter("endYmd", datePeriod.end())
				.getList(c -> toDomain(c));
	}

	/** 検索　（キー） */
	@Override
	public Optional<AttendanceTimeOfMonthly> findByPK(String employeeID, DatePeriod datePeriod) {
		return this.queryProxy()
				.find(new KrcdtMonAttendanceTimePK(employeeID, datePeriod.start(), datePeriod.end()), KrcdtMonAttendanceTime.class)
				.map(c -> toDomain(c));
	}
	
	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthly attendanceTimeOfMonthly){
		this.commandProxy().insert(toEntity(attendanceTimeOfMonthly));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthly attendanceTimeOfMonthly){
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthly.getEmployeeID(),
				attendanceTimeOfMonthly.getDatePeriod().start(),
				attendanceTimeOfMonthly.getDatePeriod().end());
		KrcdtMonAttendanceTime entity = this.queryProxy().find(key, KrcdtMonAttendanceTime.class).get();
		entity.aggregateDays = attendanceTimeOfMonthly.getAggregateDays().v();
		this.commandProxy().update(entity);
	}

	/** 削除 */
	@Override
	public void removeByPeriod(String employeeID, DatePeriod datePeriod){
		this.getEntityManager().createQuery(DELETE_BY_PERIOD)
			.setParameter("employeeID", employeeID)
			.setParameter("startYmd", datePeriod.start())
			.setParameter("endYmd", datePeriod.end())
			.executeUpdate();
	}

	/**
	 * エンティティ→ドメイン
	 * @param entity エンティティ：月別実績の勤怠時間
	 * @return ドメイン：月別実績の勤怠時間
	 */
	private static AttendanceTimeOfMonthly toDomain(KrcdtMonAttendanceTime entity){
		AttendanceTimeOfMonthly domain = AttendanceTimeOfMonthly.of(
				entity.PK.employeeID,
				new DatePeriod(entity.PK.startYmd, entity.PK.endYmd),
				toDomainMonthlyCalculation(entity),
				new AttendanceDaysMonthly(entity.aggregateDays));
		return domain;
	}

	/**
	 * エンティティ→ドメイン　（月別実績の月の計算）
	 * @param entity エンティティ：月別実績の勤怠時間
	 * @return ドメイン：月別実績の月の計算
	 */
	private static MonthlyCalculation toDomainMonthlyCalculation(KrcdtMonAttendanceTime entity){
		KrcdtMonthlyCalculation targetEntity = entity.krcdtMonthlyCalculation;
		MonthlyCalculation domain = MonthlyCalculation.of(
				toDomainRegularAndIrregularTimeOfMonthly(entity),
				toDomainFlexTimeOfMonthly(entity),
				new AttendanceTimeMonth(targetEntity.statutoryWorkingTime),
				toDomainAggregateTotalWorkingTime(entity),
				toDomainAggregateTotalTimeSpentAtWork(entity));
		return domain;
	}
	
	/**
	 * エンティティ→ドメイン　（月別実績のフレックス時間）
	 * @param entity エンティティ：月別実績の勤怠時間
	 * @return ドメイン：月別実績のフレックス時間
	 */
	private static FlexTimeOfMonthly toDomainFlexTimeOfMonthly(KrcdtMonAttendanceTime entity){
		KrcdtFlexTimeMon targetEntity = entity.krcdtFlexTimeMon;
		FlexTimeOfMonthly domain = FlexTimeOfMonthly.of(
				FlexTime.of(
						new TimeMonthWithCalculationAndMinus(
								new AttendanceTimeMonthWithMinus(targetEntity.flexTime),
								new AttendanceTimeMonthWithMinus(targetEntity.flexTimeCalc)),
						new AttendanceTimeMonthWithMinus(targetEntity.calcBeforeFlexTime),
						new AttendanceTimeMonthWithMinus(targetEntity.calcWithinStatutoryFlexTime),
						new AttendanceTimeMonthWithMinus(targetEntity.calcExcessOfStatutoryFlexTime)),
				new AttendanceTimeMonth(targetEntity.flexExcessTime),
				new AttendanceTimeMonth(targetEntity.flexShortageTime),
				new AttendanceTimeMonth(targetEntity.beforeFlexTime),
				FlexCarryforwardTime.of(
						new AttendanceTimeMonth(targetEntity.flexCarryforwardWorkTime),
						new AttendanceTimeMonth(targetEntity.flexCarryforwardTime),
						new AttendanceTimeMonth(targetEntity.flexCarryforwardShortageTime)),
				FlexTimeOfExcessOutsideWork.of(
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
		KrcdtAggrTotalWrkTime targetEntity = entity.krcdtAggrTotalWrkTime;
		AggregateTotalWorkingTime domain = AggregateTotalWorkingTime.of(
				toDomainWorkTimeOfMonthly(entity),
				toDomainOverTimeWorkOfMonthly(entity),
				toDomainHolidayUseTimeOfMonthly(entity),
				toDomainHolidayWorkTimeOfMonthly(entity),
				new AttendanceTimeMonth(targetEntity.totalWorkingTime),
				toDomainPrescribedWorkingTimeOfMonthly(entity));
		return domain;
	}
	
	/**
	 * エンティティ→ドメイン　（月別実績の就業時間）
	 * @param entity エンティティ：月別実績の勤怠時間
	 * @return ドメイン：月別実績の就業時間
	 */
	private static WorkTimeOfMonthly toDomainWorkTimeOfMonthly(KrcdtMonAttendanceTime entity){
		KrcdtWorkTimeOfMonth targetEntity = entity.krcdtWorkTimeOfMonth;
		WorkTimeOfMonthly domain = WorkTimeOfMonthly.of(
				new AttendanceTimeMonth(targetEntity.workTime),
				new AttendanceTimeMonth(targetEntity.withinPrescribedPremiumTime));
		return domain;
	}
	
	/**
	 * エンティティ→ドメイン　（月別実績の残業時間）
	 * @param entity エンティティ：月別実績の勤怠時間
	 * @return ドメイン：月別実績の残業時間
	 */
	private static OverTimeWorkOfMonthly toDomainOverTimeWorkOfMonthly(KrcdtMonAttendanceTime entity){
		KrcdtOverTimeWorkMon targetEntity = entity.krcdtOverTimeWorkMon;
		OverTimeWorkOfMonthly domain = OverTimeWorkOfMonthly.of(
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(targetEntity.totalOverTimeWork),
						new AttendanceTimeMonth(targetEntity.totalOverTimeWorkCalc)),
				new AttendanceTimeMonth(targetEntity.beforeOverTimeWork),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(targetEntity.transferTotalOverTimeWork),
						new AttendanceTimeMonth(targetEntity.transferTotalOverTimeWorkCalc)),
				entity.krcdtAggrOverTimeWorks.stream()
					.map(c -> toDomainAggregateOverTimeWork(c)).collect(Collectors.toList()));
		return domain;
	}
	
	/**
	 * エンティティ→ドメイン　（集計残業時間）
	 * @param entity エンティティ：集計残業時間
	 * @return ドメイン：集計残業時間
	 */
	private static AggregateOverTimeWork toDomainAggregateOverTimeWork(KrcdtAggrOverTimeWork entity){
		AggregateOverTimeWork domain = AggregateOverTimeWork.of(
				entity.PK.overTimeWorkFrameNo,
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(entity.overTimeWork),
						new AttendanceTimeMonth(entity.overTimeWorkCalc)),
				new AttendanceTimeMonth(entity.beforeOverTimeWork),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(entity.transferOverTimeWork),
						new AttendanceTimeMonth(entity.transferOverTimeWorkCalc)),
				new AttendanceTimeMonth(entity.withinStatutoryOverTimeWork),
				new AttendanceTimeMonth(entity.withinStatutoryTransferOverTimeWork));
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
						new AttendanceTimeMonth(entity.krcdtAnnualLeaveUtMon.useTime)),
				RetentionYearlyUseTimeOfMonthly.of(
						new AttendanceTimeMonth(entity.krcdtRetentYearUtMon.useTime)),
				SpecialHolidayUseTimeOfMonthly.of(
						new AttendanceTimeMonth(entity.krcdtSpHolidayUtMon.useTime)),
				CompensatoryLeaveUseTimeOfMonthly.of(
						new AttendanceTimeMonth(entity.krcdtCompenLeaveUtMon.useTime)));
		return domain;
	}
	
	/**
	 * エンティティ→ドメイン　（月別実績の休出時間）
	 * @param entity エンティティ：月別実績の勤怠時間
	 * @return ドメイン：月別実績の休出時間
	 */
	private static HolidayWorkTimeOfMonthly toDomainHolidayWorkTimeOfMonthly(KrcdtMonAttendanceTime entity){
		KrcdtHolidayWorkTmMon targetEntity = entity.krcdtHolidayWorkTmMon;
		HolidayWorkTimeOfMonthly domain = HolidayWorkTimeOfMonthly.of(
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(targetEntity.totalHolidayWorkTime),
						new AttendanceTimeMonth(targetEntity.totalHolidayWorkTimeCalc)),
				new AttendanceTimeMonth(targetEntity.beforeHolidayWorkTime),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(targetEntity.transferTotalTime),
						new AttendanceTimeMonth(targetEntity.transferTotalTimeCalc)),
				entity.krcdtAggrHolidayWrkTms.stream()
					.map(c -> toDomainAggregateHolidayWorkTime(c)).collect(Collectors.toList()));
		return domain;
	}
	
	/**
	 * エンティティ→ドメイン　（集計休出時間）
	 * @param entity エンティティ：集計休出時間
	 * @return ドメイン：集計休出時間
	 */
	private static AggregateHolidayWorkTime toDomainAggregateHolidayWorkTime(KrcdtAggrHolidayWrkTm entity){
		AggregateHolidayWorkTime domain = AggregateHolidayWorkTime.of(
				entity.PK.holidayWorkTimeFrameNo,
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(entity.holidayWorkTime),
						new AttendanceTimeMonth(entity.holidayWorkTimeCalc)),
				new AttendanceTimeMonth(entity.beforeHolidayWorkTime),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(entity.transferHolidayWorkTime),
						new AttendanceTimeMonth(entity.transferHolidayWorkTimeCalc)),
				new AttendanceTimeMonth(entity.withinStatutoryHolidayWorkTime),
				new AttendanceTimeMonth(entity.withinStatutoryTransferHolidayWorkTime));
		return domain;
	}
	
	/**
	 * エンティティ→ドメイン　（月別実績の通常変形時間）
	 * @param entity エンティティ：月別実績の勤怠時間
	 * @return ドメイン：月別実績の通常変形時間
	 */
	private static RegularAndIrregularTimeOfMonthly toDomainRegularAndIrregularTimeOfMonthly(KrcdtMonAttendanceTime entity){
		KrcdtRegIrregTimeMon targetEntity = entity.krcdtRegIrregTimeMon;
		RegularAndIrregularTimeOfMonthly domain = RegularAndIrregularTimeOfMonthly.of(
				new AttendanceTimeMonth(targetEntity.monthlyTotalPremiumTime),
				new AttendanceTimeMonth(targetEntity.weeklyTotalPremiumTime),
				toDomainIrregularWorkingTimeOfMonthly(entity));
		return domain;
	}
	
	/**
	 * エンティティ→ドメイン　（月別実績の変形労働時間）
	 * @param entity エンティティ：月別実績の勤怠時間
	 * @return ドメイン：月別実績の変形労働時間
	 */
	private static IrregularWorkingTimeOfMonthly toDomainIrregularWorkingTimeOfMonthly(KrcdtMonAttendanceTime entity){
		KrcdtIrregWrkTimeMon targetEntity = entity.krcdtIrregWrkTimeMon;
		IrregularWorkingTimeOfMonthly domain = IrregularWorkingTimeOfMonthly.of(
				new AttendanceTimeMonth(targetEntity.multiMonthIrregularMiddleTime),
				new AttendanceTimeMonth(targetEntity.irregularPeriodCarryforwardTime),
				new AttendanceTimeMonth(targetEntity.irregularWorkingShortageTime),
				new TimeMonthWithCalculation(
						new AttendanceTimeMonth(targetEntity.irregularWithinStatutoryOverTimeWork),
						new AttendanceTimeMonth(targetEntity.irregularWithinStatutoryOverTimeWorkCalc))
				);
		return domain;
	}
	
	/**
	 * エンティティ→ドメイン　（月別実績の所定労働時間）
	 * @param entity エンティティ：月別実績の勤怠時間
	 * @return ドメイン：月別実績の所定労働時間
	 */
	private static PrescribedWorkingTimeOfMonthly toDomainPrescribedWorkingTimeOfMonthly(KrcdtMonAttendanceTime entity){
		KrcdtPrescrWrkTimeMon targetEntity = entity.krcdtPrescrWrkTimeMon;
		PrescribedWorkingTimeOfMonthly domain = PrescribedWorkingTimeOfMonthly.of(
				new AttendanceTimeMonth(targetEntity.schedulePrescribedWorkingTime),
				new AttendanceTimeMonth(targetEntity.recordPrescribedWorkingTime));
		return domain;
	}
	
	/**
	 * エンティティ→ドメイン　（集計総拘束時間）
	 * @param entity エンティティ：月別実績の勤怠時間
	 * @return ドメイン：集計総拘束時間
	 */
	private static AggregateTotalTimeSpentAtWork toDomainAggregateTotalTimeSpentAtWork(KrcdtMonAttendanceTime entity){
		KrcdtAggrTotalTmSpent targetEntity = entity.krcdtAggrTotalTmSpent;
		AggregateTotalTimeSpentAtWork domain = AggregateTotalTimeSpentAtWork.of(
				new AttendanceTimeMonth(targetEntity.overTimeWorkSpentAtWork),
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
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthly.getEmployeeID(),
				attendanceTimeOfMonthly.getDatePeriod().start(),
				attendanceTimeOfMonthly.getDatePeriod().end());
		KrcdtMonAttendanceTime entity = new KrcdtMonAttendanceTime();
		entity.PK = key;
		entity.aggregateDays = attendanceTimeOfMonthly.getAggregateDays().v();
		return entity;
	}
}
