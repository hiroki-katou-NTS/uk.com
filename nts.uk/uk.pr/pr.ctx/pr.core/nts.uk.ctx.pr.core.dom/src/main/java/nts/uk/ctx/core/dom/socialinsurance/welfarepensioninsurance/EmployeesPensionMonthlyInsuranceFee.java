package nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance;

import lombok.Getter;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.core.dom.socialinsurance.AutoCalculationExecutionCls;
import nts.uk.ctx.core.dom.socialinsurance.RoundCalculatedValue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        this.historyId = historyId;
        this.autoCalculationCls = EnumAdaptor.valueOf(autoCalculationCls, AutoCalculationExecutionCls.class);
        this.pensionInsurancePremium = pensionInsurancePremium;
        this.salaryEmployeesPensionInsuranceRate = salaryEmployeesPensionInsuranceRate;
    }

    /**
     * アルゴリズム「月額厚生年金保険料計算処理」を実行する
     *
     * @param welfarePensionStandardMonthlyFee Get WelfarePensionStandardMonthlyFee by
     */
    public void algorithmMonthlyWelfarePensionInsuranceFeeCalculation(Optional<WelfarePensionStandardMonthlyFee> welfarePensionStandardMonthlyFee,
                                                                      Optional<WelfarePensionInsuranceClassification> welfarePensionInsuranceCls) {

        //ドメインモデル「厚生年金標準月額」を全て取得する
        welfarePensionStandardMonthlyFee.ifPresent(x -> x.getStandardMonthlyPrice().forEach(stdMonthlyFee -> {
            val healthInsuranceGrade = stdMonthlyFee.getWelfarePensionGrade();
            val standardMonthlyFee   = stdMonthlyFee.getStandardMonthlyFee();

            welfarePensionInsuranceCls.ifPresent(welfarePension -> {

                //被保険者負担
                BigDecimal insuredMaleInsurancePremium;
                BigDecimal insuredMaleExemptionInsurance = null;
                BigDecimal insuredFemaleInsurancePremium;
                BigDecimal insuredFemaleExemptionInsurance = null;

                //事業主負担
                BigDecimal employeeMaleInsurancePremium = null;
                BigDecimal employeeMaleExemptionInsurance = null;
                BigDecimal employeeFemaleInsurancePremium = null;
                BigDecimal employeeFemaleExemptionInsurance = null;

                //男子負担率
                val maleContributionRate      = this.salaryEmployeesPensionInsuranceRate.getMaleContributionRate();
                //女子負担率
                val femaleContributionRate    = this.salaryEmployeesPensionInsuranceRate.getFemaleContributionRate();
                //C5_8 給与個人端数区分
                val personalFraction          = this.salaryEmployeesPensionInsuranceRate.getFractionClassification().getPersonalFraction();
                //C5_10 給与事業主端数区分
                val businessOwnerFraction     = this.salaryEmployeesPensionInsuranceRate.getFractionClassification().getBusinessOwnerFraction();
                //事業主負担分計算方法
                val employeeShareAmountMethod = this.salaryEmployeesPensionInsuranceRate.getEmployeeShareAmountMethod();

                //「厚生年金基金加入区分」で「無」を選択している場合
                if (FundClassification.NOT_JOIN.equals(welfarePension.getFundClassification())) {
                    insuredMaleInsurancePremium   = RoundCalculatedValue.calculation(standardMonthlyFee, maleContributionRate.getIndividualBurdenRatio().v(), personalFraction, RoundCalculatedValue.ROUND_1_AFTER_DOT);
                    insuredFemaleInsurancePremium = RoundCalculatedValue.calculation(standardMonthlyFee, femaleContributionRate.getIndividualBurdenRatio().v(), personalFraction, RoundCalculatedValue.ROUND_1_AFTER_DOT);
                } else {
                    val individualMaleExemptionRate   = maleContributionRate.getIndividualExemptionRate().map(PrimitiveValueBase::v).orElse(new BigDecimal(0));
                    val individualFemaleExemptionRate = femaleContributionRate.getIndividualExemptionRate().map(PrimitiveValueBase::v).orElse(new BigDecimal(0));
                    insuredMaleInsurancePremium       = RoundCalculatedValue.calculation(standardMonthlyFee, maleContributionRate.getIndividualBurdenRatio().v().subtract(individualMaleExemptionRate), personalFraction, RoundCalculatedValue.ROUND_1_AFTER_DOT);
                    insuredMaleExemptionInsurance     = RoundCalculatedValue.calculation(standardMonthlyFee, individualMaleExemptionRate, personalFraction, RoundCalculatedValue.ROUND_1_AFTER_DOT);
                    insuredFemaleInsurancePremium     = RoundCalculatedValue.calculation(standardMonthlyFee, femaleContributionRate.getIndividualBurdenRatio().v().subtract(individualFemaleExemptionRate), personalFraction, RoundCalculatedValue.ROUND_1_AFTER_DOT);
                    insuredFemaleExemptionInsurance   = RoundCalculatedValue.calculation(standardMonthlyFee, individualFemaleExemptionRate, personalFraction, RoundCalculatedValue.ROUND_1_AFTER_DOT);
                }

                //「厚生年金基金加入区分」で「無」を選択している場合,「事業主負担率を用いて計算する」が選択されている場合
                if (FundClassification.NOT_JOIN.equals(welfarePension.getFundClassification()) && EmployeeShareAmountMethod.EMPLOYEE_CONTRIBUTION_RATIO.equals(employeeShareAmountMethod)) {
                    employeeMaleInsurancePremium   = RoundCalculatedValue.calculation(standardMonthlyFee, maleContributionRate.getEmployeeContributionRatio().v(), businessOwnerFraction, RoundCalculatedValue.ROUND_1_AFTER_DOT);
                    employeeFemaleInsurancePremium = RoundCalculatedValue.calculation(standardMonthlyFee, femaleContributionRate.getEmployeeContributionRatio().v(), businessOwnerFraction, RoundCalculatedValue.ROUND_1_AFTER_DOT);
                }
                //「厚生年金基金加入区分」で「無」を選択している場合,「全体の保険料から被保険者分を差し引く」が選択されている場合
                else if (FundClassification.NOT_JOIN.equals(welfarePension.getFundClassification()) && EmployeeShareAmountMethod.SUBTRACT_INSURANCE_PREMIUM.equals(employeeShareAmountMethod)) {

                }
                //「厚生年金基金加入区分」で「有」を選択している場合,「事業主負担率を用いて計算する」が選択されている場合
                else if (FundClassification.JOIN.equals(welfarePension.getFundClassification()) && EmployeeShareAmountMethod.EMPLOYEE_CONTRIBUTION_RATIO.equals(employeeShareAmountMethod)) {
                    val employeeMaleExemptionRate    = maleContributionRate.getEmployeeExemptionRate().map(PrimitiveValueBase::v).orElse(new BigDecimal(0));
                    val employeeFemaleExemptionRate  = femaleContributionRate.getEmployeeExemptionRate().map(PrimitiveValueBase::v).orElse(new BigDecimal(0));
                    employeeMaleInsurancePremium     = RoundCalculatedValue.calculation(standardMonthlyFee, maleContributionRate.getEmployeeContributionRatio().v().subtract(employeeMaleExemptionRate), businessOwnerFraction, RoundCalculatedValue.ROUND_1_AFTER_DOT);
                    employeeMaleExemptionInsurance   = RoundCalculatedValue.calculation(standardMonthlyFee, employeeMaleExemptionRate, businessOwnerFraction, RoundCalculatedValue.ROUND_1_AFTER_DOT);
                    employeeFemaleInsurancePremium   = RoundCalculatedValue.calculation(standardMonthlyFee, femaleContributionRate.getEmployeeContributionRatio().v().subtract(employeeFemaleExemptionRate), businessOwnerFraction, RoundCalculatedValue.ROUND_1_AFTER_DOT);
                    employeeFemaleExemptionInsurance = RoundCalculatedValue.calculation(standardMonthlyFee, employeeFemaleExemptionRate, businessOwnerFraction, RoundCalculatedValue.ROUND_1_AFTER_DOT);
                }
                //「厚生年金基金加入区分」で「有」を選択している場合,「全体の保険料から被保険者分を差し引く」が選択されている場合
                else if (FundClassification.JOIN.equals(welfarePension.getFundClassification()) && EmployeeShareAmountMethod.SUBTRACT_INSURANCE_PREMIUM.equals(employeeShareAmountMethod)) {

                }

                pensionInsurancePremium.add(new GradeWelfarePensionInsurancePremium(
                        healthInsuranceGrade,
                        new ContributionFee(insuredFemaleInsurancePremium, insuredMaleInsurancePremium, insuredFemaleExemptionInsurance, insuredMaleExemptionInsurance),
                        new ContributionFee(employeeFemaleInsurancePremium, employeeMaleInsurancePremium, employeeFemaleExemptionInsurance, employeeMaleExemptionInsurance)
                ));
            });
        }));
    }
}
