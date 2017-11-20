package nts.uk.ctx.at.record.infra.repository.monthly.calc.totalworkingtime.holidayusetime;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime.AnnualLeaveUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime.AnnualLeaveUseTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.holidayusetime.KrcdtAnnualLeaveUtMon;

/**
 * リポジトリ実装：月別実績の年休使用時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaAnnualLeaveUseTimeOfMonthly extends JpaRepository implements AnnualLeaveUseTimeOfMonthlyRepository {
	
	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AnnualLeaveUseTimeOfMonthly annualLeaveUseTimeOfMonthly) {
		
		this.commandProxy().insert(toEntity(attendanceTimeOfMonthlyKey, annualLeaveUseTimeOfMonthly));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AnnualLeaveUseTimeOfMonthly annualLeaveUseTimeOfMonthly) {
		
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end());
		KrcdtAnnualLeaveUtMon entity = this.queryProxy().find(key, KrcdtAnnualLeaveUtMon.class).get();
		entity.useTime = annualLeaveUseTimeOfMonthly.getUseTime().v();
		this.commandProxy().update(entity);
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param annualLeaveUseTimeOfMonthly ドメイン：月別実績の年休使用時間
	 * @return エンティティ：月別実績の年休使用時間
	 */
	private static KrcdtAnnualLeaveUtMon toEntity(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AnnualLeaveUseTimeOfMonthly annualLeaveUseTimeOfMonthly){
		
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end());
		KrcdtAnnualLeaveUtMon entity = new KrcdtAnnualLeaveUtMon();
		entity.PK = key;
		entity.useTime = annualLeaveUseTimeOfMonthly.getUseTime().v();
		return entity;
	}
}
