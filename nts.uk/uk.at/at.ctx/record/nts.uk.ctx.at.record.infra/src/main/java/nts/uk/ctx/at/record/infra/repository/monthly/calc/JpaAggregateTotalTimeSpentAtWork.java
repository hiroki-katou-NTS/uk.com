package nts.uk.ctx.at.record.infra.repository.monthly.calc;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.AggregateTotalTimeSpentAtWork;
import nts.uk.ctx.at.record.dom.monthly.calc.AggregateTotalTimeSpentAtWorkRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.KrcdtMonAggrTotalSpt;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;

/**
 * リポジトリ実装：集計総拘束時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaAggregateTotalTimeSpentAtWork extends JpaRepository implements AggregateTotalTimeSpentAtWorkRepository {

	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateTotalTimeSpentAtWork aggregateTotalTimeSpentAtWork) {
		
		this.commandProxy().insert(toEntity(attendanceTimeOfMonthlyKey, aggregateTotalTimeSpentAtWork));
	}
	
	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateTotalTimeSpentAtWork aggregateTotalTimeSpentAtWork) {

		// 締め日付
		ClosureDate closureDate = attendanceTimeOfMonthlyKey.getClosureDate();
		
		// キー
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeId(),
				attendanceTimeOfMonthlyKey.getYearMonth().v(),
				attendanceTimeOfMonthlyKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));
		
		KrcdtMonAggrTotalSpt entity = this.queryProxy().find(key, KrcdtMonAggrTotalSpt.class).get();
		entity.overTimeSpentAtWork = aggregateTotalTimeSpentAtWork.getOverTimeSpentAtWork().v();
		entity.midnightTimeSpentAtWork = aggregateTotalTimeSpentAtWork.getMidnightTimeSpentAtWork().v();
		entity.holidayTimeSpentAtWork = aggregateTotalTimeSpentAtWork.getHolidayTimeSpentAtWork().v();
		entity.varienceTimeSpentAtWork = aggregateTotalTimeSpentAtWork.getVarienceTimeSpentAtWork().v();
		entity.totalTimeSpentAtWork = aggregateTotalTimeSpentAtWork.getTotalTimeSpentAtWork().v();
		this.commandProxy().update(entity);
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param aggregateTotalTimeSpentAtWork ドメイン：集計総拘束時間
	 * @return エンティティ：集計総拘束時間
	 */
	private static KrcdtMonAggrTotalSpt toEntity(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateTotalTimeSpentAtWork aggregateTotalTimeSpentAtWork){

		// 締め日付
		ClosureDate closureDate = attendanceTimeOfMonthlyKey.getClosureDate();
		
		// キー
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeId(),
				attendanceTimeOfMonthlyKey.getYearMonth().v(),
				attendanceTimeOfMonthlyKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));
		
		KrcdtMonAggrTotalSpt entity = new KrcdtMonAggrTotalSpt();
		entity.PK = key;
		entity.overTimeSpentAtWork = aggregateTotalTimeSpentAtWork.getOverTimeSpentAtWork().v();
		entity.midnightTimeSpentAtWork = aggregateTotalTimeSpentAtWork.getMidnightTimeSpentAtWork().v();
		entity.holidayTimeSpentAtWork = aggregateTotalTimeSpentAtWork.getHolidayTimeSpentAtWork().v();
		entity.varienceTimeSpentAtWork = aggregateTotalTimeSpentAtWork.getVarienceTimeSpentAtWork().v();
		entity.totalTimeSpentAtWork = aggregateTotalTimeSpentAtWork.getTotalTimeSpentAtWork().v();
		return entity;
	}
}
