package nts.uk.ctx.core.app.find.socialinsurance.healthinsurance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.val;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.BonusHealthInsuranceRate;

import java.util.Optional;


/**
 * 賞与健康保険料率
 */
@Data
@AllArgsConstructor
public class BonusHealthInsuranceRateDto {
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
    private HealthContributionRateDto individualBurdenRatio;

    /**
     * 事業主負担率
     */
    private HealthContributionRateDto employeeBurdenRatio;

    /**
     * 賞与健康保険料率
     *
     * @param optDomain BonusHealthInsuranceRate
     * @return BonusHealthInsuranceRateDto
     */
    public static BonusHealthInsuranceRateDto fromDomain(Optional<BonusHealthInsuranceRate> optDomain) {
        if (!optDomain.isPresent()) return null;
        val domain = optDomain.get();
        val individualBurden = domain.getIndividualBurdenRatio();
        val employeeBurden = domain.getEmployeeBurdenRatio();
        return new BonusHealthInsuranceRateDto(domain.getHistoryID(),
                domain.getEmployeeShareAmountMethod().value,
                new HealthContributionRateDto(individualBurden.getLongCareInsuranceRate().v(), individualBurden.getBasicInsuranceRate().v(), individualBurden.getHealthInsuranceRate().v(), individualBurden.getFractionCls().value, individualBurden.getSpecialInsuranceRate().v()),
                new HealthContributionRateDto(employeeBurden.getLongCareInsuranceRate().v(), employeeBurden.getBasicInsuranceRate().v(), employeeBurden.getHealthInsuranceRate().v(), employeeBurden.getFractionCls().value, employeeBurden.getSpecialInsuranceRate().v()));
    }
}
