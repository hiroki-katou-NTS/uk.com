package nts.uk.ctx.core.app.command.socialinsurance.welfarepensioninsurance.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.GradeWelfarePensionInsurancePremium;

/**
 * 等級毎厚生年金保険料
 */
@AllArgsConstructor
@Data
public class GradeWelfarePensionInsurancePremiumCommand {
	/**
     * 厚生年金等級
     */
    private int welfarePensionGrade;

    /**
     * 被保険者負担
     */
    private ContributionFeeCommand insuredBurden;

    /**
     * 事業主負担
     */
    private ContributionFeeCommand employeeBurden;

    /**
     * 等級毎厚生年金保険料
     *
     * @param welfarePensionGrade 厚生年金等級
     * @param insuredBurden       被保険者負担
     * @param employeeBurden      事業主負担
     */
    public GradeWelfarePensionInsurancePremium fromDomainToDto() {
        return new GradeWelfarePensionInsurancePremium(this.welfarePensionGrade, insuredBurden.fromCommandToDomain(), employeeBurden.fromCommandToDomain());
    }
}
