package nts.uk.ctx.core.app.command.socialinsurance.healthinsurance.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.BonusHealthInsuranceRate;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.EmployeeShareAmountMethod;


/**
 * 賞与健康保険料率
 */
@Data
@AllArgsConstructor
public class BonusHealthInsuranceRateCommand {
    /**
     * 履歴ID
     */
    private String historyId;

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

    /**
     * 賞与健康保険料率
     *
     * @param optDomain BonusHealthInsuranceRate
     * @return BonusHealthInsuranceRateDto
     */
    public BonusHealthInsuranceRate fromCommandToDomain() {
        return new BonusHealthInsuranceRate(this.historyId, EnumAdaptor.valueOf(this.employeeShareAmountMethod, EmployeeShareAmountMethod.class), this.individualBurdenRatio.fromCommandToDomain(), this.employeeBurdenRatio.fromCommandToDomain());
    }
}
