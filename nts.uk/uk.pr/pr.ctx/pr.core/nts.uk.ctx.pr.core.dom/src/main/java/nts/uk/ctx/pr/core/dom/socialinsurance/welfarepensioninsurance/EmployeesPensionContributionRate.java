package nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.core.dom.socialinsurance.InsuranceRate;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

/**
 * 厚生年金各負担率
 */
@Getter
public class EmployeesPensionContributionRate extends DomainObject {

    /**
     * 個人負担率
     */
    private InsuranceRate individualBurdenRatio;

    /**
     * 事業主負担率
     */
    private InsuranceRate employeeContributionRatio;

    /**
     * 個人免除率
     */
    private Optional<InsuranceRate> individualExemptionRate;

    /**
     * 事業主免除率
     */
    private Optional<InsuranceRate> employeeExemptionRate;

    /**
     * 厚生年金各負担率
     *
     * @param individualBurdenRatio     個人負担率
     * @param employeeContributionRatio 事業主負担率
     * @param individualExemptionRate   個人免除率
     * @param employeeExemptionRate     事業主免除率
     */
    public EmployeesPensionContributionRate(BigDecimal individualBurdenRatio, BigDecimal employeeContributionRatio, BigDecimal individualExemptionRate, BigDecimal employeeExemptionRate) {
        this.individualBurdenRatio     = new InsuranceRate(individualBurdenRatio);
        this.employeeContributionRatio = new InsuranceRate(employeeContributionRatio);
        this.individualExemptionRate   = Optional.ofNullable(Objects.isNull(individualExemptionRate) ? null : new InsuranceRate(individualExemptionRate));
        this.employeeExemptionRate     = Optional.ofNullable(Objects.isNull(employeeExemptionRate) ? null : new InsuranceRate(employeeExemptionRate));
    }
    
    public void changeDataWhenNotJoinFund () {
    	this.individualExemptionRate = Optional.empty();
    	this.employeeExemptionRate = Optional.empty();
    }
}