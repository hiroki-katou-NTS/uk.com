package nts.uk.ctx.at.record.infra.repository.monthly.calc.totalworkingtime;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTimeRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.KrcdtAggrTotalWrkTime;

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
		
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end());
		KrcdtAggrTotalWrkTime entity = this.queryProxy().find(key, KrcdtAggrTotalWrkTime.class).get();
		entity.totalWorkingTime = aggregateTotalWorkingTime.getTotalWorkingTime().v();
		this.commandProxy().update(entity);
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param aggregateTotalWorkingTime ドメイン：集計総労働時間
	 * @return エンティティ：集計総労働時間
	 */
	private static KrcdtAggrTotalWrkTime toEntity(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateTotalWorkingTime aggregateTotalWorkingTime){
		
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end());
		KrcdtAggrTotalWrkTime entity = new KrcdtAggrTotalWrkTime();
		entity.PK = key;
		entity.totalWorkingTime = aggregateTotalWorkingTime.getTotalWorkingTime().v();
		return entity;
	}
}
