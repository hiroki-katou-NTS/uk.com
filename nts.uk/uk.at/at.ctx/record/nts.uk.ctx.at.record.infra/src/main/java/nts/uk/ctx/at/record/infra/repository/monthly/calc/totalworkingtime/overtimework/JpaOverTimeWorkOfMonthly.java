package nts.uk.ctx.at.record.infra.repository.monthly.calc.totalworkingtime.overtimework;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.OverTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.OverTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtime.KrcdtMonOverTime;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;

/**
 * リポジトリ実装：月別実績の残業時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaOverTimeWorkOfMonthly extends JpaRepository implements OverTimeOfMonthlyRepository {

	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			OverTimeOfMonthly overTimeWorkOfMonthly) {
		
		this.commandProxy().insert(toEntity(attendanceTimeOfMonthlyKey, overTimeWorkOfMonthly));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			OverTimeOfMonthly overTimeWorkOfMonthly) {

		// 締め日付
		ClosureDate closureDate = attendanceTimeOfMonthlyKey.getClosureDate();
		
		// キー
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeId(),
				attendanceTimeOfMonthlyKey.getYearMonth().v(),
				attendanceTimeOfMonthlyKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));
		
		KrcdtMonOverTime entity = this.queryProxy().find(key, KrcdtMonOverTime.class).get();
		entity.totalOverTime = overTimeWorkOfMonthly.getTotalOverTime().getTime().v();
		entity.calcTotalOverTime = overTimeWorkOfMonthly.getTotalOverTime().getCalculationTime().v();
		entity.beforeOverTime = overTimeWorkOfMonthly.getBeforeOverTime().v();
		entity.totalTransferOverTime = overTimeWorkOfMonthly.getTotalTransferOverTime().getTime().v();
		entity.calcTotalTransferOverTime = overTimeWorkOfMonthly.getTotalTransferOverTime().getCalculationTime().v();
		this.commandProxy().update(entity);
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param overTimeWorkOfMonthly ドメイン：月別実績の残業時間
	 * @return エンティティ：月別実績の残業時間
	 */
	private static KrcdtMonOverTime toEntity(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			OverTimeOfMonthly overTimeWorkOfMonthly){

		// 締め日付
		ClosureDate closureDate = attendanceTimeOfMonthlyKey.getClosureDate();
		
		// キー
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeId(),
				attendanceTimeOfMonthlyKey.getYearMonth().v(),
				attendanceTimeOfMonthlyKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));
		
		KrcdtMonOverTime entity = new KrcdtMonOverTime();
		entity.PK = key;
		entity.totalOverTime = overTimeWorkOfMonthly.getTotalOverTime().getTime().v();
		entity.calcTotalOverTime = overTimeWorkOfMonthly.getTotalOverTime().getCalculationTime().v();
		entity.beforeOverTime = overTimeWorkOfMonthly.getBeforeOverTime().v();
		entity.totalTransferOverTime = overTimeWorkOfMonthly.getTotalTransferOverTime().getTime().v();
		entity.calcTotalTransferOverTime = overTimeWorkOfMonthly.getTotalTransferOverTime().getCalculationTime().v();
		return entity;
	}
}
