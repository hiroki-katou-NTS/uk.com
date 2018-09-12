package nts.uk.ctx.core.app.command.socialinsurance.welfarepensioninsurance.command;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.EmployeesPensionContributionRate;

/**
 * 厚生年金各負担率
 */
@Getter
@AllArgsConstructor
public class EmployeesPensionContributionRateCommand{

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
    public EmployeesPensionContributionRate fromCommandToDomain () {
    	return new EmployeesPensionContributionRate(this.individualBurdenRatio, this.employeeContributionRatio, this.individualExemptionRate, this.employeeExemptionRate);
    }
}