package nts.uk.ctx.at.record.infra.repository.monthly.calc.totalworkingtime.holidayusetime;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime.RetentionYearlyUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime.RetentionYearlyUseTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.holidayusetime.KrcdtRetentYearUtMon;

/**
 * リポジトリ実装：月別実績の積立年休使用時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaRetentionYearlyUseTimeOfMonthly extends JpaRepository implements RetentionYearlyUseTimeOfMonthlyRepository {

	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			RetentionYearlyUseTimeOfMonthly retentionYearlyUseTimeOfMonthly) {
		
		this.commandProxy().insert(toEntity(attendanceTimeOfMonthlyKey, retentionYearlyUseTimeOfMonthly));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			RetentionYearlyUseTimeOfMonthly retentionYearlyUseTimeOfMonthly) {
		
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end());
		KrcdtRetentYearUtMon entity = this.queryProxy().find(key, KrcdtRetentYearUtMon.class).get();
		entity.useTime = retentionYearlyUseTimeOfMonthly.getUseTime().v();
		this.commandProxy().update(entity);
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param retentionYearlyUseTimeOfMonthly ドメイン：月別実績の積立年休使用時間
	 * @return エンティティ：月別実績の積立年休使用時間
	 */
	private static KrcdtRetentYearUtMon toEntity(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			RetentionYearlyUseTimeOfMonthly retentionYearlyUseTimeOfMonthly){
		
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end());
		KrcdtRetentYearUtMon entity = new KrcdtRetentYearUtMon();
		entity.PK = key;
		entity.useTime = retentionYearlyUseTimeOfMonthly.getUseTime().v();
		return entity;
	}
}
