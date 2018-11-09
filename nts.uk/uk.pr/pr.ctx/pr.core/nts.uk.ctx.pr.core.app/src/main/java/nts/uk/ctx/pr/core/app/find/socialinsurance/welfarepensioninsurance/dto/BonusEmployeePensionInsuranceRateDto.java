package nts.uk.ctx.pr.core.app.find.socialinsurance.welfarepensioninsurance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance.BonusEmployeePensionInsuranceRate;

/**
 * 賞与厚生年金保険料率
 */
@Getter
@AllArgsConstructor
public class BonusEmployeePensionInsuranceRateDto{

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
     * 賞与厚生年金保険料率
     *
     * @param historyId                 履歴ID
     * @param employeeShareAmountMethod 事業主負担分計算方法
     * @param maleContributionRate      男子負担率
     * @param femaleContributionRate    女子負担率
     * @param fractionClassification    端数区分
     */
    public static BonusEmployeePensionInsuranceRateDto fromDomainToDto (BonusEmployeePensionInsuranceRate domain) {
        return new BonusEmployeePensionInsuranceRateDto(domain.getHistoryId(), domain.getEmployeeShareAmountMethod().value, EmployeesPensionContributionRateDto.fromDomainToDto(domain.getMaleContributionRate()), EmployeesPensionContributionRateDto.fromDomainToDto(domain.getFemaleContributionRate()), EmployeesPensionClassificationDto.fromDomainToDto(domain.getFractionClassification()));
    }
}
