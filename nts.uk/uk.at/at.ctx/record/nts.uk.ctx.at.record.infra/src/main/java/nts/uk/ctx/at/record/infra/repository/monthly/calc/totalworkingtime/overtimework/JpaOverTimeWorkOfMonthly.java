package nts.uk.ctx.at.record.infra.repository.monthly.calc.totalworkingtime.overtimework;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtimework.OverTimeWorkOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtimework.OverTimeWorkOfMonthlyRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtimework.KrcdtOverTimeWorkMon;

/**
 * リポジトリ実装：月別実績の残業時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaOverTimeWorkOfMonthly extends JpaRepository implements OverTimeWorkOfMonthlyRepository {

	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			OverTimeWorkOfMonthly overTimeWorkOfMonthly) {
		
		this.commandProxy().insert(toEntity(attendanceTimeOfMonthlyKey, overTimeWorkOfMonthly));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			OverTimeWorkOfMonthly overTimeWorkOfMonthly) {
		
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end());
		KrcdtOverTimeWorkMon entity = this.queryProxy().find(key, KrcdtOverTimeWorkMon.class).get();
		entity.totalOverTimeWork = overTimeWorkOfMonthly.getTotalOverTimeWork().getTime().v();
		entity.totalOverTimeWorkCalc = overTimeWorkOfMonthly.getTotalOverTimeWork().getCalculationTime().v();
		entity.beforeOverTimeWork = overTimeWorkOfMonthly.getBeforeOverTimeWork().v();
		entity.transferTotalOverTimeWork = overTimeWorkOfMonthly.getTransferTotalOverTimeWork().getTime().v();
		entity.transferTotalOverTimeWorkCalc = overTimeWorkOfMonthly.getTransferTotalOverTimeWork().getCalculationTime().v();
		this.commandProxy().update(entity);
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param overTimeWorkOfMonthly ドメイン：月別実績の残業時間
	 * @return エンティティ：月別実績の残業時間
	 */
	private static KrcdtOverTimeWorkMon toEntity(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			OverTimeWorkOfMonthly overTimeWorkOfMonthly){
		
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end());
		KrcdtOverTimeWorkMon entity = new KrcdtOverTimeWorkMon();
		entity.PK = key;
		entity.totalOverTimeWork = overTimeWorkOfMonthly.getTotalOverTimeWork().getTime().v();
		entity.totalOverTimeWorkCalc = overTimeWorkOfMonthly.getTotalOverTimeWork().getCalculationTime().v();
		entity.beforeOverTimeWork = overTimeWorkOfMonthly.getBeforeOverTimeWork().v();
		entity.transferTotalOverTimeWork = overTimeWorkOfMonthly.getTransferTotalOverTimeWork().getTime().v();
		entity.transferTotalOverTimeWorkCalc = overTimeWorkOfMonthly.getTransferTotalOverTimeWork().getCalculationTime().v();
		return entity;
	}
}
