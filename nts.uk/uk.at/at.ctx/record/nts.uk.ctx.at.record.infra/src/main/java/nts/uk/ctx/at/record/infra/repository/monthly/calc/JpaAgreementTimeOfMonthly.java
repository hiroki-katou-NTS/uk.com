package nts.uk.ctx.at.record.infra.repository.monthly.calc;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.KrcdtMonAgreementTime;

/**
 * リポジトリ実装：月別実績の36協定時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaAgreementTimeOfMonthly extends JpaRepository implements AgreementTimeOfMonthlyRepository {

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AgreementTimeOfMonthly agreementTimeOfMonthly) {
		
		this.toUpdate(attendanceTimeOfMonthlyKey, agreementTimeOfMonthly);
	}
	
	/**
	 * データ更新
	 * @param domainKey キー値：月別実績の勤怠時間
	 * @param domain ドメイン：月別実績の36協定時間
	 */
	private void toUpdate(AttendanceTimeOfMonthlyKey domainKey, AgreementTimeOfMonthly domain){

		// 締め日付
		val closureDate = domainKey.getClosureDate();
		
		// キー
		val key = new KrcdtMonAttendanceTimePK(
				domainKey.getEmployeeId(),
				domainKey.getYearMonth().v(),
				domainKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));
		
		KrcdtMonAgreementTime entity = this.queryProxy().find(key, KrcdtMonAgreementTime.class).get();
		if (entity == null) return;
		entity.agreementTime = domain.getAgreementTime().v();
		entity.limitErrorTime = domain.getLimitErrorTime().v();
		entity.limitAlarmTime = domain.getLimitAlarmTime().v();
		entity.exceptionLimitErrorTime =
				(domain.getExceptionLimitErrorTime().isPresent() ? domain.getExceptionLimitErrorTime().get().v() : null); 
		entity.exceptionLimitAlarmTime =
				(domain.getExceptionLimitAlarmTime().isPresent() ? domain.getExceptionLimitAlarmTime().get().v() : null);
		entity.status = domain.getStatus().value;
	}
}
