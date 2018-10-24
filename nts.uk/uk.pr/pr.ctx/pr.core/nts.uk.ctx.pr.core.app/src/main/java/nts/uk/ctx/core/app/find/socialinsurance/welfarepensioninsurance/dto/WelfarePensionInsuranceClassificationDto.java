package nts.uk.ctx.core.app.find.socialinsurance.welfarepensioninsurance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.FundClassification;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceClassification;

/**
 * 厚生年金保険区分
 */
@AllArgsConstructor
@Data
public class WelfarePensionInsuranceClassificationDto {
	/**
     * 履歴ID
     */
    private String historyId;

    /**
     * 厚生年金基金加入区分
     */
    private int fundClassification;

    public static WelfarePensionInsuranceClassificationDto fromDomainToDto(WelfarePensionInsuranceClassification domain) {
        return new WelfarePensionInsuranceClassificationDto(domain.getHistoryId(), domain.getFundClassification().value);
    }
}
