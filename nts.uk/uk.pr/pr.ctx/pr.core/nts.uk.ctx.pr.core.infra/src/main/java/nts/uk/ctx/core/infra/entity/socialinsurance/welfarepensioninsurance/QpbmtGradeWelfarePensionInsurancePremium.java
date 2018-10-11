package nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.EmployeesPensionMonthlyInsuranceFee;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 等級毎厚生年金保険料
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_GRADE_WELFARE_PREMI")
public class QpbmtGradeWelfarePensionInsurancePremium extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtGradeWelfarePensionInsurancePremiumPk gradeWelfarePremiPk;

    /**
     * 女子保険料
     */
    @Basic(optional = false)
    @Column(name = "EMP_FEMALE_INSU_PREMIUM")
    public BigDecimal empFemaleInsurancePremium;

    /**
     * 男子保険料
     */
    @Basic(optional = false)
    @Column(name = "EMP_MALE_INSU_PREMIUM")
    public BigDecimal empMaleInsurancePremium;

    /**
     * 女子免除保険料
     */
    @Basic
    @Column(name = "EMP_FEMALE_EXEMPT_INSU_PREMIUM")
    public BigDecimal empFemaleExemptionInsurancePremium;

    /**
     * 男子免除保険料
     */
    @Basic
    @Column(name = "EMP_MALE_EXEMPT_INSU_PREMIUM")
    public BigDecimal empMaleExemptionInsurancePremium;

    /**
     * 女子保険料
     */
    @Basic(optional = false)
    @Column(name = "INS_FEMALE_INSU_PREMIUM")
    public BigDecimal insFemaleInsurancePremium;

    /**
     * 男子保険料
     */
    @Basic(optional = false)
    @Column(name = "INS_MALE_INSU_PREMIUM")
    public BigDecimal insMaleInsurancePremium;

    /**
     * 女子免除保険料
     */
    @Basic
    @Column(name = "INS_FEMALE_EXEMP_INSU_PREMIUM")
    public BigDecimal insFemaleExemptionInsurancePremium;

    /**
     * 男子免除保険料
     */
    @Basic
    @Column(name = "INS_MALE_EXEMP_INSU_PREMIUM")
    public BigDecimal insMaleExemptionInsurancePremium;

    @Override
    protected Object getKey() {
        return gradeWelfarePremiPk;
    }

    public static List<QpbmtGradeWelfarePensionInsurancePremium> toEntity(EmployeesPensionMonthlyInsuranceFee domain) {
        return domain.getPensionInsurancePremium().stream().map(x -> new QpbmtGradeWelfarePensionInsurancePremium(
                new QpbmtGradeWelfarePensionInsurancePremiumPk(domain.getHistoryId(), x.getWelfarePensionGrade()),
                x.getEmployeeBurden().getFemaleInsurancePremium().v(),
                x.getEmployeeBurden().getMaleInsurancePremium().v(),
                x.getEmployeeBurden().getFemaleExemptionInsurance().map(PrimitiveValueBase::v).orElse(null),
                x.getEmployeeBurden().getMaleExemptionInsurance().map(PrimitiveValueBase::v).orElse(null),
                x.getInsuredBurden().getFemaleInsurancePremium().v(),
                x.getInsuredBurden().getMaleInsurancePremium().v(),
                x.getInsuredBurden().getFemaleExemptionInsurance().map(PrimitiveValueBase::v).orElse(null),
                x.getInsuredBurden().getMaleExemptionInsurance().map(PrimitiveValueBase::v).orElse(null)
        )).collect(Collectors.toList());
    }
}