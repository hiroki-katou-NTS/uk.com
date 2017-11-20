package nts.uk.ctx.at.record.infra.repository.monthly.calc.actualworkingtime;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.IrregularWorkingTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.IrregularWorkingTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.actualworkingtime.KrcdtIrregWrkTimeMon;

/**
 * リポジトリ実装：月別実績の変形労働時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaIrregularWorkingTimeOfMonthly extends JpaRepository implements IrregularWorkingTimeOfMonthlyRepository {

	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			IrregularWorkingTimeOfMonthly irregularWorkingTimeOfMonthly) {
		
		this.commandProxy().insert(toEntity(attendanceTimeOfMonthlyKey, irregularWorkingTimeOfMonthly));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			IrregularWorkingTimeOfMonthly irregularWorkingTimeOfMonthly) {
		
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end());
		KrcdtIrregWrkTimeMon entity = this.queryProxy().find(key, KrcdtIrregWrkTimeMon.class).get();
		entity.multiMonthIrregularMiddleTime = irregularWorkingTimeOfMonthly.getMultiMonthIrregularMiddleTime().v();
		entity.irregularPeriodCarryforwardTime = irregularWorkingTimeOfMonthly.getIrregularPeriodCarryforwardTime().v();
		entity.irregularWorkingShortageTime = irregularWorkingTimeOfMonthly.getIrregularWorkingShortageTime().v();
		entity.irregularWithinStatutoryOverTimeWork =
				irregularWorkingTimeOfMonthly.getIrregularWithinStatutoryOverTimeWork().getTime().v();
		entity.irregularWithinStatutoryOverTimeWorkCalc =
				irregularWorkingTimeOfMonthly.getIrregularWithinStatutoryOverTimeWork().getCalculationTime().v();
		this.commandProxy().update(entity);
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param irregularWorkingTimeOfMonthly ドメイン：月別実績の変形労働時間
	 * @return エンティティ：月別実績の変形労働時間
	 */
	private static KrcdtIrregWrkTimeMon toEntity(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			IrregularWorkingTimeOfMonthly irregularWorkingTimeOfMonthly){
		
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end());
		KrcdtIrregWrkTimeMon entity = new KrcdtIrregWrkTimeMon();
		entity.PK = key;
		entity.multiMonthIrregularMiddleTime = irregularWorkingTimeOfMonthly.getMultiMonthIrregularMiddleTime().v();
		entity.irregularPeriodCarryforwardTime = irregularWorkingTimeOfMonthly.getIrregularPeriodCarryforwardTime().v();
		entity.irregularWorkingShortageTime = irregularWorkingTimeOfMonthly.getIrregularWorkingShortageTime().v();
		entity.irregularWithinStatutoryOverTimeWork =
				irregularWorkingTimeOfMonthly.getIrregularWithinStatutoryOverTimeWork().getTime().v();
		entity.irregularWithinStatutoryOverTimeWorkCalc =
				irregularWorkingTimeOfMonthly.getIrregularWithinStatutoryOverTimeWork().getCalculationTime().v();
		return entity;
	}
}
