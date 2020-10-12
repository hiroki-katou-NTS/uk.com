package nts.uk.ctx.at.record.pub.monthly.agreement;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.YearMonth;

@Data
@Builder
public class AgreementTimeOfManagePeriodExport {

	/** 社員ID */
	private String sid;
	/** 月度 */
	private YearMonth ym;
	/** 36協定対象時間 */
	private AgreementTimeOfMonthlyExport agreementTime;
	/** 法定上限対象時間 */
	private AgreementTimeOfMonthlyExport legalMaxTime;
	/** 内訳 */
	private AgreementTimeBreakdownExport breakdown;
	/** 状態 */
	private int status;
}
