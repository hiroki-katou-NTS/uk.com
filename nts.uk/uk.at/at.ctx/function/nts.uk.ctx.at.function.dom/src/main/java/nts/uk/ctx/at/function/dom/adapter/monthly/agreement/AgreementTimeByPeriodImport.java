package nts.uk.ctx.at.function.dom.adapter.monthly.agreement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeStatusOfMonthly;

/**
 * @author dat.lh
 *
 */
@Getter
@AllArgsConstructor
public class AgreementTimeByPeriodImport {
	/** 開始月度 */
	private YearMonth startMonth;
	/** 終了月度 */
	private YearMonth endMonth;
	/** 36協定時間 */
	private AttendanceTimeYear agreementTime;
	/** 限度エラー時間 */
	private String limitErrorTime;
	/** 限度アラーム時間 */
	private String limitAlarmTime;
	/** 特例限度エラー時間 */
	private String exceptionLimitErrorTime;
	/** 特例限度アラーム時間 */
	private String exceptionLimitAlarmTime;
	/** 状態 */
	private AgreementTimeStatusOfMonthly status;
}
