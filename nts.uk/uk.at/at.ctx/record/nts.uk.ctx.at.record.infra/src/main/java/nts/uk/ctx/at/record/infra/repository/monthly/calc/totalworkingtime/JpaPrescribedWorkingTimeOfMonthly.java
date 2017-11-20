package nts.uk.ctx.at.record.infra.repository.monthly.calc.totalworkingtime;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.PrescribedWorkingTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.PrescribedWorkingTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.KrcdtPrescrWrkTimeMon;

/**
 * リポジトリ実装：月別実績の所定労働時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaPrescribedWorkingTimeOfMonthly extends JpaRepository implements PrescribedWorkingTimeOfMonthlyRepository {

	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			PrescribedWorkingTimeOfMonthly prescribedWorkingTimeOfMonthly) {
		
		this.commandProxy().insert(toEntity(attendanceTimeOfMonthlyKey, prescribedWorkingTimeOfMonthly));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			PrescribedWorkingTimeOfMonthly prescribedWorkingTimeOfMonthly) {
		
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end());
		KrcdtPrescrWrkTimeMon entity = this.queryProxy().find(key, KrcdtPrescrWrkTimeMon.class).get();
		entity.schedulePrescribedWorkingTime = prescribedWorkingTimeOfMonthly.getSchedulePrescribedWorkingTime().v();
		entity.recordPrescribedWorkingTime = prescribedWorkingTimeOfMonthly.getRecordPrescribedWorkingTime().v();
		this.commandProxy().update(entity);
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param prescribedWorkingTimeOfMonthly ドメイン：月別実績の所定労働時間
	 * @return エンティティ：月別実績の所定労働時間
	 */
	private static KrcdtPrescrWrkTimeMon toEntity(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			PrescribedWorkingTimeOfMonthly prescribedWorkingTimeOfMonthly){
		
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end());
		KrcdtPrescrWrkTimeMon entity = new KrcdtPrescrWrkTimeMon();
		entity.PK = key;
		entity.schedulePrescribedWorkingTime = prescribedWorkingTimeOfMonthly.getSchedulePrescribedWorkingTime().v();
		entity.recordPrescribedWorkingTime = prescribedWorkingTimeOfMonthly.getRecordPrescribedWorkingTime().v();
		return entity;
	}
}
