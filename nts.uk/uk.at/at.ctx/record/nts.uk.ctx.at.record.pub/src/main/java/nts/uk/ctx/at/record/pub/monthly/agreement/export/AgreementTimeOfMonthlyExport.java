package nts.uk.ctx.at.record.pub.monthly.agreement.export;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfYear;

@Data
@Builder
public class AgreementTimeOfMonthlyExport {

	/** 対象時間 */
	@Setter
	private int agreementTime;
	/** 閾値 */
	private OneTimeExport threshold;
	
	public static AgreementTimeOfMonthlyExport copy(AgreementTimeOfMonthly domain) {
		return AgreementTimeOfMonthlyExport.builder()
				.agreementTime(domain.getAgreementTime().valueAsMinutes())
				.threshold(OneTimeExport.copy(domain.getThreshold()))
				.build();
	}
	
	public static AgreementTimeOfMonthlyExport copy(AgreementTimeOfYear domain) {
		return AgreementTimeOfMonthlyExport.builder()
				.agreementTime(domain.getTargetTime().valueAsMinutes())
				.threshold(OneTimeExport.copy(domain.getThreshold()))
				.build();
	}
}
