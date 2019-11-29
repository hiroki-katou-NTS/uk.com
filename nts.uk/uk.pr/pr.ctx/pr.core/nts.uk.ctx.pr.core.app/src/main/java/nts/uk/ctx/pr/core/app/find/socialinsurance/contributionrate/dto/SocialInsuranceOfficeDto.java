package nts.uk.ctx.pr.core.app.find.socialinsurance.contributionrate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.pr.core.app.find.socialinsurance.healthinsurance.dto.HealthInsuranceFeeRateHistoryDto;

@Data
@AllArgsConstructor
public class SocialInsuranceOfficeDto {
    private String socialInsuranceCode;
    private String socialInsuranceName;
    private ContributionRateHistoryDto contributionRateHistory;
    private HealthInsuranceFeeRateHistoryDto healthInsuranceFeeRateHistory;

    public SocialInsuranceOfficeDto(String socialInsuranceCode, String socialInsuranceName, ContributionRateHistoryDto contributionRateHistory) {
        this.socialInsuranceCode = socialInsuranceCode;
        this.socialInsuranceName = socialInsuranceName;
        this.contributionRateHistory = contributionRateHistory;
    }

    public SocialInsuranceOfficeDto(String socialInsuranceCode, String socialInsuranceName, HealthInsuranceFeeRateHistoryDto healthInsuranceFeeRateHistory) {
        this.socialInsuranceCode = socialInsuranceCode;
        this.socialInsuranceName = socialInsuranceName;
        this.healthInsuranceFeeRateHistory = healthInsuranceFeeRateHistory;
    }
}