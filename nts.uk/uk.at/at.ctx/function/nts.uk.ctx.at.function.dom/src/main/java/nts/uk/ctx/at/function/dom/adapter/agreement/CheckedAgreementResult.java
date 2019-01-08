package nts.uk.ctx.at.function.dom.adapter.agreement;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.AgreementTimeByPeriodImport;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.ErrorAlarm;
/**
 * 36協定チェック結果
 * @author thuongtv
 *
 */
@Setter
@Getter
@Builder
public class CheckedAgreementResult {

	/** チェック結果 */
	private boolean checkResult;

	/** 上限値  */
	private String upperLimit;

	/** エラーアラーム */
	private ErrorAlarm errorAlarm;

	/** 指定期間36協定時間 */
	private AgreementTimeByPeriodImport agreementTimeByPeriod;

	/** 社員ID */
	private String empId;
}
