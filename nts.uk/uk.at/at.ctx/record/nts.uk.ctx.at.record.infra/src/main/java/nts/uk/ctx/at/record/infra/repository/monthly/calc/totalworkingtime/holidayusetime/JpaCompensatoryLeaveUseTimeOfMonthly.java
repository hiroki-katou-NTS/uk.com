package nts.uk.ctx.at.record.infra.repository.monthly.calc.totalworkingtime.holidayusetime;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime.CompensatoryLeaveUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime.CompensatoryLeaveUseTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.holidayusetime.KrcdtCompenLeaveUtMon;

/**
 * リポジトリ実装：月別実績の代休使用時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaCompensatoryLeaveUseTimeOfMonthly extends JpaRepository implements CompensatoryLeaveUseTimeOfMonthlyRepository {

	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			CompensatoryLeaveUseTimeOfMonthly compensatoryLeaveUseTimeOfMonthly) {
		
		this.commandProxy().insert(toEntity(attendanceTimeOfMonthlyKey, compensatoryLeaveUseTimeOfMonthly));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			CompensatoryLeaveUseTimeOfMonthly compensatoryLeaveUseTimeOfMonthly) {
		
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end());
		KrcdtCompenLeaveUtMon entity = this.queryProxy().find(key, KrcdtCompenLeaveUtMon.class).get();
		entity.useTime = compensatoryLeaveUseTimeOfMonthly.getUseTime().v();
		this.commandProxy().update(entity);
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param compensatoryLeaveUseTimeOfMonthly ドメイン：月別実績の代休使用時間
	 * @return エンティティ：月別実績の代休使用時間
	 */
	private static KrcdtCompenLeaveUtMon toEntity(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			CompensatoryLeaveUseTimeOfMonthly compensatoryLeaveUseTimeOfMonthly){
		
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end());
		KrcdtCompenLeaveUtMon entity = new KrcdtCompenLeaveUtMon();
		entity.PK = key;
		entity.useTime = compensatoryLeaveUseTimeOfMonthly.getUseTime().v();
		return entity;
	}
}
