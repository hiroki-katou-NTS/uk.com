package nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance;

import java.math.BigDecimal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 等級毎厚生年金保険料
 */
@Getter
@EqualsAndHashCode
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
    
    public GradeWelfarePensionInsurancePremium(int welfarePensionGrade, BigDecimal insuredFemaleInsurancePremium, BigDecimal insuredMaleInsurancePremium,
    		BigDecimal insuredFemaleExemptionInsurance, BigDecimal insuredMaleExemptionInsurance, BigDecimal employeeFemaleInsurancePremium, BigDecimal employeeMaleInsurancePremium,
    		BigDecimal employeeFemaleExemptionInsurance, BigDecimal employeeMaleExemptionInsurance
    		) {
        this.welfarePensionGrade = welfarePensionGrade;
        this.insuredBurden       =  new ContributionFee(insuredFemaleInsurancePremium, insuredMaleInsurancePremium, insuredFemaleExemptionInsurance, insuredMaleExemptionInsurance);
        this.employeeBurden      = new ContributionFee(employeeFemaleInsurancePremium, employeeMaleInsurancePremium, employeeFemaleExemptionInsurance, employeeMaleExemptionInsurance);
    }
}
