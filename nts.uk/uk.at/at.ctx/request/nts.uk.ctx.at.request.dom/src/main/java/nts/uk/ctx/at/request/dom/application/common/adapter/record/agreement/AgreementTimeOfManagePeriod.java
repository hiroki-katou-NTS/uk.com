package nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement;


import lombok.Builder;
import lombok.Data;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeBreakdown;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeStatusOfMonthly;

@Data
@Builder
public class AgreementTimeOfManagePeriod {
	// 36協定対象時間
	private AgreementTimeOfMonthly agreementTime;
	// 社員ID
	private String sid;
	// 状態
	private AgreementTimeStatusOfMonthly status;
	// 内訳
	private AgreementTimeBreakdown agreementTimeBreakDown;
	// 年月
	private YearMonth yearMonth;
	// 法定上限対象時間
	private AgreementTimeOfMonthly legalMaxTime;
}
