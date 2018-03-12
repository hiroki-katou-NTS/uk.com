package nts.uk.ctx.at.record.infra.repository.monthly.calc;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.AggregateTotalTimeSpentAtWork;
import nts.uk.ctx.at.record.dom.monthly.calc.AggregateTotalTimeSpentAtWorkRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.KrcdtMonAggrTotalSpt;

/**
 * リポジトリ実装：集計総拘束時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaAggregateTotalTimeSpentAtWork extends JpaRepository implements AggregateTotalTimeSpentAtWorkRepository {

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateTotalTimeSpentAtWork aggregateTotalTimeSpentAtWork) {
		
		this.toUpdate(attendanceTimeOfMonthlyKey, aggregateTotalTimeSpentAtWork);
	}
	
	/**
	 * データ更新
	 * @param domainKey キー値：月別実績の勤怠時間
	 * @param domain ドメイン：集計総拘束時間
	 */
	private void toUpdate(AttendanceTimeOfMonthlyKey domainKey, AggregateTotalTimeSpentAtWork domain){

		// 締め日付
		val closureDate = domainKey.getClosureDate();
		
		// キー
		val key = new KrcdtMonAttendanceTimePK(
				domainKey.getEmployeeId(),
				domainKey.getYearMonth().v(),
				domainKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));
		
		KrcdtMonAggrTotalSpt entity = this.queryProxy().find(key, KrcdtMonAggrTotalSpt.class).get();
		if (entity == null) return;
		entity.overTimeSpentAtWork = domain.getOverTimeSpentAtWork().v();
		entity.midnightTimeSpentAtWork = domain.getMidnightTimeSpentAtWork().v();
		entity.holidayTimeSpentAtWork = domain.getHolidayTimeSpentAtWork().v();
		entity.varienceTimeSpentAtWork = domain.getVarienceTimeSpentAtWork().v();
		entity.totalTimeSpentAtWork = domain.getTotalTimeSpentAtWork().v();
	}
}
