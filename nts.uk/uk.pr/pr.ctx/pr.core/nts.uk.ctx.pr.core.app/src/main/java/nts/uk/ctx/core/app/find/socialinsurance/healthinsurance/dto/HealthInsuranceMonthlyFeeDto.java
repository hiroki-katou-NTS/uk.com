package nts.uk.ctx.core.app.find.socialinsurance.healthinsurance.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceMonthlyFee;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 健康保険月額保険料額
 */
@AllArgsConstructor
@Getter
public class HealthInsuranceMonthlyFeeDto {
    /**
     * 履歴ID
     */
    private String historyId;

    /**
     * 健康保険料率
     */
    private SalaryHealthInsurancePremiumRateDto healthInsuranceRate;

    /**
     * 自動計算区分
     */
    private Integer autoCalculationCls;

    /**
     * 等級毎健康保険料
     */
    private List<HealthInsurancePerGradeFeeDto> healthInsurancePerGradeFee;

    /**
     * 健康保険月額保険料額
     *
     * @param optionalDomain HealthInsuranceMonthlyFee
     * @return HealthInsuranceMonthlyFeeDto
     */
    public static HealthInsuranceMonthlyFeeDto fromDomain(Optional<HealthInsuranceMonthlyFee> optionalDomain) {
        if (!optionalDomain.isPresent()) return null;
        val domain                     = optionalDomain.get();
        val healthInsuranceRate        = domain.getHealthInsuranceRate();
        val individualBurden           = healthInsuranceRate.getIndividualBurdenRatio();
        val employeeBurden             = healthInsuranceRate.getEmployeeBurdenRatio();
        val healthInsurancePerGradeFee = domain.getHealthInsurancePerGradeFee();
        return new HealthInsuranceMonthlyFeeDto(
                domain.getHistoryId(),
                new SalaryHealthInsurancePremiumRateDto(healthInsuranceRate.getEmployeeShareAmountMethod().value,
                        new HealthContributionRateDto(individualBurden.getLongCareInsuranceRate().v(), individualBurden.getBasicInsuranceRate().v(), individualBurden.getHealthInsuranceRate().v(), individualBurden.getFractionCls().value, individualBurden.getSpecialInsuranceRate().v()),
                        new HealthContributionRateDto(employeeBurden.getLongCareInsuranceRate().v(), employeeBurden.getBasicInsuranceRate().v(), employeeBurden.getHealthInsuranceRate().v(), employeeBurden.getFractionCls().value, employeeBurden.getSpecialInsuranceRate().v())),
                domain.getAutoCalculationCls().value,
                healthInsurancePerGradeFee.stream().map(x ->
                        new HealthInsurancePerGradeFeeDto(x.getHealthInsuranceGrade(),
                                new HealthInsuranceContributionFeeDto(x.getEmployeeBurden().getHealthInsurancePremium().v(), x.getEmployeeBurden().getNursingCare().v(), x.getEmployeeBurden().getSpecInsurancePremium().v(), x.getEmployeeBurden().getBasicInsurancePremium().v()),
                                new HealthInsuranceContributionFeeDto(x.getInsuredBurden().getHealthInsurancePremium().v(), x.getInsuredBurden().getNursingCare().v(), x.getInsuredBurden().getSpecInsurancePremium().v(), x.getInsuredBurden().getBasicInsurancePremium().v()))
                ).collect(Collectors.toList())
        );
    }
}