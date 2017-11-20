package nts.uk.ctx.at.record.infra.repository.monthly.calc.totalworkingtime;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.WorkTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.WorkTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.KrcdtWorkTimeOfMonth;

/**
 * リポジトリ実装：月別実績の就業時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaWorkTimeOfMonthly extends JpaRepository implements WorkTimeOfMonthlyRepository {

	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey, WorkTimeOfMonthly workTimeOfMonthly) {
		this.commandProxy().insert(toEntity(attendanceTimeOfMonthlyKey, workTimeOfMonthly));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey, WorkTimeOfMonthly workTimeOfMonthly) {
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end());
		KrcdtWorkTimeOfMonth entity = this.queryProxy().find(key, KrcdtWorkTimeOfMonth.class).get();
		entity.workTime = workTimeOfMonthly.getWorkTime().v();
		entity.withinPrescribedPremiumTime = workTimeOfMonthly.getWithinPrescribedPremiumTime().v();
		this.commandProxy().update(entity);
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param workTimeOfMonthly ドメイン：月別実績の就業時間
	 * @return エンティティ：月別実績の就業時間
	 */
	private static KrcdtWorkTimeOfMonth toEntity(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			WorkTimeOfMonthly workTimeOfMonthly){
		
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end());
		KrcdtWorkTimeOfMonth entity = new KrcdtWorkTimeOfMonth();
		entity.PK = key;
		entity.workTime = workTimeOfMonthly.getWorkTime().v();
		entity.withinPrescribedPremiumTime = workTimeOfMonthly.getWithinPrescribedPremiumTime().v();
		return entity;
	}
}
