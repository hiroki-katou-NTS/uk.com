package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfMonthly;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgreementTimeOfMonthlyDto {
	
	public Integer agreementTime;
	
	public OneMonthTimeDto threshold;
	
	public static AgreementTimeOfMonthlyDto fromDomain(AgreementTimeOfMonthly agreementTimeOfMonthly) {
		
		return new AgreementTimeOfMonthlyDto(
				agreementTimeOfMonthly.getAgreementTime().v(),
				OneMonthTimeDto.fromDomain(agreementTimeOfMonthly.getThreshold()));
	}
}
