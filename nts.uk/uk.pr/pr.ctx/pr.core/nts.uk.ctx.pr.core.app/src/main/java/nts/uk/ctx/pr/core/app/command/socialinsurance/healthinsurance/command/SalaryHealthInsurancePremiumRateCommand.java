package nts.uk.ctx.pr.core.app.command.socialinsurance.healthinsurance.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.pr.core.dom.socialinsurance.healthinsurance.SalaryHealthInsurancePremiumRate;

/**
 * 給与健康保険料率
 */
@AllArgsConstructor
@Data
public class SalaryHealthInsurancePremiumRateCommand {
    /**
     * 事業主負担分計算方法
     */
    private Integer employeeShareAmountMethod;

    /**
     * 個人負担率
     */
    private HealthContributionRateCommand individualBurdenRatio;

    /**
     * 事業主負担率
     */
    private HealthContributionRateCommand employeeBurdenRatio;
    
    public SalaryHealthInsurancePremiumRate fromCommandToDomain (){
    	return new SalaryHealthInsurancePremiumRate(this.employeeShareAmountMethod, this.individualBurdenRatio.fromCommandToDomain(), this.employeeBurdenRatio.fromCommandToDomain());
    }
}
