package nts.uk.ctx.at.function.dom.adapter.monthly.agreement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeStatusOfMonthly;
@Setter
@Getter
@AllArgsConstructor
public class AgreementTimeOfManagePeriodImport {
	/** 社員ID */
	private String sid;
	/** 月度 */
	private YearMonth ym;
	/** 状態 */ //AgreementTimeStatusOfMonthly
	private AgreementTimeStatusOfMonthly status;
	/** 対象時間 */
	private AgreMaxTimeOfMonthlyImport agreementTime;
}
