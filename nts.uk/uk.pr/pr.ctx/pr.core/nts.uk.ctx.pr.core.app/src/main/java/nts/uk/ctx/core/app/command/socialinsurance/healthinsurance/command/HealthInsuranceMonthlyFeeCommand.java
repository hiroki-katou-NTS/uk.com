package nts.uk.ctx.core.app.command.socialinsurance.healthinsurance.command;


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
public class HealthInsuranceMonthlyFeeCommand {
    /**
     * 履歴ID
     */
    private String historyId;

    /**
     * 健康保険料率
     */
    private SalaryHealthInsurancePremiumRateCommand healthInsuranceRate;

    /**
     * 自動計算区分
     */
    private int autoCalculationCls;

    /**
     * 等級毎健康保険料
     */
    private List<HealthInsurancePerGradeFeeCommand> healthInsurancePerGradeFee;

    /**
     * 健康保険月額保険料額
     *
     * @param optionalDomain HealthInsuranceMonthlyFee
     * @return HealthInsuranceMonthlyFeeDto
     */
    public HealthInsuranceMonthlyFee fromCommandToDomain() {
       return new HealthInsuranceMonthlyFee(this.historyId, this.healthInsuranceRate.fromCommandToDomain(), this.autoCalculationCls, this.healthInsurancePerGradeFee.stream().map(item -> {
    	   return item.fromCommandToDomain();
       }).collect(Collectors.toList()));
    }
}