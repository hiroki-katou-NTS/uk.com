package nts.uk.ctx.core.infra.entity.socialinsurance.healthinsurance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceMonthlyFee;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 等級毎健康保険料
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_HEALTH_MON_PER_GRA")
public class QpbmtHealthInsurancePerGradeFee extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtHealthInsurancePerGradeFeePk healthMonPerGraPk;

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
     * 介護保険料
     */
    @Basic(optional = false)
    @Column(name = "EMPLOYEE_NURSING_CARE_INSURANCE_FEE")
    public BigDecimal employeeNursingCareInsuranceFee;

    /**
     * 基本保険料
     */
    @Basic(optional = false)
    @Column(name = "EMPLOYEE_BASIC_INSURANCE_FEE")
    public BigDecimal employeeBasicInsuranceFee;

    /**
     * 健康保険料
     */
    @Basic(optional = false)
    @Column(name = "EMPLOYEE_HEALTH_INSURANCE_FEE")
    public BigDecimal employeeHealthInsuranceFee;

    /**
     * 特定保険料
     */
    @Basic(optional = false)
    @Column(name = "EMPLOYEE_SPECIFIC_INSURANCE_FEE")
    public BigDecimal employeeSpecificInsuranceFee;

    /**
     * 介護保険料
     */
    @Basic(optional = false)
    @Column(name = "INSURED_NURSING_CARE_INSURANCE_FEE")
    public BigDecimal insuredNursingCareInsuranceFee;

    /**
     * 基本保険料
     */
    @Basic(optional = false)
    @Column(name = "INSURED_BASIC_INSURANCE_FEE")
    public BigDecimal insuredBasicInsuranceFee;

    /**
     * 健康保険料
     */
    @Basic(optional = false)
    @Column(name = "INSURED_HEALTH_INSURANCE_FEE")
    public BigDecimal insuredHealthInsuranceFee;

    /**
     * 特定保険料
     */
    @Basic(optional = false)
    @Column(name = "INSURED_SPECIFIC_INSURANCE_FEE")
    public BigDecimal insuredSpecificInsuranceFee;

    @Override
    protected Object getKey() {
        return healthMonPerGraPk;
    }

    public static List<QpbmtHealthInsurancePerGradeFee> toEntity(HealthInsuranceMonthlyFee domain, String officeCode, YearMonthHistoryItem yearMonth) {
        return domain.getHealthInsurancePerGradeFee().stream().map(x -> new QpbmtHealthInsurancePerGradeFee(new QpbmtHealthInsurancePerGradeFeePk(AppContexts.user().companyId(), officeCode, domain.getHistoryId(), x.getHealthInsuranceGrade()),
                yearMonth.start().v(), yearMonth.end().v(),
                x.getEmployeeBurden().getNursingCare().v(),
                x.getEmployeeBurden().getBasicInsurancePremium().v(),
                x.getEmployeeBurden().getHealthInsurancePremium().v(),
                x.getEmployeeBurden().getSpecInsurancePremium().v(),
                x.getInsuredBurden().getNursingCare().v(),
                x.getInsuredBurden().getBasicInsurancePremium().v(),
                x.getInsuredBurden().getHealthInsurancePremium().v(),
                x.getInsuredBurden().getSpecInsurancePremium().v())).collect(Collectors.toList());
    }
}
