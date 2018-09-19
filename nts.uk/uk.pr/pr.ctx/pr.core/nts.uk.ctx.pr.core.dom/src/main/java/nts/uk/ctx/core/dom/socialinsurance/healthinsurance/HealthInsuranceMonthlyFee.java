package nts.uk.ctx.core.dom.socialinsurance.healthinsurance;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.socialinsurance.AutoCalculationExecutionCls;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.EmployeeShareAmountMethod;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.InsurancePremiumFractionClassification;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

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

    @Inject
    private HealthInsuranceStandardMonthlyRepository healthInsuranceStandardMonthlyRepository;

    @Inject
    private HealthInsuranceMonthlyFeeRepository healthInsuranceMonthlyFee;

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
        this.autoCalculationCls = EnumAdaptor.valueOf(autoCalculationCls, AutoCalculationExecutionCls.class);
        this.healthInsurancePerGradeFee = healthInsurancePerGradeFee;
    }

    /**
     * アルゴリズム「月額健康保険料計算処理」を実行する
     *
     * @param screenMode     screenMode
     * @param startYearMonth startYearMonth
     */
    public void algorithmMonthlyHealthInsurancePremiumCalculation(ScreenMode screenMode, int startYearMonth, BonusHealthInsuranceRate bonusHealthInsuranceRate) {

        //「自動計算区分」で「する」を選択している場合
        if (AutoCalculationExecutionCls.AUTO.equals(this.getAutoCalculationCls())) {
            //ドメインモデル「健康保険標準月額」を全て取得する
            val healthInsuranceStandardMonthlyOptional = this.healthInsuranceStandardMonthlyRepository.getHealthInsuranceStandardMonthlyByStartYearMonth(startYearMonth);
            if (healthInsuranceStandardMonthlyOptional.isPresent()) {
                val standardGradePerMonth = healthInsuranceStandardMonthlyOptional.get().getStandardGradePerMonth();

                standardGradePerMonth.forEach(x -> {
                    val healthInsuranceGrade = x.getHealthInsuranceGrade();
                    val standardMonthlyFee = x.getStandardMonthlyFee();

                    val individualBurdenRatio = this.getHealthInsuranceRate().getIndividualBurdenRatio();
                    val employeeBurdenRatio = this.getHealthInsuranceRate().getEmployeeBurdenRatio();
                    val individualFractionCls = individualBurdenRatio.getFractionCls();
                    val bonusHealthInsuranceRateIndividualBurden = bonusHealthInsuranceRate.getIndividualBurdenRatio();

                    //取得した値と画面上の値を元に、「健康保険月額保険料額.等級毎健康保険料.被保険者負担」の計算処理を実施する
                    val insuredHealthInsurancePremium = calculationHealthInsuranceContributionFee(standardMonthlyFee, individualBurdenRatio.getHealthInsuranceRate().v(), individualFractionCls);
                    val insuredNursingCare = calculationHealthInsuranceContributionFee(standardMonthlyFee, individualBurdenRatio.getLongCareInsuranceRate().v(), individualFractionCls);
                    val insuredSpecInsurancePremium = calculationHealthInsuranceContributionFee(standardMonthlyFee, individualBurdenRatio.getSpecialInsuranceRate().v(), individualFractionCls);
                    val insuredBasicInsurancePremium = calculationHealthInsuranceContributionFee(standardMonthlyFee, individualBurdenRatio.getBasicInsuranceRate().v(), individualFractionCls);

                    val employeeShareAmountMethod = this.getHealthInsuranceRate().getEmployeeShareAmountMethod();
                    BigDecimal employeeHealthInsurancePremium;
                    BigDecimal employeeNursingCare;
                    BigDecimal employeeSpecInsurancePremium;
                    BigDecimal employeeBasicInsurancePremium;

                    //取得した値と画面上の値を元に、「健康保険月額保険料額.等級毎健康保険料.事業主負担」の計算処理を実施する
                    //「事業主負担率を用いて計算する」が選択されている場合
                    if (EmployeeShareAmountMethod.SUBTRACT_INSURANCE_PREMIUM.equals(employeeShareAmountMethod)) {
                        employeeHealthInsurancePremium = calculationHealthInsuranceContributionFee(standardMonthlyFee, bonusHealthInsuranceRateIndividualBurden.getHealthInsuranceRate().v(), individualFractionCls);
                        employeeNursingCare = calculationHealthInsuranceContributionFee(standardMonthlyFee, bonusHealthInsuranceRateIndividualBurden.getLongCareInsuranceRate().v(), individualFractionCls);
                        employeeSpecInsurancePremium = calculationHealthInsuranceContributionFee(standardMonthlyFee, bonusHealthInsuranceRateIndividualBurden.getSpecialInsuranceRate().v(), individualFractionCls);
                        employeeBasicInsurancePremium = calculationHealthInsuranceContributionFee(standardMonthlyFee, bonusHealthInsuranceRateIndividualBurden.getBasicInsuranceRate().v(), individualFractionCls);
                    } else {
                        employeeHealthInsurancePremium = calculationHealthInsuranceContributionFee(standardMonthlyFee, employeeBurdenRatio.getHealthInsuranceRate().v(), individualFractionCls);
                        employeeNursingCare = calculationHealthInsuranceContributionFee(standardMonthlyFee, employeeBurdenRatio.getLongCareInsuranceRate().v(), individualFractionCls);
                        employeeSpecInsurancePremium = calculationHealthInsuranceContributionFee(standardMonthlyFee, employeeBurdenRatio.getSpecialInsuranceRate().v(), individualFractionCls);
                        employeeBasicInsurancePremium = calculationHealthInsuranceContributionFee(standardMonthlyFee, employeeBurdenRatio.getBasicInsuranceRate().v(), individualFractionCls);
                    }

                    this.healthInsurancePerGradeFee.add(new HealthInsurancePerGradeFee(healthInsuranceGrade, employeeHealthInsurancePremium, employeeNursingCare, employeeSpecInsurancePremium, employeeBasicInsurancePremium,
                            insuredHealthInsurancePremium, insuredNursingCare, insuredSpecInsurancePremium, insuredBasicInsurancePremium));
                });
            }
        }

        //ドメインモデル「健康保険月額保険料額」を更新する, ドメインモデル「健康保険月額保険料額」を追加する
        this.healthInsuranceMonthlyFee.addOrUpdate(this, screenMode);
    }

    /**
     * 計算した値を端数処理する
     *
     * @param standardMonthlyFee standardMonthlyFee
     * @param value              value
     * @param fractionCls        InsurancePremiumFractionClassification
     * @return Round value
     */
    private BigDecimal calculationHealthInsuranceContributionFee(long standardMonthlyFee, BigDecimal value, InsurancePremiumFractionClassification fractionCls) {
        Double calculation = standardMonthlyFee * value.doubleValue() / 1000;
        switch (fractionCls) {
            //切り捨て
            case TRUNCATION:
                return new BigDecimal(calculation).setScale(0, RoundingMode.DOWN);
            //切り上げ
            case ROUND_UP:
                return new BigDecimal(calculation).setScale(0, RoundingMode.UP);
            //四捨五入
            case ROUND_4_UP_5:
                return new BigDecimal(calculation).setScale(0, RoundingMode.HALF_UP);
            //五捨六入
            case ROUND_5_UP_6:
                return new BigDecimal(calculation).setScale(0, RoundingMode.HALF_DOWN);
            //五捨五超入
            case ROUND_SUPER_5:
                return new BigDecimal(Math.floor(calculation + 0.4000000000));
            default:
                return new BigDecimal(0);
        }
    }
}