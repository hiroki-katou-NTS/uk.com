package nts.uk.ctx.core.app.command.socialinsurance.healthinsurance.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsurancePerGradeFee;

/**
 * 等級毎健康保険料
 */
@AllArgsConstructor
@Data
public class HealthInsurancePerGradeFeeCommand {
    /**
     * 健康保険等級
     */
    private int healthInsuranceGrade;

    /**
     * 事業主負担
     */
    private HealthInsuranceContributionFeeCommand employeeBurden;

    /**
     * 被保険者負担
     */
    private HealthInsuranceContributionFeeCommand insuredBurden;
    
    public HealthInsurancePerGradeFee fromCommandToDomain (){
    	return new HealthInsurancePerGradeFee(this.healthInsuranceGrade, this.employeeBurden.fromCommandToDomain(), this.insuredBurden.fromCommandToDomain());
    }
}
