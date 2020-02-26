package nts.uk.ctx.pr.core.app.find.socialinsurance.welfarepensioninsurance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.pr.core.app.find.socialinsurance.healthinsurance.dto.HealthInsuranceFeeRateHistoryDto;

@Data
@AllArgsConstructor
public class SocialInsuranceOfficeDto {
    private String socialInsuranceCode;
    private String socialInsuranceName;
    private WelfarePensionInsuranceRateHistoryDto welfareInsuranceRateHistory;
    private HealthInsuranceFeeRateHistoryDto healthInsuranceFeeRateHistory;

    public SocialInsuranceOfficeDto(String socialInsuranceCode, String socialInsuranceName, WelfarePensionInsuranceRateHistoryDto welfareInsuranceRateHistory) {
        this.socialInsuranceCode = socialInsuranceCode;
        this.socialInsuranceName = socialInsuranceName;
        this.welfareInsuranceRateHistory = welfareInsuranceRateHistory;
    }

    public SocialInsuranceOfficeDto(String socialInsuranceCode, String socialInsuranceName, HealthInsuranceFeeRateHistoryDto healthInsuranceFeeRateHistory) {
        this.socialInsuranceCode = socialInsuranceCode;
        this.socialInsuranceName = socialInsuranceName;
        this.healthInsuranceFeeRateHistory = healthInsuranceFeeRateHistory;
    }
}