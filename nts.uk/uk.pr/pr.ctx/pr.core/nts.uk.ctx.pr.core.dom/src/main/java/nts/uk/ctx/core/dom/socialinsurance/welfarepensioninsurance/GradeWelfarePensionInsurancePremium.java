package nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 等級毎厚生年金保険料
 */
@Getter
public class GradeWelfarePensionInsurancePremium extends DomainObject {

    /**
     * 厚生年金等級
     */
    private int welfarePensionGrade;

    /**
     * 被保険者負担
     */
    private ContributionFee insuredBurden;

    /**
     * 事業主負担
     */
    private ContributionFee employeeBurden;

    /**
     * 等級毎厚生年金保険料
     *
     * @param welfarePensionGrade 厚生年金等級
     * @param insuredBurden       被保険者負担
     * @param employeeBurden      事業主負担
     */
    public GradeWelfarePensionInsurancePremium(int welfarePensionGrade, ContributionFee insuredBurden, ContributionFee employeeBurden) {
        this.welfarePensionGrade = welfarePensionGrade;
        this.insuredBurden       = insuredBurden;
        this.employeeBurden      = employeeBurden;
    }
}
