package nts.uk.ctx.at.record.infra.repository.monthly.calc.actualworkingtime;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.actualworkingtime.KrcdtMonRegIrregTime;

/**
 * リポジトリ実装：月別実績の通常変形時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaRegularAndIrregularTimeOfMonthly extends JpaRepository implements RegularAndIrregularTimeOfMonthlyRepository {

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			RegularAndIrregularTimeOfMonthly regularAndIrregularTimeOfMonthly) {
		
		this.toUpdate(attendanceTimeOfMonthlyKey, regularAndIrregularTimeOfMonthly);
	}
	
	/**
	 * データ更新
	 * @param domainKey キー値：月別実績の勤怠時間
	 * @param domain ドメイン：月別実績の通常変形時間
	 */
	private void toUpdate(AttendanceTimeOfMonthlyKey domainKey, RegularAndIrregularTimeOfMonthly domain){

		// 締め日付
		val closureDate = domainKey.getClosureDate();
		
		// キー
		val key = new KrcdtMonAttendanceTimePK(
				domainKey.getEmployeeId(),
				domainKey.getYearMonth().v(),
				domainKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));
		
		// 月別実績の変形労働時間
		val irregularWorkingTime = domain.getIrregularWorkingTime();
		
		KrcdtMonRegIrregTime entity = this.getEntityManager().find(KrcdtMonRegIrregTime.class, key);
		if (entity == null) return;
		entity.weeklyTotalPremiumTime = domain.getWeeklyTotalPremiumTime().v();
		entity.monthlyTotalPremiumTime = domain.getMonthlyTotalPremiumTime().v();
		entity.multiMonthIrregularMiddleTime = irregularWorkingTime.getMultiMonthIrregularMiddleTime().v();
		entity.irregularPeriodCarryforwardTime = irregularWorkingTime.getIrregularPeriodCarryforwardTime().v();
		entity.irregularWorkingShortageTime = irregularWorkingTime.getIrregularWorkingShortageTime().v();
		entity.irregularLegalOverTime = irregularWorkingTime.getIrregularLegalOverTime().getTime().v();
		entity.calcIrregularLegalOverTime = irregularWorkingTime.getIrregularLegalOverTime().getCalcTime().v();
	}
}
