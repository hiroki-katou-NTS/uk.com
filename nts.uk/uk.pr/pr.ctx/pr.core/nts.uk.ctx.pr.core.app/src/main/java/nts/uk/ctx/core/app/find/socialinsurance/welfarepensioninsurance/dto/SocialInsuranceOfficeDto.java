package nts.uk.ctx.core.app.find.socialinsurance.welfarepensioninsurance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SocialInsuranceOfficeDto {
	private String socialInsuranceCode;
	private String socialInsuranceName;
	private WelfarePensionInsuranceRateHistoryDto welfareInsuranceRateHistory;
}
