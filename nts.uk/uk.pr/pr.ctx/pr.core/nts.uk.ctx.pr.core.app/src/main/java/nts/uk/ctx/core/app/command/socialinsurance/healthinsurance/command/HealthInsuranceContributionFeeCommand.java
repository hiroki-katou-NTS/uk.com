package nts.uk.ctx.core.app.command.socialinsurance.healthinsurance.command;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceContributionFee;

/**
 * 各負担料
 */
@AllArgsConstructor
@Data
public class HealthInsuranceContributionFeeCommand {
    /**
     * 健康保険料
     */
    private BigDecimal healthInsurancePremium;

    /**
     * 介護保険料
     */
    private BigDecimal nursingCare;

    /**
     * 特定保険料
     */
    private BigDecimal specInsurancePremium;

    /**
     * 基本保険料
     */
    private BigDecimal basicInsurancePremium;
    
    public HealthInsuranceContributionFee fromCommandToDomain (){
    	return new HealthInsuranceContributionFee(this.healthInsurancePremium, this.nursingCare, this.specInsurancePremium, this.basicInsurancePremium);
    }
}
