package nts.uk.ctx.at.record.infra.repository.monthly.calc.totalworkingtime;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTimeRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.KrcdtMonAggrTotalWrk;

/**
 * リポジトリ実装：集計総労働時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaAggregateTotalWorkingTime extends JpaRepository implements AggregateTotalWorkingTimeRepository {

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateTotalWorkingTime aggregateTotalWorkingTime) {

		this.toUpdate(attendanceTimeOfMonthlyKey, aggregateTotalWorkingTime);
	}
	
	/**
	 * データ更新
	 * @param domainKey キー値：月別実績の勤怠時間
	 * @param domain ドメイン：集計総労働時間
	 */
	private void toUpdate(AttendanceTimeOfMonthlyKey domainKey, AggregateTotalWorkingTime domain){

		// 締め日付
		val closureDate = domainKey.getClosureDate();
		
		// キー
		val key = new KrcdtMonAttendanceTimePK(
				domainKey.getEmployeeId(),
				domainKey.getYearMonth().v(),
				domainKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));
		
		// 月別実績の就業時間
		val workTime = domain.getWorkTime();
		// 月別実績の所定労働時間
		val prescribedWorkingTime = domain.getPrescribedWorkingTime();
		
		KrcdtMonAggrTotalWrk entity = this.getEntityManager().find(KrcdtMonAggrTotalWrk.class, key);
		if (entity == null) return;
		entity.workTime = workTime.getWorkTime().v();
		entity.actualWorkTime = workTime.getActualWorkTime().v();
		entity.withinPrescribedPremiumTime = workTime.getWithinPrescribedPremiumTime().v();
		entity.schedulePrescribedWorkingTime = prescribedWorkingTime.getSchedulePrescribedWorkingTime().v();
		entity.recordPrescribedWorkingTime = prescribedWorkingTime.getRecordPrescribedWorkingTime().v();
	}
}
