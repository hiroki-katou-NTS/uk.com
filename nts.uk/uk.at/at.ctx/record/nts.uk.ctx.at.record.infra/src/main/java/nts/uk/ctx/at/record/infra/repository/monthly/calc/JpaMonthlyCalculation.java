package nts.uk.ctx.at.record.infra.repository.monthly.calc;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyCalculationRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.KrcdtMonthlyCalculation;

/**
 * リポジトリ実装：月別実績の月の計算
 * @author shuichu_ishida
 */
@Stateless
public class JpaMonthlyCalculation extends JpaRepository implements MonthlyCalculationRepository {

	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey, MonthlyCalculation monthlyCalculation) {
		this.commandProxy().insert(toEntity(attendanceTimeOfMonthlyKey, monthlyCalculation));
	}
	
	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey, MonthlyCalculation monthlyCalculation) {
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end());
		KrcdtMonthlyCalculation entity = this.queryProxy().find(key, KrcdtMonthlyCalculation.class).get();
		entity.statutoryWorkingTime = monthlyCalculation.getStatutoryWorkingTime().v();
		this.commandProxy().update(entity);
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param monthlyCalculation ドメイン：月別実績の月の計算
	 * @return エンティティ：月別実績の月の計算
	 */
	private static KrcdtMonthlyCalculation toEntity(
			AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey, MonthlyCalculation monthlyCalculation){
		
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end());
		KrcdtMonthlyCalculation entity = new KrcdtMonthlyCalculation();
		entity.PK = key;
		entity.statutoryWorkingTime = monthlyCalculation.getStatutoryWorkingTime().v();
		return entity;
	}
}
