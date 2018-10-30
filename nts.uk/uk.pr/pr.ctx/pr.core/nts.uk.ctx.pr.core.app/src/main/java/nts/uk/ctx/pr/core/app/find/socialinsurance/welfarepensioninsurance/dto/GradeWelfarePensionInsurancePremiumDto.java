package nts.uk.ctx.pr.core.app.find.socialinsurance.welfarepensioninsurance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.GradeWelfarePensionInsurancePremium;

/**
 * 等級毎厚生年金保険料
 */
@AllArgsConstructor
@Data
public class GradeWelfarePensionInsurancePremiumDto {
	/**
     * 厚生年金等級
     */
    private int welfarePensionGrade;

    /**
     * 被保険者負担
     */
    private ContributionFeeDto insuredBurden;

    /**
     * 事業主負担
     */
    private ContributionFeeDto employeeBurden;

    /**
     * 等級毎厚生年金保険料
     *
     * @param welfarePensionGrade 厚生年金等級
     * @param insuredBurden       被保険者負担
     * @param employeeBurden      事業主負担
     */
    public static GradeWelfarePensionInsurancePremiumDto fromDomainToDto(GradeWelfarePensionInsurancePremium domain) {
        return new GradeWelfarePensionInsurancePremiumDto(domain.getWelfarePensionGrade(), ContributionFeeDto.fromDomainToDto(domain.getInsuredBurden()), ContributionFeeDto.fromDomainToDto(domain.getEmployeeBurden()));
    }
}
