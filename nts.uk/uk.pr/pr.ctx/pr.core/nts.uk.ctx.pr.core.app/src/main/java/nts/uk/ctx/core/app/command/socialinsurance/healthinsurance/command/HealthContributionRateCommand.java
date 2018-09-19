package nts.uk.ctx.core.app.command.socialinsurance.healthinsurance.command;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthContributionRate;

@Data
@AllArgsConstructor
public class HealthContributionRateCommand {
    /**
     * 介護保険料率
     */
    private BigDecimal longCareInsuranceRate;

    /**
     * 基本保険料率
     */
    private BigDecimal basicInsuranceRate;

    /**
     * 健康保険料率
     */
    private BigDecimal healthInsuranceRate;

    /**
     * 端数区分
     */
    private Integer fractionCls;

    /**
     * 特定保険料率
     */
    private BigDecimal specialInsuranceRate;
    
    public HealthContributionRate fromCommandToDomain(){
    	return new HealthContributionRate(this.longCareInsuranceRate, this.basicInsuranceRate, this.healthInsuranceRate, this.fractionCls, this.specialInsuranceRate);
    }
}
