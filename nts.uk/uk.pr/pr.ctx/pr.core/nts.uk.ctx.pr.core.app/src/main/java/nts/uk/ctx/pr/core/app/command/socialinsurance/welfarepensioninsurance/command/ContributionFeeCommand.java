package nts.uk.ctx.pr.core.app.command.socialinsurance.welfarepensioninsurance.command;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.ContributionFee;
/**
 * 各負担料
 */
@Data
@AllArgsConstructor
public class ContributionFeeCommand {
	/**
     * 女子保険料
     */
    private BigDecimal femaleInsurancePremium;

    /**
     * 男子保険料
     */
    private BigDecimal maleInsurancePremium;

    /**
     * 女子免除保険料
     */
    private BigDecimal femaleExemptionInsurance;

    /**
     * 男子免除保険料
     */
    private BigDecimal maleExemptionInsurance;
    
    public ContributionFee fromCommandToDomain () {
    	return new ContributionFee(this.femaleInsurancePremium,this.maleInsurancePremium, this.femaleExemptionInsurance, this.maleExemptionInsurance);
    }
}
