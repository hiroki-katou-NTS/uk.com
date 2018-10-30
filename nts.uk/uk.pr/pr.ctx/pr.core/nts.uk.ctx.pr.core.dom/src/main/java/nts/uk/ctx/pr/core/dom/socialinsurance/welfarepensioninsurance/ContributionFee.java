package nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.core.dom.socialinsurance.InsuranceFee;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

/**
 * 各負担料
 */
@Getter
@EqualsAndHashCode
public class ContributionFee extends DomainObject {

    /**
     * 女子保険料
     */
    private InsuranceFee femaleInsurancePremium;

    /**
     * 男子保険料
     */
    private InsuranceFee maleInsurancePremium;

    /**
     * 女子免除保険料
     */
    private Optional<InsuranceFee> femaleExemptionInsurance;

    /**
     * 男子免除保険料
     */
    private Optional<InsuranceFee> maleExemptionInsurance;

    /**
     * 各負担料
     *
     * @param femaleInsurancePremium   女子保険料
     * @param maleInsurancePremium     男子保険料
     * @param femaleExemptionInsurance 女子免除保険料
     * @param maleExemptionInsurance   男子免除保険料
     */
    public ContributionFee(BigDecimal femaleInsurancePremium, BigDecimal maleInsurancePremium, BigDecimal femaleExemptionInsurance, BigDecimal maleExemptionInsurance) {
        this.femaleInsurancePremium   = new InsuranceFee(femaleInsurancePremium);
        this.maleInsurancePremium     = new InsuranceFee(maleInsurancePremium);
        this.femaleExemptionInsurance = Optional.ofNullable(Objects.isNull(femaleExemptionInsurance) ? null : new InsuranceFee(femaleExemptionInsurance));
        this.maleExemptionInsurance   = Optional.ofNullable(Objects.isNull(maleExemptionInsurance) ? null : new InsuranceFee(maleExemptionInsurance));
    }
}
