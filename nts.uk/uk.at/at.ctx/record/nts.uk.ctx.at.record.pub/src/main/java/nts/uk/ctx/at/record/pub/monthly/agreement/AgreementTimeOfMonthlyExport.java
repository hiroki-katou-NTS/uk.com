package nts.uk.ctx.at.record.pub.monthly.agreement;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfMonthly;

@Data
@Builder
public class AgreementTimeOfMonthlyExport {

	/** 対象時間 */
	@Setter
	private int agreementTime;
	/** 閾値 */
	private OneMonthTimeExport threshold;
	
	public static AgreementTimeOfMonthlyExport copy(AgreementTimeOfMonthly domain) {
		return AgreementTimeOfMonthlyExport.builder()
				.agreementTime(domain.getAgreementTime().valueAsMinutes())
				.threshold(OneMonthTimeExport.builder()
						.alarmTime(domain.getThreshold().getErAlTime().getAlarm().valueAsMinutes())
						.errorTime(domain.getThreshold().getErAlTime().getError().valueAsMinutes())
						.upperLimit(domain.getThreshold().getUpperLimit().valueAsMinutes())
						.build())
				.build();
	}
}
