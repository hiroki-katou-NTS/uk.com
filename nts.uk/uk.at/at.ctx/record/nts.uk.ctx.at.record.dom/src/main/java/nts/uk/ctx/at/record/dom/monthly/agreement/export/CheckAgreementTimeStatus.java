package nts.uk.ctx.at.record.dom.monthly.agreement.export;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreTimeYearStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeYear;
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

	/**
	 * 36協定上限時間の状態チェック
	 * @param agreementTime 36協定時間
	 * @param maxTime 上限時間
	 * @param requestTimeOpt 申請時間
	 * @return 月別実績の36協定上限時間状態
	 */
	// RequestList540
	AgreMaxTimeStatusOfMonthly maxTime(
			AttendanceTimeMonth agreementTime,
			LimitOneMonth maxTime,
			Optional<AttendanceTimeMonth> requestTimeOpt);

	/**
	 * 36協定上限複数月平均時間の状態チェック
	 * @param companyId 会社ID
	 * @param sourceTime 36協定上限複数月平均時間（元データ）
	 * @param requestTimeOpt 申請時間
	 * @param requestDateOpt 申請年月日
	 * @return 36協定上限複数月平均時間
	 */
	// RequestList543
	AgreMaxAverageTimeMulti maxAverageTimeMulti(
			String companyId,
			AgreMaxAverageTimeMulti sourceTime,
			Optional<AttendanceTime> requestTimeOpt,
			Optional<GeneralDate> requestDateOpt);

	/**
	 * 36協定年間時間の状態チェック
	 * @param agreementTimeYear 36協定年間時間
	 * @param requestTimeOpt 申請時間
	 * @return 月別実績の36協定年間時間状態
	 */
	// RequestList545
	AgreTimeYearStatusOfMonthly timeYear(
			AgreementTimeYear agreementTimeYear,
			Optional<AttendanceTimeYear> requestTimeOpt);
}
