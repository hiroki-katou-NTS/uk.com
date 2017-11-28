package nts.uk.ctx.at.record.infra.repository.monthly.calc.totalworkingtime;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTimeRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.PrescribedWorkingTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.WorkTimeOfMonthly;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.KrcdtMonAggrTotalWrk;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;

/**
 * リポジトリ実装：集計総労働時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaAggregateTotalWorkingTime extends JpaRepository implements AggregateTotalWorkingTimeRepository {

	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateTotalWorkingTime aggregateTotalWorkingTime) {
		
		this.commandProxy().insert(toEntity(attendanceTimeOfMonthlyKey, aggregateTotalWorkingTime));
	}
	
	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateTotalWorkingTime aggregateTotalWorkingTime) {

		// 締め日付
		ClosureDate closureDate = attendanceTimeOfMonthlyKey.getClosureDate();
		
		// キー
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeId(),
				attendanceTimeOfMonthlyKey.getYearMonth().v(),
				attendanceTimeOfMonthlyKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));
		
		// 月別実績の就業時間
		WorkTimeOfMonthly workTime = aggregateTotalWorkingTime.getWorkTime();
		// 月別実績の所定労働時間
		PrescribedWorkingTimeOfMonthly prescribedWorkingTime = aggregateTotalWorkingTime.getPrescribedWorkingTime();
		
		KrcdtMonAggrTotalWrk entity = this.queryProxy().find(key, KrcdtMonAggrTotalWrk.class).get();
		entity.totalWorkingTime = aggregateTotalWorkingTime.getTotalWorkingTime().v();
		entity.workTime = workTime.getWorkTime().v();
		entity.withinPrescribedPremiumTime = workTime.getWithinPrescribedPremiumTime().v();
		entity.schedulePrescribedWorkingTime = prescribedWorkingTime.getSchedulePrescribedWorkingTime().v();
		entity.recordPrescribedWorkingTime = prescribedWorkingTime.getRecordPrescribedWorkingTime().v();
		this.commandProxy().update(entity);
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param aggregateTotalWorkingTime ドメイン：集計総労働時間
	 * @return エンティティ：集計総労働時間
	 */
	private static KrcdtMonAggrTotalWrk toEntity(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateTotalWorkingTime aggregateTotalWorkingTime){

		// 締め日付
		ClosureDate closureDate = attendanceTimeOfMonthlyKey.getClosureDate();
		
		// キー
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeId(),
				attendanceTimeOfMonthlyKey.getYearMonth().v(),
				attendanceTimeOfMonthlyKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));
		
		// 月別実績の就業時間
		WorkTimeOfMonthly workTime = aggregateTotalWorkingTime.getWorkTime();
		// 月別実績の所定労働時間
		PrescribedWorkingTimeOfMonthly prescribedWorkingTime = aggregateTotalWorkingTime.getPrescribedWorkingTime();
		
		KrcdtMonAggrTotalWrk entity = new KrcdtMonAggrTotalWrk();
		entity.PK = key;
		entity.totalWorkingTime = aggregateTotalWorkingTime.getTotalWorkingTime().v();
		entity.workTime = workTime.getWorkTime().v();
		entity.withinPrescribedPremiumTime = workTime.getWithinPrescribedPremiumTime().v();
		entity.schedulePrescribedWorkingTime = prescribedWorkingTime.getSchedulePrescribedWorkingTime().v();
		entity.recordPrescribedWorkingTime = prescribedWorkingTime.getRecordPrescribedWorkingTime().v();
		return entity;
	}
}
