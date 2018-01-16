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

	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateTotalTimeSpentAtWork aggregateTotalTimeSpentAtWork) {
		
		this.commandProxy().insert(toEntity(attendanceTimeOfMonthlyKey, aggregateTotalTimeSpentAtWork, false));
	}
	
	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateTotalTimeSpentAtWork aggregateTotalTimeSpentAtWork) {
		
		this.toEntity(attendanceTimeOfMonthlyKey, aggregateTotalTimeSpentAtWork, true);
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param domainKey キー値：月別実績の勤怠時間
	 * @param domain ドメイン：集計総拘束時間
	 * @param execUpdate 更新を実行する
	 * @return エンティティ：集計総拘束時間
	 */
	private KrcdtMonAggrTotalSpt toEntity(AttendanceTimeOfMonthlyKey domainKey,
			AggregateTotalTimeSpentAtWork domain, boolean execUpdate){

		// 締め日付
		val closureDate = domainKey.getClosureDate();
		
		// キー
		val key = new KrcdtMonAttendanceTimePK(
				domainKey.getEmployeeId(),
				domainKey.getYearMonth().v(),
				domainKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));
		
		KrcdtMonAggrTotalSpt entity;
		if (execUpdate){
			entity = this.queryProxy().find(key, KrcdtMonAggrTotalSpt.class).get();
		}
		else{
			entity = new KrcdtMonAggrTotalSpt();
			entity.PK = key;
		}
		entity.overTimeSpentAtWork = domain.getOverTimeSpentAtWork().v();
		entity.midnightTimeSpentAtWork = domain.getMidnightTimeSpentAtWork().v();
		entity.holidayTimeSpentAtWork = domain.getHolidayTimeSpentAtWork().v();
		entity.varienceTimeSpentAtWork = domain.getVarienceTimeSpentAtWork().v();
		entity.totalTimeSpentAtWork = domain.getTotalTimeSpentAtWork().v();
		if (execUpdate) this.commandProxy().update(entity);
		return entity;
	}
}
