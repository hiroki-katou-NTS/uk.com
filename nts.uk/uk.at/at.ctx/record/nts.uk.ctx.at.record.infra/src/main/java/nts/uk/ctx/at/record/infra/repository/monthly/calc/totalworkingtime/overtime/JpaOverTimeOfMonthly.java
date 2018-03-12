package nts.uk.ctx.at.record.infra.repository.monthly.calc.totalworkingtime.overtime;

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
public class JpaOverTimeOfMonthly extends JpaRepository implements OverTimeOfMonthlyRepository {

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			OverTimeOfMonthly overTimeWorkOfMonthly) {

		this.toUpdate(attendanceTimeOfMonthlyKey, overTimeWorkOfMonthly);
	}
	
	/**
	 * データ更新
	 * @param domainKey キー値：月別実績の勤怠時間
	 * @param domain ドメイン：月別実績の残業時間
	 */
	private void toUpdate(AttendanceTimeOfMonthlyKey domainKey, OverTimeOfMonthly domain){

		// 締め日付
		val closureDate = domainKey.getClosureDate();
		
		// キー
		val key = new KrcdtMonAttendanceTimePK(
				domainKey.getEmployeeId(),
				domainKey.getYearMonth().v(),
				domainKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));
		
		// データ更新
		KrcdtMonOverTime entity = this.getEntityManager().find(KrcdtMonOverTime.class, key);
		if (entity == null) return;
		entity.totalOverTime = domain.getTotalOverTime().getTime().v();
		entity.calcTotalOverTime = domain.getTotalOverTime().getCalcTime().v();
		entity.beforeOverTime = domain.getBeforeOverTime().v();
		entity.totalTransferOverTime = domain.getTotalTransferOverTime().getTime().v();
		entity.calcTotalTransferOverTime = domain.getTotalTransferOverTime().getCalcTime().v();
	}
}
