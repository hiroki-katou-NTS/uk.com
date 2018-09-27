package nts.uk.ctx.at.record.dom.monthly.agreement.export;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneMonth;

/**
 * 36協定時間の状態チェック
 * @author shuichi_ishida
 */
public interface CheckAgreementTimeStatus {

	/**
	 * 36協定時間の状態チェック
	 * @param agreementTime 36協定時間
	 * @param limitAlarmTime 限度アラーム時間
	 * @param limitErrorTime 限度エラー時間
	 * @param exceptionLimitAlarmTime 特例限度アラーム時間
	 * @param exceptionLimitErrorTime 特例限度エラー時間
	 * @return 月別実績の36協定時間状態
	 */
	// RequestList514
	AgreementTimeStatusOfMonthly algorithm(
			AttendanceTimeMonth agreementTime,
			LimitOneMonth limitAlarmTime,
			LimitOneMonth limitErrorTime,
			Optional<LimitOneMonth> exceptionLimitAlarmTime,
			Optional<LimitOneMonth> exceptionLimitErrorTime);
}
