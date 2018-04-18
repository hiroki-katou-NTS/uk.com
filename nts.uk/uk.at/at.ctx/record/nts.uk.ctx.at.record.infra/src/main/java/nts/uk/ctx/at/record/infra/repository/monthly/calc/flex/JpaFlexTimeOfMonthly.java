package nts.uk.ctx.at.record.infra.repository.monthly.calc.flex;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.flex.KrcdtMonFlexTime;

/**
 * リポジトリ実装：月別実績のフレックス時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaFlexTimeOfMonthly extends JpaRepository implements FlexTimeOfMonthlyRepository {
	
	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey, FlexTimeOfMonthly flexTimeOfMonthly) {
		this.toUpdate(attendanceTimeOfMonthlyKey, flexTimeOfMonthly);
	}
	
	/**
	 * データ更新
	 * @param domainKey キー値：月別実績の勤怠時間
	 * @param domain ドメイン：月別実績のフレックス時間
	 */
	private void toUpdate(AttendanceTimeOfMonthlyKey domainKey, FlexTimeOfMonthly domain){

		// 締め日付
		val closureDate = domainKey.getClosureDate();
		
		// キー
		val key = new KrcdtMonAttendanceTimePK(
				domainKey.getEmployeeId(),
				domainKey.getYearMonth().v(),
				domainKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));
		
		// フレックス時間
		val flexTime = domain.getFlexTime();
		// フレックス繰越時間
		val flexCarryForwardTime = domain.getFlexCarryforwardTime();
		// 時間外超過のフレックス時間
		val flexTimeOfExcessOutsideTime = domain.getFlexTimeOfExcessOutsideTime();
		// フレックス不足控除時間
		val flexShortDeductTime = domain.getFlexShortDeductTime();
		
		KrcdtMonFlexTime entity = this.getEntityManager().find(KrcdtMonFlexTime.class, key);
		if (entity == null) return;
		entity.flexTime = flexTime.getFlexTime().getTime().v();
		entity.calcFlexTime = flexTime.getFlexTime().getCalcTime().v();
		entity.beforeFlexTime = flexTime.getBeforeFlexTime().v();
		entity.legalFlexTime = flexTime.getLegalFlexTime().v();
		entity.illegalFlexTime = flexTime.getIllegalFlexTime().v();
		entity.flexExcessTime = domain.getFlexExcessTime().v();
		entity.flexShortageTime = domain.getFlexShortageTime().v();
		entity.flexCarryforwardWorkTime = flexCarryForwardTime.getFlexCarryforwardWorkTime().v();
		entity.flexCarryforwardTime = flexCarryForwardTime.getFlexCarryforwardTime().v();
		entity.flexCarryforwardShortageTime = flexCarryForwardTime.getFlexCarryforwardShortageTime().v();
		entity.excessFlexAtr = flexTimeOfExcessOutsideTime.getExcessFlexAtr().value;
		entity.principleTime = flexTimeOfExcessOutsideTime.getPrincipleTime().v();
		entity.forConvenienceTime = flexTimeOfExcessOutsideTime.getForConvenienceTime().v();
		entity.annualLeaveDeductDays = flexShortDeductTime.getAnnualLeaveDeductDays().v();
		entity.absenceDeductTime = flexShortDeductTime.getAbsenceDeductTime().v();
		entity.shotTimeBeforeDeduct = flexShortDeductTime.getFlexShortTimeBeforeDeduct().v();
	}
}
