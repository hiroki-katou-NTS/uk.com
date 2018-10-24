package nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.EmployeesPensionMonthlyInsuranceFee;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
import sun.awt.AppContext;

/**
 * 厚生年金月額保険料額
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_EMP_PEN_MON_INS_FEE")
public class QpbmtEmployeesPensionMonthlyInsuranceFee extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 履歴ID
     */
    @EmbeddedId
    public QpbmtEmployeesPensionMonthlyInsuranceFeePk welfarePenMonthlyPk;

    /**
     * 年月開始
     */
    @Basic(optional = false)
    @Column(name = "START_YEAR_MONTH")
    public int startYearMonth;

    /**
     * 年月終了
     */
    @Basic(optional = false)
    @Column(name = "END_YEAR_MONTH")
    public int endYearMonth;

    /**
     * 自動計算区分
     */
    @Basic(optional = false)
    @Column(name = "AUTO_CALCULATION_CLS")
    public int autoCalculationCls;

    /**
     * 事業主負担分計算方法
     */
    @Basic(optional = false)
    @Column(name = "EMPLOYEE_SHARE_AMOUNT_METHOD")
    public int employeeShareAmountMethod;

    /**
     * 女子個人負担率
     */
    @Basic(optional = false)
    @Column(name = "FEMALE_INDIVIDUAL_BURDEN_RATIO")
    public BigDecimal femaleIndividualBurdenRatio;

    /**
     * 女子事業主負担率
     */
    @Basic(optional = false)
    @Column(name = "FEMALE_EMPLOYEE_CONTRIBUTION_RATIO")
    public BigDecimal femaleEmployeeContributionRatio;

    /**
     * 女子個人免除率
     */
    @Basic(optional = true)
    @Column(name = "FEMALE_INDIVIDUAL_EXEMPTION_RATE")
    public BigDecimal femaleIndividualExemptionRate;

    /**
     * 女子事業主免除率
     */
    @Basic(optional = true)
    @Column(name = "FEMALE_EMPLOYER_EXEMPTION_RATE")
    public BigDecimal femaleEmployerExemptionRate;

    /**
     * 個人端数区分
     */
    @Basic(optional = false)
    @Column(name = "PERSONAL_FRACTION")
    public int personalFraction;

    /**
     * 事業主端数区分
     */
    @Basic(optional = false)
    @Column(name = "BUSINESS_OWNER_FRACTION")
    public int businessOwnerFraction;

    /**
     * 男子個人負担率
     */
    @Basic(optional = false)
    @Column(name = "MALE_INDIVIDUAL_BURDEN_RATIO")
    public BigDecimal maleIndividualBurdenRatio;

    /**
     * 男子事業主負担率
     */
    @Basic(optional = false)
    @Column(name = "MALE_EMPLOYEE_CONTRIBUTION_RATIO")
    public BigDecimal maleEmployeeContributionRatio;

    /**
     * 男子個人免除率
     */
    @Basic(optional = true)
    @Column(name = "MALE_INDIVIDUAL_EXEMPTION_RATE")
    public BigDecimal maleIndividualExemptionRate;

    /**
     * 男子事業主免除率
     */
    @Basic(optional = true)
    @Column(name = "MALE_EMPLOYER_EXEMPTION_RATE")
    public BigDecimal maleEmployerExemptionRate;

    @Override
    protected Object getKey() {
        return welfarePenMonthlyPk;
    }

    public static QpbmtEmployeesPensionMonthlyInsuranceFee toEntity(EmployeesPensionMonthlyInsuranceFee domain, String officeCode, YearMonthHistoryItem yearMonth) {
        return new QpbmtEmployeesPensionMonthlyInsuranceFee( new QpbmtEmployeesPensionMonthlyInsuranceFeePk(AppContexts.user().companyId(), officeCode , yearMonth.identifier()),
                yearMonth.start().v(),
                yearMonth.end().v(),
                domain.getAutoCalculationCls().value,
                domain.getSalaryEmployeesPensionInsuranceRate().getEmployeeShareAmountMethod().value,
                domain.getSalaryEmployeesPensionInsuranceRate().getFemaleContributionRate().getIndividualBurdenRatio().v(),
                domain.getSalaryEmployeesPensionInsuranceRate().getFemaleContributionRate().getEmployeeContributionRatio().v(),
                domain.getSalaryEmployeesPensionInsuranceRate().getFemaleContributionRate().getIndividualExemptionRate().map(PrimitiveValueBase::v).orElse(null),
                domain.getSalaryEmployeesPensionInsuranceRate().getFemaleContributionRate().getEmployeeExemptionRate().map(PrimitiveValueBase::v).orElse(null),
                domain.getSalaryEmployeesPensionInsuranceRate().getFractionClassification().getPersonalFraction().value,
                domain.getSalaryEmployeesPensionInsuranceRate().getFractionClassification().getBusinessOwnerFraction().value,
                domain.getSalaryEmployeesPensionInsuranceRate().getMaleContributionRate().getIndividualBurdenRatio().v(),
                domain.getSalaryEmployeesPensionInsuranceRate().getMaleContributionRate().getEmployeeContributionRatio().v(),
                domain.getSalaryEmployeesPensionInsuranceRate().getMaleContributionRate().getIndividualExemptionRate().map(PrimitiveValueBase::v).orElse(null),
                domain.getSalaryEmployeesPensionInsuranceRate().getMaleContributionRate().getEmployeeExemptionRate().map(PrimitiveValueBase::v).orElse(null));
    }
}
