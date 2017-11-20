package nts.uk.ctx.at.record.infra.repository.monthly.calc;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.AggregateTotalTimeSpentAtWork;
import nts.uk.ctx.at.record.dom.monthly.calc.AggregateTotalTimeSpentAtWorkRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.KrcdtAggrTotalTmSpent;

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
		
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end());
		KrcdtAggrTotalTmSpent entity = this.queryProxy().find(key, KrcdtAggrTotalTmSpent.class).get();
		entity.overTimeWorkSpentAtWork = aggregateTotalTimeSpentAtWork.getOverTimeWorkSpentAtWork().v();
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
	private static KrcdtAggrTotalTmSpent toEntity(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateTotalTimeSpentAtWork aggregateTotalTimeSpentAtWork){

		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end());
		KrcdtAggrTotalTmSpent entity = new KrcdtAggrTotalTmSpent();
		entity.PK = key;
		entity.overTimeWorkSpentAtWork = aggregateTotalTimeSpentAtWork.getOverTimeWorkSpentAtWork().v();
		entity.midnightTimeSpentAtWork = aggregateTotalTimeSpentAtWork.getMidnightTimeSpentAtWork().v();
		entity.holidayTimeSpentAtWork = aggregateTotalTimeSpentAtWork.getHolidayTimeSpentAtWork().v();
		entity.varienceTimeSpentAtWork = aggregateTotalTimeSpentAtWork.getVarienceTimeSpentAtWork().v();
		entity.totalTimeSpentAtWork = aggregateTotalTimeSpentAtWork.getTotalTimeSpentAtWork().v();
		return entity;
	}
}
