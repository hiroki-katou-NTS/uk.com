package nts.uk.ctx.at.function.dom.adapter.monthly.agreement;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneMonth;

public interface CheckAgreementTimeStatusAdapter {
	/**
	 * 36協定上限時間の状態チェック
	 * @param agreementTime 36協定時間
	 * @param maxTime 上限時間
	 * @param requestTimeOpt 申請時間
	 * @return 月別実績の36協定上限時間状態
	 */
	//RequestList540
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
	//RequestList543
	AgreMaxAverageTimeMulti maxAverageTimeMulti(
			String companyId,
			AgreMaxAverageTimeMulti sourceTime,
			Optional<AttendanceTime> requestTimeOpt,
			Optional<GeneralDate> requestDateOpt);
}
