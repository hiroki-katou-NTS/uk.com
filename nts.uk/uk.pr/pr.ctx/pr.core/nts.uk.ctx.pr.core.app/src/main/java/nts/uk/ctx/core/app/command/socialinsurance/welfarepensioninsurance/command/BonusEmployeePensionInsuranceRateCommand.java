package nts.uk.ctx.core.app.command.socialinsurance.welfarepensioninsurance.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.BonusEmployeePensionInsuranceRate;

/**
 * 賞与厚生年金保険料率
 */
@Getter
@AllArgsConstructor
public class BonusEmployeePensionInsuranceRateCommand{

    /**
     * 履歴ID
     */
    private String historyId;

    /**
     * 事業主負担分計算方法
     */
    private int employeeShareAmountMethod;

    /**
     * 男子負担率
     */
    private EmployeesPensionContributionRateCommand maleContributionRate;

    /**
     * 女子負担率
     */
    private EmployeesPensionContributionRateCommand femaleContributionRate;

    /**
     * 端数区分
     */
    private EmployeesPensionClassificationCommand fractionClassification;

    /**
     * 賞与厚生年金保険料率
     *
     * @param historyId                 履歴ID
     * @param employeeShareAmountMethod 事業主負担分計算方法
     * @param maleContributionRate      男子負担率
     * @param femaleContributionRate    女子負担率
     * @param fractionClassification    端数区分
     */
    public BonusEmployeePensionInsuranceRate fromCommandToDomain () {
    	return new BonusEmployeePensionInsuranceRate(this.historyId, this.employeeShareAmountMethod, maleContributionRate.fromCommandToDomain(), femaleContributionRate.fromCommandToDomain(), this.fractionClassification.fromCommandToDomain());
    }
}
