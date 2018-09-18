package nts.uk.ctx.core.dom.socialinsurance.healthinsurance;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.socialinsurance.AutoCalculationExecutionCls;

import java.math.BigDecimal;
import java.util.List;

/**
 * 健康保険月額保険料額
 */
@Getter
public class HealthInsuranceMonthlyFee extends AggregateRoot {

    /**
     * 履歴ID
     */
    private String historyId;

    /**
     * 健康保険料率
     */
    private SalaryHealthInsurancePremiumRate healthInsuranceRate;

    /**
     * 自動計算区分
     */
    private AutoCalculationExecutionCls autoCalculationCls;

    /**
     * 等級毎健康保険料
     */
    private List<HealthInsurancePerGradeFee> healthInsurancePerGradeFee;

    /**
     * 健康保険月額保険料額
     *
     * @param historyId                       履歴ID
     * @param employeeShareAmountMethod       事業主負担分計算方法
     * @param individualLongCareInsuranceRate 個人負担率介護保険料率
     * @param individualBasicInsuranceRate    個人負担率基本保険料率
     * @param individualHealthInsuranceRate   個人負担率健康保険料率
     * @param individualFractionCls           個人負担率端数区分
     * @param individualSpecialInsuranceRate  個人負担率特定保険料率
     * @param employeeLongCareInsuranceRate   事業主負担率介護保険料率
     * @param employeeBasicInsuranceRate      事業主負担率基本保険料率
     * @param employeeHealthInsuranceRate     事業主負担率健康保険料率
     * @param employeeFractionCls             事業主負担率端数区分
     * @param employeeSpecialInsuranceRate    事業主負担率特定保険料率
     * @param autoCalculationCls              自動計算区分
     * @param healthInsurancePerGradeFee      等級毎健康保険料
     */
    public HealthInsuranceMonthlyFee(String historyId, int employeeShareAmountMethod,
                                     BigDecimal individualLongCareInsuranceRate, BigDecimal individualBasicInsuranceRate, BigDecimal individualHealthInsuranceRate, int individualFractionCls, BigDecimal individualSpecialInsuranceRate,
                                     BigDecimal employeeLongCareInsuranceRate, BigDecimal employeeBasicInsuranceRate, BigDecimal employeeHealthInsuranceRate, int employeeFractionCls, BigDecimal employeeSpecialInsuranceRate,
                                     int autoCalculationCls, List<HealthInsurancePerGradeFee> healthInsurancePerGradeFee) {
        this.historyId                  = historyId;
        this.healthInsuranceRate        = new SalaryHealthInsurancePremiumRate(employeeShareAmountMethod, individualLongCareInsuranceRate, individualBasicInsuranceRate, individualHealthInsuranceRate, individualFractionCls, individualSpecialInsuranceRate,
                employeeLongCareInsuranceRate, employeeBasicInsuranceRate, employeeHealthInsuranceRate, employeeFractionCls, employeeSpecialInsuranceRate);
        this.autoCalculationCls         = EnumAdaptor.valueOf(autoCalculationCls, AutoCalculationExecutionCls.class);
        this.healthInsurancePerGradeFee = healthInsurancePerGradeFee;
    }

	public HealthInsuranceMonthlyFee(String historyId, SalaryHealthInsurancePremiumRate healthInsuranceRate,
			int autoCalculationCls,
			List<HealthInsurancePerGradeFee> healthInsurancePerGradeFee) {
		super();
		this.historyId = historyId;
		this.healthInsuranceRate = healthInsuranceRate;
		this.autoCalculationCls         = EnumAdaptor.valueOf(autoCalculationCls, AutoCalculationExecutionCls.class);
		this.healthInsurancePerGradeFee = healthInsurancePerGradeFee;
	}
}