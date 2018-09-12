package nts.uk.ctx.core.app.find.socialinsurance.welfarepensioninsurance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.SalaryEmployeesPensionInsuranceRate;

/**
 * 給与厚生年金保険料率
 */
@Data
@AllArgsConstructor
public class SalaryEmployeesPensionInsuranceRateDto {
	/**
     * 事業主負担分計算方法
     */
    private int employeeShareAmountMethod;

    /**
     * 男子負担率
     */
    private EmployeesPensionContributionRateDto maleContributionRate;

    /**
     * 女子負担率
     */
    private EmployeesPensionContributionRateDto femaleContributionRate;

    /**
     * 端数区分
     */
    private EmployeesPensionClassificationDto fractionClassification;

    /**
     * 給与厚生年金保険料率
     *
     * @param employeeShareAmountMethod 事業主負担分計算方法
     * @param maleContributionRate      男子負担率
     * @param femaleContributionRate    女子負担率
     * @param fractionClassification    端数区分
     */
    public static SalaryEmployeesPensionInsuranceRateDto fromDomainToDto(SalaryEmployeesPensionInsuranceRate domain) {
        return new SalaryEmployeesPensionInsuranceRateDto(domain.getEmployeeShareAmountMethod().value, EmployeesPensionContributionRateDto.fromDomainToDto(domain.getMaleContributionRate()), EmployeesPensionContributionRateDto.fromDomainToDto(domain.getFemaleContributionRate()), EmployeesPensionClassificationDto.fromDomainToDto(domain.getFractionClassification()));
    }
}
