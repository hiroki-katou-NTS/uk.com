package nts.uk.ctx.at.record.infra.repository.monthly.excessoutside;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.excessoutside.ExcessOutsideWorkOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.excessoutside.ExcessOutsideWorkOfMonthlyRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.excessoutside.KrcdtMonExcessOutside;

/**
 * リポジトリ実装：月別実績の時間外超過
 * @author shuichu_ishida
 */
@Stateless
public class JpaExcessOutsideWorkOfMonthly extends JpaRepository implements ExcessOutsideWorkOfMonthlyRepository {

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			ExcessOutsideWorkOfMonthly excessOutsideWorkOfMonthly) {

		this.toUpdate(attendanceTimeOfMonthlyKey, excessOutsideWorkOfMonthly);
	}
	
	/**
	 * データ更新
	 * @param domainKey キー値：月別実績の勤怠時間
	 * @param domain ドメイン：月別実績の時間外超過
	 */
	private void toUpdate(AttendanceTimeOfMonthlyKey domainKey, ExcessOutsideWorkOfMonthly domain){

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
		KrcdtMonExcessOutside entity = this.getEntityManager().find(KrcdtMonExcessOutside.class, key);
		if (entity == null) return;
		entity.totalWeeklyPremiumTime = domain.getWeeklyTotalPremiumTime().v();
		entity.totalMonthlyPremiumTime = domain.getMonthlyTotalPremiumTime().v();
		entity.deformationCarryforwardTime = domain.getDeformationCarryforwardTime().v();
	}
}
