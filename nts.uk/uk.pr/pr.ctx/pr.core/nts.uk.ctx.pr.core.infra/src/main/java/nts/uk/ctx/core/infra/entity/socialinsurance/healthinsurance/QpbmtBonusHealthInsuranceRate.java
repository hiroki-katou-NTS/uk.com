package nts.uk.ctx.core.infra.entity.socialinsurance.healthinsurance;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceFeeRateHistory;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 賞与健康保険料率
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_BONUS_HEALTH_INS")
public class QpbmtBonusHealthInsuranceRate extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtBonusHealthInsuranceRatePk bonusHealthInsurancePk;

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
     * 事業主負担分計算方法
     */
    @Basic(optional = false)
    @Column(name = "EMPLOYEE_SHARE_AMOUNT_METHOD")
    public int employeeShareAmountMethod;

    /**
     * 介護保険料率
     */
    @Basic(optional = false)
    @Column(name = "INDIVIDUAL_LONG_CARE_INSURANCE_RATE")
    public BigDecimal individualLongCareInsuranceRate;

    /**
     * 基本保険料率
     */
    @Basic(optional = false)
    @Column(name = "INDIVIDUAL_BASIC_INSURANCE_RATE")
    public BigDecimal individualBasicInsuranceRate;

    /**
     * 健康保険料率
     */
    @Basic(optional = false)
    @Column(name = "INDIVIDUAL_HEALTH_INSURANCE_RATE")
    public BigDecimal individualHealthInsuranceRate;

    /**
     * 端数区分
     */
    @Basic(optional = false)
    @Column(name = "INDIVIDUAL_FRACTION_CLS")
    public int individualFractionCls;

    /**
     * 特定保険料率
     */
    @Basic(optional = false)
    @Column(name = "INDIVIDUAL_SPECIAL_INSURANCE_RATE")
    public BigDecimal individualSpecialInsuranceRate;

    /**
     * 介護保険料率
     */
    @Basic(optional = false)
    @Column(name = "EMPLOYEE_LONG_CARE_INSURANCE_RATE")
    public BigDecimal employeeLongCareInsuranceRate;

    /**
     * 基本保険料率
     */
    @Basic(optional = false)
    @Column(name = "EMPLOYEE_BASIC_INSURANCE_RATE")
    public BigDecimal employeeBasicInsuranceRate;

    /**
     * 健康保険料率
     */
    @Basic(optional = false)
    @Column(name = "EMPLOYEE_HEALTH_INSURANCE_RATE")
    public BigDecimal employeeHealthInsuranceRate;

    /**
     * 端数区分
     */
    @Basic(optional = false)
    @Column(name = "EMPLOYEE_FRACTION_CLS")
    public int employeeFractionCls;

    /**
     * 特定保険料率
     */
    @Basic(optional = false)
    @Column(name = "EMPLOYEE_SPECIAL_INSURANCE_RATE")
    public BigDecimal employeeSpecialInsuranceRate;

    @Override
    protected Object getKey() {
        return bonusHealthInsurancePk;
    }

}
