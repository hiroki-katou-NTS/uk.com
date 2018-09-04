package nts.uk.ctx.at.record.dom.monthly.agreement.export;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneMonth;

/**
 * 実装：36協定時間の状態チェック
 * @author shuichi_ishida
 */
@Stateless
public class CheckAgreementTimeStatusImpl implements CheckAgreementTimeStatus {

	/** 36協定時間の状態チェック */
	@Override
	public AgreementTimeStatusOfMonthly algorithm(AttendanceTimeMonth agreementTime, LimitOneMonth limitAlarmTime,
			LimitOneMonth limitErrorTime, Optional<LimitOneMonth> exceptionLimitAlarmTime,
			Optional<LimitOneMonth> exceptionLimitErrorTime) {
		
		// 月別実績の36協定時間をパラメータから作成
		int paramlimitAlarmTime = 0;
		if (limitAlarmTime != null) paramlimitAlarmTime = limitAlarmTime.v();
		int paramlimitErrorTime = 0;
		if (limitErrorTime != null) paramlimitErrorTime = limitErrorTime.v();
		nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitOneMonth paramExceptionLimitAlarmTime = null;
		if (exceptionLimitAlarmTime.isPresent()){
			paramExceptionLimitAlarmTime = new nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitOneMonth(
					exceptionLimitAlarmTime.get().v());
		}
		nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitOneMonth paramExceptionLimitErrorTime = null;
		if (exceptionLimitErrorTime.isPresent()){
			paramExceptionLimitErrorTime = new nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitOneMonth(
					exceptionLimitErrorTime.get().v());
		}
		AgreementTimeOfMonthly agreementTimeOfMonthly = AgreementTimeOfMonthly.of(
				(agreementTime == null ? new AttendanceTimeMonth(0) : agreementTime),
				new nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitOneMonth(paramlimitErrorTime),
				new nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitOneMonth(paramlimitAlarmTime),
				Optional.ofNullable(paramExceptionLimitErrorTime),
				Optional.ofNullable(paramExceptionLimitAlarmTime),
				AgreementTimeStatusOfMonthly.NORMAL);
		
		// チェック処理
		agreementTimeOfMonthly.errorCheck();
		
		// 「状態」を返す
		return agreementTimeOfMonthly.getStatus();
	}
}
