package nts.uk.ctx.pr.core.app.command.socialinsurance.welfarepensioninsurance.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.SalaryEmployeesPensionInsuranceRate;

/**
 * 給与厚生年金保険料率
 */
@Data
@AllArgsConstructor
public class SalaryEmployeesPensionInsuranceRateCommand {
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
     * 給与厚生年金保険料率
     *
     * @param employeeShareAmountMethod 事業主負担分計算方法
     * @param maleContributionRate      男子負担率
     * @param femaleContributionRate    女子負担率
     * @param fractionClassification    端数区分
     */
    public SalaryEmployeesPensionInsuranceRate fromCommandToDomain() {
        return new SalaryEmployeesPensionInsuranceRate(this.employeeShareAmountMethod, this.maleContributionRate.fromCommandToDomain(), this.femaleContributionRate.fromCommandToDomain(), this.fractionClassification.fromCommandToDomain());
    }
}
