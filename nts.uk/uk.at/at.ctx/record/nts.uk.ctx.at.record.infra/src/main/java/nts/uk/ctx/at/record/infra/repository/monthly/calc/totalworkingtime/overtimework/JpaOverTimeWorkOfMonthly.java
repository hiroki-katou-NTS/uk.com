package nts.uk.ctx.at.record.infra.repository.monthly.calc.totalworkingtime.overtimework;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.OverTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.OverTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtime.KrcdtMonOverTime;

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
		
		this.commandProxy().insert(toEntity(attendanceTimeOfMonthlyKey, overTimeWorkOfMonthly, false));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			OverTimeOfMonthly overTimeWorkOfMonthly) {

		this.toEntity(attendanceTimeOfMonthlyKey, overTimeWorkOfMonthly, true);
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param domainKey キー値：月別実績の勤怠時間
	 * @param domain ドメイン：月別実績の残業時間
	 * @param execUpdate 更新を実行する
	 * @return エンティティ：月別実績の残業時間
	 */
	private KrcdtMonOverTime toEntity(AttendanceTimeOfMonthlyKey domainKey,
			OverTimeOfMonthly domain, boolean execUpdate){

		// 締め日付
		val closureDate = domainKey.getClosureDate();
		
		// キー
		val key = new KrcdtMonAttendanceTimePK(
				domainKey.getEmployeeId(),
				domainKey.getYearMonth().v(),
				domainKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));
		
		KrcdtMonOverTime entity;
		if (execUpdate){
			entity = this.queryProxy().find(key, KrcdtMonOverTime.class).get();
		}
		else {
			entity = new KrcdtMonOverTime();
			entity.PK = key;
		}
		entity.totalOverTime = domain.getTotalOverTime().getTime().v();
		entity.calcTotalOverTime = domain.getTotalOverTime().getCalcTime().v();
		entity.beforeOverTime = domain.getBeforeOverTime().v();
		entity.totalTransferOverTime = domain.getTotalTransferOverTime().getTime().v();
		entity.calcTotalTransferOverTime = domain.getTotalTransferOverTime().getCalcTime().v();
		if (execUpdate) this.commandProxy().update(entity);
		return entity;
	}
}
