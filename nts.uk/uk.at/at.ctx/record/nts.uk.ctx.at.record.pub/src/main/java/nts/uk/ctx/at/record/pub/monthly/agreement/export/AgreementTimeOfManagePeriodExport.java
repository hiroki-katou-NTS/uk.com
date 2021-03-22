package nts.uk.ctx.at.record.pub.monthly.agreement.export;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;

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
	
	public static AgreementTimeOfManagePeriodExport copy(AgreementTimeOfManagePeriod domain) {
		
		return AgreementTimeOfManagePeriodExport.builder()
				.sid(domain.getSid())
				.ym(domain.getYm())
				.agreementTime(AgreementTimeOfMonthlyExport.copy(domain.getAgreementTime()))
				.legalMaxTime(AgreementTimeOfMonthlyExport.copy(domain.getLegalMaxTime()))
				.breakdown(AgreementTimeBreakdownExport.copy(domain.getBreakdown()))
				.status(domain.getStatus().value)
				.build();
	}
}
