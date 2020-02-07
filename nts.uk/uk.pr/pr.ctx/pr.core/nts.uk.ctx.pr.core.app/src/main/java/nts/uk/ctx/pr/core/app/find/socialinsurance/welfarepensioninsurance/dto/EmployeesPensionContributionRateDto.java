package nts.uk.ctx.pr.core.app.find.socialinsurance.welfarepensioninsurance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.EmployeesPensionContributionRate;

import java.math.BigDecimal;

/**
 * 厚生年金各負担率
 */
@Getter
@AllArgsConstructor
public class EmployeesPensionContributionRateDto{

    /**
     * 個人負担率
     */
    private BigDecimal individualBurdenRatio;

    /**
     * 事業主負担率
     */
    private BigDecimal employeeContributionRatio;

    /**
     * 個人免除率
     */
    private BigDecimal individualExemptionRate;

    /**
     * 事業主免除率
     */
    private BigDecimal employeeExemptionRate;

    /**
     * 厚生年金各負担率
     *
     * @param individualBurdenRatio     個人負担率
     * @param employeeContributionRatio 事業主負担率
     * @param individualExemptionRate   個人免除率
     * @param employeeExemptionRate     事業主免除率
     */
    public static EmployeesPensionContributionRateDto fromDomainToDto (EmployeesPensionContributionRate domain) {
    	BigDecimal individualExemptionRate = domain.getIndividualExemptionRate().isPresent() ? domain.getIndividualExemptionRate().get().v() : null;
    	BigDecimal employeeExemptionRate = domain.getEmployeeExemptionRate().isPresent() ? domain.getEmployeeExemptionRate().get().v() : null;
    	return new EmployeesPensionContributionRateDto(domain.getIndividualBurdenRatio().v(), domain.getEmployeeContributionRatio().v(), individualExemptionRate, employeeExemptionRate);
    }
}