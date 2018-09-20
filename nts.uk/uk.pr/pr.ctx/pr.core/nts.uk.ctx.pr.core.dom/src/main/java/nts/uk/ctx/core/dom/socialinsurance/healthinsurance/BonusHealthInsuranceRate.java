package nts.uk.ctx.core.dom.socialinsurance.healthinsurance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.EmployeeShareAmountMethod;

import java.math.BigDecimal;

/**
 * 賞与健康保険料率
 */
@Getter
@AllArgsConstructor
public class BonusHealthInsuranceRate extends AggregateRoot {

    /**
     * 履歴ID
     */
    private String historyID;

    /**
     * 事業主負担分計算方法
     */
    private EmployeeShareAmountMethod employeeShareAmountMethod;

    /**
     * 個人負担率
     */
    private HealthContributionRate individualBurdenRatio;

    /**
     * 事業主負担率
     */
    private HealthContributionRate employeeBurdenRatio;

    /**
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
     */
    public BonusHealthInsuranceRate(String historyId, int employeeShareAmountMethod,
                                    BigDecimal individualLongCareInsuranceRate, BigDecimal individualBasicInsuranceRate, BigDecimal individualHealthInsuranceRate, int individualFractionCls, BigDecimal individualSpecialInsuranceRate,
                                    BigDecimal employeeLongCareInsuranceRate, BigDecimal employeeBasicInsuranceRate, BigDecimal employeeHealthInsuranceRate, int employeeFractionCls, BigDecimal employeeSpecialInsuranceRate) {
        this.historyID = historyId;
        this.employeeShareAmountMethod = EnumAdaptor.valueOf(employeeShareAmountMethod, EmployeeShareAmountMethod.class);
        this.individualBurdenRatio = new HealthContributionRate(individualLongCareInsuranceRate, individualBasicInsuranceRate, individualHealthInsuranceRate, individualFractionCls, individualSpecialInsuranceRate);
        this.employeeBurdenRatio = new HealthContributionRate(employeeLongCareInsuranceRate, employeeBasicInsuranceRate, employeeHealthInsuranceRate, employeeFractionCls, employeeSpecialInsuranceRate);
    }

    /**
     * @param historyID                 履歴ID
     * @param employeeShareAmountMethod 事業主負担分計算方法
     * @param individualBurdenRatio     個人負担率
     * @param employeeBurdenRatio       事業主負担率
     */
    public BonusHealthInsuranceRate(String historyID, int employeeShareAmountMethod,
                                    HealthContributionRate individualBurdenRatio, HealthContributionRate employeeBurdenRatio) {
        super();
        this.historyID = historyID;
        this.employeeShareAmountMethod = EnumAdaptor.valueOf(employeeShareAmountMethod, EmployeeShareAmountMethod.class);
        this.individualBurdenRatio = individualBurdenRatio;
        this.employeeBurdenRatio = employeeBurdenRatio;
    }
}
