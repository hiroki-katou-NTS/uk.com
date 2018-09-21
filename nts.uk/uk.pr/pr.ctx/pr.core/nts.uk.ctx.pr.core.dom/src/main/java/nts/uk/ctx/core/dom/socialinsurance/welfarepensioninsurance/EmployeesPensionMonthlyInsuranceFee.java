package nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance;

import lombok.Getter;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.core.dom.socialinsurance.AutoCalculationExecutionCls;
import nts.uk.ctx.core.dom.socialinsurance.RoundCalculatedValue;

import java.math.BigDecimal;
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
                                                                      WelfarePensionInsuranceClassification welfarePensionInsuranceCls) {

        //ドメインモデル「厚生年金標準月額」を全て取得する
    	this.pensionInsurancePremium.clear();
        welfarePensionStandardMonthlyFee.ifPresent(x -> x.getStandardMonthlyPrice().forEach(stdMonthlyFee -> {
            val healthInsuranceGrade = stdMonthlyFee.getWelfarePensionGrade();
            val standardMonthlyFee   = stdMonthlyFee.getStandardMonthlyFee();

            //被保険者負担
            BigDecimal insuredMaleInsurancePremium;
            BigDecimal insuredMaleExemptionInsurance   = null;
            BigDecimal insuredFemaleInsurancePremium;
            BigDecimal insuredFemaleExemptionInsurance = null;

            //事業主負担
            BigDecimal employeeMaleInsurancePremium     = null;
            BigDecimal employeeMaleExemptionInsurance   = null;
            BigDecimal employeeFemaleInsurancePremium   = null;
            BigDecimal employeeFemaleExemptionInsurance = null;

            //男子負担率
            val maleContributionRate                = this.salaryEmployeesPensionInsuranceRate.getMaleContributionRate();
            val maleIndividualBurdenRatio           = maleContributionRate.getIndividualBurdenRatio().v();                                                             //C3_9  男子給与個人負担率
            val maleEmployeeContributionRatio       = maleContributionRate.getEmployeeContributionRatio().v();                                                         //C3_11 男子給与事業主負担率
            val maleIndividualExemptionRate         = maleContributionRate.getIndividualExemptionRate().map(PrimitiveValueBase::v).orElse(new BigDecimal(0));     //C4_19 男子給与個人免除保険料率
            val maleEmployeeExemptionRate           = maleContributionRate.getEmployeeExemptionRate().map(PrimitiveValueBase::v).orElse(new BigDecimal(0));       //C4_21 男子給与事業主免除保険料率

            //女子負担率
            val femaleContributionRate              = this.salaryEmployeesPensionInsuranceRate.getFemaleContributionRate();
            val femaleIndividualBurdenRatio         = femaleContributionRate.getIndividualBurdenRatio().v();                                                            //C3_18  女子給与個人負担率
            val femaleEmployeeContributionRatio     = femaleContributionRate.getEmployeeContributionRatio().v();                                                        //C3_20 女子給与事業主負担率
            val femaleIndividualExemptionRate       = femaleContributionRate.getIndividualExemptionRate().map(PrimitiveValueBase::v).orElse(new BigDecimal(0));    //C4_38 女子給与個人免除保険料率
            val femaleEmployeeExemptionRate         = femaleContributionRate.getEmployeeExemptionRate().map(PrimitiveValueBase::v).orElse(new BigDecimal(0));      //C4_40 女子給与事業主免除保険料率

            val maleSalaryIndividualInsuranceRate   = maleIndividualBurdenRatio.subtract(maleIndividualExemptionRate);                                                  //C4_10 男子給与個人保険料率
            val maleSalaryEmployerInsuranceRate     = maleEmployeeContributionRatio.subtract(maleEmployeeExemptionRate);                                                //C4_12 男子給与事業主保険料率
            val femaleSalaryIndividualInsuranceRate = femaleIndividualBurdenRatio.subtract(femaleIndividualExemptionRate);                                              //C4_29 女子給与個人保険料率
            val femaleSalaryEmployerInsuranceRate   = femaleEmployeeContributionRatio.subtract(femaleEmployeeExemptionRate);                                            //C4_31 女子給与事業主保険料率

            val personalFraction                    = this.salaryEmployeesPensionInsuranceRate.getFractionClassification().getPersonalFraction();                       //C5_8 給与個人端数区分
            val businessOwnerFraction               = this.salaryEmployeesPensionInsuranceRate.getFractionClassification().getBusinessOwnerFraction();                  //C5_10 給与事業主端数区分
            val employeeShareAmountMethod           = this.salaryEmployeesPensionInsuranceRate.getEmployeeShareAmountMethod();                                          //事業主負担分計算方法

            //「厚生年金基金加入区分」で「無」を選択している場合
            if (FundClassification.NOT_JOIN.equals(welfarePensionInsuranceCls.getFundClassification())) {
                insuredMaleInsurancePremium      = RoundCalculatedValue.calculation(standardMonthlyFee, maleIndividualBurdenRatio, personalFraction, RoundCalculatedValue.ROUND_1_AFTER_DOT);
                insuredFemaleInsurancePremium    = RoundCalculatedValue.calculation(standardMonthlyFee, femaleIndividualBurdenRatio, personalFraction, RoundCalculatedValue.ROUND_1_AFTER_DOT);
            } else {
                insuredMaleInsurancePremium      = RoundCalculatedValue.calculation(standardMonthlyFee, maleSalaryIndividualInsuranceRate, personalFraction, RoundCalculatedValue.ROUND_1_AFTER_DOT);
                insuredMaleExemptionInsurance    = RoundCalculatedValue.calculation(standardMonthlyFee, maleIndividualExemptionRate, personalFraction, RoundCalculatedValue.ROUND_1_AFTER_DOT);
                insuredFemaleInsurancePremium    = RoundCalculatedValue.calculation(standardMonthlyFee, femaleSalaryIndividualInsuranceRate, personalFraction, RoundCalculatedValue.ROUND_1_AFTER_DOT);
                insuredFemaleExemptionInsurance  = RoundCalculatedValue.calculation(standardMonthlyFee, femaleIndividualExemptionRate, personalFraction, RoundCalculatedValue.ROUND_1_AFTER_DOT);
            }

            //「厚生年金基金加入区分」で「無」を選択している場合,「事業主負担率を用いて計算する」が選択されている場合
            if (FundClassification.NOT_JOIN.equals(welfarePensionInsuranceCls.getFundClassification()) && EmployeeShareAmountMethod.EMPLOYEE_CONTRIBUTION_RATIO.equals(employeeShareAmountMethod)) {
                employeeMaleInsurancePremium     = RoundCalculatedValue.calculation(standardMonthlyFee, maleEmployeeContributionRatio, businessOwnerFraction, RoundCalculatedValue.ROUND_1_AFTER_DOT);
                employeeFemaleInsurancePremium   = RoundCalculatedValue.calculation(standardMonthlyFee, femaleEmployeeContributionRatio, businessOwnerFraction, RoundCalculatedValue.ROUND_1_AFTER_DOT);
            }
            //「厚生年金基金加入区分」で「無」を選択している場合,「全体の保険料から被保険者分を差し引く」が選択されている場合
            else if (FundClassification.NOT_JOIN.equals(welfarePensionInsuranceCls.getFundClassification()) && EmployeeShareAmountMethod.SUBTRACT_INSURANCE_PREMIUM.equals(employeeShareAmountMethod)) {
                employeeMaleInsurancePremium     = RoundCalculatedValue.calculation(standardMonthlyFee, maleSalaryIndividualInsuranceRate.add(maleSalaryEmployerInsuranceRate).subtract(maleIndividualBurdenRatio), businessOwnerFraction, RoundCalculatedValue.ROUND_1_AFTER_DOT);
                employeeFemaleInsurancePremium   = RoundCalculatedValue.calculation(standardMonthlyFee, femaleSalaryIndividualInsuranceRate.add(femaleSalaryEmployerInsuranceRate).subtract(femaleIndividualBurdenRatio), businessOwnerFraction, RoundCalculatedValue.ROUND_1_AFTER_DOT);
            }
            //「厚生年金基金加入区分」で「有」を選択している場合,「事業主負担率を用いて計算する」が選択されている場合
            else if (FundClassification.JOIN.equals(welfarePensionInsuranceCls.getFundClassification()) && EmployeeShareAmountMethod.EMPLOYEE_CONTRIBUTION_RATIO.equals(employeeShareAmountMethod)) {
                employeeMaleInsurancePremium     = RoundCalculatedValue.calculation(standardMonthlyFee, maleSalaryEmployerInsuranceRate, businessOwnerFraction, RoundCalculatedValue.ROUND_1_AFTER_DOT);
                employeeMaleExemptionInsurance   = RoundCalculatedValue.calculation(standardMonthlyFee, maleEmployeeExemptionRate, businessOwnerFraction, RoundCalculatedValue.ROUND_1_AFTER_DOT);
                employeeFemaleInsurancePremium   = RoundCalculatedValue.calculation(standardMonthlyFee, femaleSalaryEmployerInsuranceRate, businessOwnerFraction, RoundCalculatedValue.ROUND_1_AFTER_DOT);
                employeeFemaleExemptionInsurance = RoundCalculatedValue.calculation(standardMonthlyFee, femaleEmployeeExemptionRate, businessOwnerFraction, RoundCalculatedValue.ROUND_1_AFTER_DOT);
            }
            //「厚生年金基金加入区分」で「有」を選択している場合,「全体の保険料から被保険者分を差し引く」が選択されている場合
            else if (FundClassification.JOIN.equals(welfarePensionInsuranceCls.getFundClassification()) && EmployeeShareAmountMethod.SUBTRACT_INSURANCE_PREMIUM.equals(employeeShareAmountMethod)) {
                employeeMaleInsurancePremium     = RoundCalculatedValue.calculation(standardMonthlyFee, maleSalaryIndividualInsuranceRate, businessOwnerFraction, RoundCalculatedValue.ROUND_1_AFTER_DOT);
                employeeMaleExemptionInsurance   = RoundCalculatedValue.calculation(standardMonthlyFee, maleSalaryEmployerInsuranceRate, businessOwnerFraction, RoundCalculatedValue.ROUND_1_AFTER_DOT);
                employeeFemaleInsurancePremium   = RoundCalculatedValue.calculation(standardMonthlyFee, femaleSalaryIndividualInsuranceRate, businessOwnerFraction, RoundCalculatedValue.ROUND_1_AFTER_DOT);
                employeeFemaleExemptionInsurance = RoundCalculatedValue.calculation(standardMonthlyFee, femaleSalaryEmployerInsuranceRate, businessOwnerFraction, RoundCalculatedValue.ROUND_1_AFTER_DOT);
            }

            pensionInsurancePremium.add(new GradeWelfarePensionInsurancePremium(
                    healthInsuranceGrade,
                    new ContributionFee(insuredFemaleInsurancePremium, insuredMaleInsurancePremium, insuredFemaleExemptionInsurance, insuredMaleExemptionInsurance),
                    new ContributionFee(employeeFemaleInsurancePremium, employeeMaleInsurancePremium, employeeFemaleExemptionInsurance, employeeMaleExemptionInsurance)
            ));
        }));
    }

    public void updateGradeList (List<GradeWelfarePensionInsurancePremium> pensionInsurancePremium){
        this.pensionInsurancePremium = pensionInsurancePremium;
    }
    
    public void changeDataWhenNotJoinFund (){
    	this.salaryEmployeesPensionInsuranceRate.changeDataWhenNotJoinFund();
    }
}
