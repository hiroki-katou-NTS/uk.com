package nts.uk.ctx.at.record.infra.repository.monthly.calc.actualworkingtime;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.IrregularWorkingTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.actualworkingtime.KrcdtMonRegIrregTime;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;

/**
 * リポジトリ実装：月別実績の通常変形時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaRegularAndIrregularTimeOfMonthly extends JpaRepository implements RegularAndIrregularTimeOfMonthlyRepository {

	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			RegularAndIrregularTimeOfMonthly regularAndIrregularTimeOfMonthly) {
		
		this.commandProxy().insert(toEntity(attendanceTimeOfMonthlyKey, regularAndIrregularTimeOfMonthly));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			RegularAndIrregularTimeOfMonthly regularAndIrregularTimeOfMonthly) {
		
		// 締め日付
		ClosureDate closureDate = attendanceTimeOfMonthlyKey.getClosureDate();
		
		// キー
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeId(),
				attendanceTimeOfMonthlyKey.getYearMonth().v(),
				attendanceTimeOfMonthlyKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));
		
		// 月別実績の変形労働時間
		IrregularWorkingTimeOfMonthly irregularWorkingTime = regularAndIrregularTimeOfMonthly.getIrregularWorkingTime();
		
		KrcdtMonRegIrregTime entity = this.queryProxy().find(key, KrcdtMonRegIrregTime.class).get();
		entity.weeklyTotalPremiumTime = regularAndIrregularTimeOfMonthly.getWeeklyTotalPremiumTime().v();
		entity.monthlyTotalPremiumTime = regularAndIrregularTimeOfMonthly.getMonthlyTotalPremiumTime().v();
		entity.multiMonthIrregularMiddleTime = irregularWorkingTime.getMultiMonthIrregularMiddleTime().v();
		entity.irregularPeriodCarryforwardTime = irregularWorkingTime.getIrregularPeriodCarryforwardTime().v();
		entity.irregularWorkingShortageTime = irregularWorkingTime.getIrregularWorkingShortageTime().v();
		entity.irregularLegalOverTime = irregularWorkingTime.getIrregularLegalOverTime().getTime().v();
		entity.calcIrregularLegalOverTime = irregularWorkingTime.getIrregularLegalOverTime().getCalculationTime().v();
		this.commandProxy().update(entity);
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param regularAndIrregularTimeOfMonthly ドメイン：月別実績の通常変形時間
	 * @return エンティティ：月別実績の通常変形時間
	 */
	private static KrcdtMonRegIrregTime toEntity(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			RegularAndIrregularTimeOfMonthly regularAndIrregularTimeOfMonthly){

		// 締め日付
		ClosureDate closureDate = attendanceTimeOfMonthlyKey.getClosureDate();
		
		// キー
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeId(),
				attendanceTimeOfMonthlyKey.getYearMonth().v(),
				attendanceTimeOfMonthlyKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));
		
		// 月別実績の変形労働時間
		IrregularWorkingTimeOfMonthly irregularWorkingTime = regularAndIrregularTimeOfMonthly.getIrregularWorkingTime();
		
		KrcdtMonRegIrregTime entity = new KrcdtMonRegIrregTime();
		entity.PK = key;
		entity.weeklyTotalPremiumTime = regularAndIrregularTimeOfMonthly.getWeeklyTotalPremiumTime().v();
		entity.monthlyTotalPremiumTime = regularAndIrregularTimeOfMonthly.getMonthlyTotalPremiumTime().v();
		entity.multiMonthIrregularMiddleTime = irregularWorkingTime.getMultiMonthIrregularMiddleTime().v();
		entity.irregularPeriodCarryforwardTime = irregularWorkingTime.getIrregularPeriodCarryforwardTime().v();
		entity.irregularWorkingShortageTime = irregularWorkingTime.getIrregularWorkingShortageTime().v();
		entity.irregularLegalOverTime = irregularWorkingTime.getIrregularLegalOverTime().getTime().v();
		entity.calcIrregularLegalOverTime = irregularWorkingTime.getIrregularLegalOverTime().getCalculationTime().v();
		return entity;
	}
}
