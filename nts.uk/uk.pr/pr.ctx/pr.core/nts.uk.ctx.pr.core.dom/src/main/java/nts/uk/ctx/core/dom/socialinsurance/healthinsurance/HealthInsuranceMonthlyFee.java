package nts.uk.ctx.core.dom.socialinsurance.healthinsurance;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.socialinsurance.AutoCalculationExecutionCls;
import nts.uk.ctx.core.dom.socialinsurance.RoundCalculatedValue;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.EmployeeShareAmountMethod;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 健康保険月額保険料額
 */
@Getter
@NoArgsConstructor
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
                                     BigDecimal individualLongCareInsuranceRate, BigDecimal individualBasicInsuranceRate,
                                     BigDecimal individualHealthInsuranceRate, int individualFractionCls,
                                     BigDecimal individualSpecialInsuranceRate, BigDecimal employeeLongCareInsuranceRate,
                                     BigDecimal employeeBasicInsuranceRate, BigDecimal employeeHealthInsuranceRate, int employeeFractionCls,
                                     BigDecimal employeeSpecialInsuranceRate, int autoCalculationCls,
                                     List<HealthInsurancePerGradeFee> healthInsurancePerGradeFee) {
        this.historyId = historyId;
        this.healthInsuranceRate = new SalaryHealthInsurancePremiumRate(employeeShareAmountMethod,
                individualLongCareInsuranceRate, individualBasicInsuranceRate, individualHealthInsuranceRate,
                individualFractionCls, individualSpecialInsuranceRate, employeeLongCareInsuranceRate,
                employeeBasicInsuranceRate, employeeHealthInsuranceRate, employeeFractionCls,
                employeeSpecialInsuranceRate);
        this.autoCalculationCls = EnumAdaptor.valueOf(autoCalculationCls, AutoCalculationExecutionCls.class);
        this.healthInsurancePerGradeFee = healthInsurancePerGradeFee;
    }

    public HealthInsuranceMonthlyFee(String historyId, SalaryHealthInsurancePremiumRate healthInsuranceRate,
                                     int autoCalculationCls, List<HealthInsurancePerGradeFee> healthInsurancePerGradeFee) {
        super();
        this.historyId = historyId;
        this.healthInsuranceRate = healthInsuranceRate;
        this.autoCalculationCls = EnumAdaptor.valueOf(autoCalculationCls, AutoCalculationExecutionCls.class);
        this.healthInsurancePerGradeFee = healthInsurancePerGradeFee;
    }

    /**
     * アルゴリズム「月額健康保険料計算処理」を実行する
     *
     * @param healthInsuranceStandardMonthlyOptional HealthInsuranceStandardMonthly
     * @param bonusHealthInsuranceRate               BonusHealthInsuranceRate
     */
    public void algorithmMonthlyHealthInsurancePremiumCalculation(Optional<HealthInsuranceStandardMonthly> healthInsuranceStandardMonthlyOptional, BonusHealthInsuranceRate bonusHealthInsuranceRate) {
        // ドメインモデル「健康保険標準月額」を全て取得する
        this.healthInsurancePerGradeFee.clear();
        if (healthInsuranceStandardMonthlyOptional.isPresent()) {
            val standardGradePerMonth = healthInsuranceStandardMonthlyOptional.get().getStandardGradePerMonth();

            standardGradePerMonth.forEach(x -> {
                val healthInsuranceGrade = x.getHealthInsuranceGrade();
                val standardMonthlyFee = x.getStandardMonthlyFee();

                val individualBurdenRatio = this.getHealthInsuranceRate().getIndividualBurdenRatio();
                val employeeBurdenRatio = this.getHealthInsuranceRate().getEmployeeBurdenRatio();
                val individualFractionCls = individualBurdenRatio.getFractionCls();
                HealthContributionRate bonusHealthInsuranceRateIndividualBurden = null;
                if (!Objects.isNull(bonusHealthInsuranceRate)) {
                    bonusHealthInsuranceRateIndividualBurden = bonusHealthInsuranceRate.getIndividualBurdenRatio();
                }

                // 取得した値と画面上の値を元に、「健康保険月額保険料額.等級毎健康保険料.被保険者負担」の計算処理を実施する
                val insuredHealthInsurancePremium = RoundCalculatedValue.calculation(standardMonthlyFee,
                        individualBurdenRatio.getHealthInsuranceRate().v(), individualFractionCls, RoundCalculatedValue.ROUND_1_AFTER_DOT);
                val insuredNursingCare = RoundCalculatedValue.calculation(standardMonthlyFee,
                        individualBurdenRatio.getLongCareInsuranceRate().v(), individualFractionCls, RoundCalculatedValue.ROUND_1_AFTER_DOT);
                val insuredSpecInsurancePremium = RoundCalculatedValue.calculation(standardMonthlyFee,
                        individualBurdenRatio.getSpecialInsuranceRate().v(), individualFractionCls, RoundCalculatedValue.ROUND_3_AFTER_DOT);
                val insuredBasicInsurancePremium = RoundCalculatedValue.calculation(standardMonthlyFee,
                        individualBurdenRatio.getBasicInsuranceRate().v(), individualFractionCls, RoundCalculatedValue.ROUND_3_AFTER_DOT);

                val employeeShareAmountMethod = this.getHealthInsuranceRate().getEmployeeShareAmountMethod();
                BigDecimal employeeHealthInsurancePremium;
                BigDecimal employeeNursingCare;
                BigDecimal employeeSpecInsurancePremium;
                BigDecimal employeeBasicInsurancePremium;

                // 取得した値と画面上の値を元に、「健康保険月額保険料額.等級毎健康保険料.事業主負担」の計算処理を実施する
                // 「事業主負担率を用いて計算する」が選択されている場合
                if (EmployeeShareAmountMethod.SUBTRACT_INSURANCE_PREMIUM.equals(employeeShareAmountMethod) && !Objects.isNull(bonusHealthInsuranceRateIndividualBurden)) {
                    employeeHealthInsurancePremium = RoundCalculatedValue.calculation(standardMonthlyFee,
                            bonusHealthInsuranceRateIndividualBurden.getHealthInsuranceRate().v(),
                            individualFractionCls, RoundCalculatedValue.ROUND_1_AFTER_DOT);
                    employeeNursingCare = RoundCalculatedValue.calculation(standardMonthlyFee,
                            bonusHealthInsuranceRateIndividualBurden.getLongCareInsuranceRate().v(),
                            individualFractionCls, RoundCalculatedValue.ROUND_1_AFTER_DOT);
                    employeeSpecInsurancePremium = RoundCalculatedValue.calculation(standardMonthlyFee,
                            bonusHealthInsuranceRateIndividualBurden.getSpecialInsuranceRate().v(),
                            individualFractionCls, RoundCalculatedValue.ROUND_3_AFTER_DOT);
                    employeeBasicInsurancePremium = RoundCalculatedValue.calculation(standardMonthlyFee,
                            bonusHealthInsuranceRateIndividualBurden.getBasicInsuranceRate().v(), individualFractionCls,
                            RoundCalculatedValue.ROUND_3_AFTER_DOT);
                } else {
                    employeeHealthInsurancePremium = RoundCalculatedValue.calculation(standardMonthlyFee,
                            employeeBurdenRatio.getHealthInsuranceRate().v(), individualFractionCls, RoundCalculatedValue.ROUND_1_AFTER_DOT);
                    employeeNursingCare = RoundCalculatedValue.calculation(standardMonthlyFee,
                            employeeBurdenRatio.getLongCareInsuranceRate().v(), individualFractionCls,
                            RoundCalculatedValue.ROUND_1_AFTER_DOT);
                    employeeSpecInsurancePremium = RoundCalculatedValue.calculation(standardMonthlyFee,
                            employeeBurdenRatio.getSpecialInsuranceRate().v(), individualFractionCls,
                            RoundCalculatedValue.ROUND_3_AFTER_DOT);
                    employeeBasicInsurancePremium = RoundCalculatedValue.calculation(standardMonthlyFee,
                            employeeBurdenRatio.getBasicInsuranceRate().v(), individualFractionCls, RoundCalculatedValue.ROUND_3_AFTER_DOT);
                }

                this.healthInsurancePerGradeFee.add(new HealthInsurancePerGradeFee(healthInsuranceGrade,
                        employeeHealthInsurancePremium, employeeNursingCare, employeeSpecInsurancePremium,
                        employeeBasicInsurancePremium, insuredHealthInsurancePremium, insuredNursingCare,
                        insuredSpecInsurancePremium, insuredBasicInsurancePremium));
            });
        }
    }

    /**
     * アルゴリズム「月額健康保険料計算処理」を実行する
     *
     * @param healthInsuranceStandardMonthlyOptional HealthInsuranceStandardMonthly
     */
    public void algorithmMonthlyHealthInsurancePremiumCalculation(Optional<HealthInsuranceStandardMonthly> healthInsuranceStandardMonthlyOptional) {
        this.algorithmMonthlyHealthInsurancePremiumCalculation(healthInsuranceStandardMonthlyOptional, null);
    }

    public void updateGradeFee(List<HealthInsurancePerGradeFee> healthInsurancePerGradeFee) {
        this.healthInsurancePerGradeFee = healthInsurancePerGradeFee;
    }
}