package nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.socialinsurance.AutoCalculationExecutionCls;

import java.util.List;

/**
 * 厚生年金月額保険料額
 */
@Getter
public class EmployeesPensionMonthlyInsuranceFee extends AggregateRoot {

    /**
     * 履歴ID
     */
    private String historyId;

    /**
     * 自動計算区分
     */
    private AutoCalculationExecutionCls autoCalculationCls;

    /**
     * 等級毎厚生年金保険料
     */
    private List<GradeWelfarePensionInsurancePremium> pensionInsurancePremium;

    /**
     * 給与厚生年金保険料率
     */
    private SalaryEmployeesPensionInsuranceRate salaryEmployeesPensionInsuranceRate;

    /**
     * 厚生年金月額保険料額
     *
     * @param historyId                           履歴ID
     * @param autoCalculationCls                  自動計算区分
     * @param pensionInsurancePremium             等級毎厚生年金保険料
     * @param salaryEmployeesPensionInsuranceRate 給与厚生年金保険料率
     */
    public EmployeesPensionMonthlyInsuranceFee(String historyId,
                                               int autoCalculationCls,
                                               List<GradeWelfarePensionInsurancePremium> pensionInsurancePremium,
                                               SalaryEmployeesPensionInsuranceRate salaryEmployeesPensionInsuranceRate) {
        this.historyId                           = historyId;
        this.autoCalculationCls                  = EnumAdaptor.valueOf(autoCalculationCls, AutoCalculationExecutionCls.class);
        this.pensionInsurancePremium             = pensionInsurancePremium;
        this.salaryEmployeesPensionInsuranceRate = salaryEmployeesPensionInsuranceRate;
    }
}
