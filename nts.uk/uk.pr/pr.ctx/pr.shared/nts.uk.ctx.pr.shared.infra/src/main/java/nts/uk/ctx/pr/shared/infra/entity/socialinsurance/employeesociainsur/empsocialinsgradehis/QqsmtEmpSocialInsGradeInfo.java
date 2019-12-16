package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empsocialinsgradehis;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeInfo;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 社員社会保険等級情報
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSMT_EMP_SOC_INS_GRA_INF")
public class QqsmtEmpSocialInsGradeInfo extends UkJpaEntity implements Serializable {
    /**
     * 履歴ID
     */
    @EmbeddedId
    public QqsmtEmpSocialInsGradeInfoPk empSocialInsGradeInfoPk;

    /**
     * 社会保険報酬月額(実質)
     */
    @Basic(optional = false)
    @Column(name = "SOC_INS_PAY_MONTHLY")
    public Integer socialInsPayMonthly;

    /**
     * 算定区分
     */
    @Basic(optional = false)
    @Column(name = "CALCULATION_ATR")
    public Integer calculationAtr;

    /**
     * 健康保険標準報酬月額
     */
    @Basic(optional = true)
    @Column(name = "HEALTH_INS_MONTHLY_PAY")
    public Integer healInsMonthlyPay;

    /**
     * 健康保険等級
     */
    @Basic(optional = true)
    @Column(name = "HEALTH_INS_GRADE")
    public Integer healInsGrade;

    /**
     * 厚生年金保険標準報酬月額
     */
    @Basic(optional = true)
    @Column(name = "PENSION_INS_MONTHLY_PAY")
    public Integer pensionInsMonthlyPay;

    /**
     * 厚生年金保険等級
     */
    @Basic(optional = true)
    @Column(name = "PENSION_INS_GRADE")
    public Integer pensionInsGrade;

    @Override
    protected Object getKey() {
        return null;
    }

    public EmpSocialInsGradeInfo toDomain() {
        return new EmpSocialInsGradeInfo(
                this.empSocialInsGradeInfoPk.historyId,
                this.socialInsPayMonthly,
                this.calculationAtr,
                this.healInsMonthlyPay,
                this.healInsGrade,
                this.pensionInsMonthlyPay,
                this.pensionInsGrade
        );
    }

    public static QqsmtEmpSocialInsGradeInfo toEntity(EmpSocialInsGradeInfo domain) {
        return new QqsmtEmpSocialInsGradeInfo(new QqsmtEmpSocialInsGradeInfoPk(domain.getHisId()),
                domain.getSocInsMonthlyRemune().v(),
                domain.getCalculationAtr().value,
                domain.getHealInsStandMonthlyRemune().map(PrimitiveValueBase::v).orElse(null),
                domain.getHealInsGrade().map(PrimitiveValueBase::v).orElse(null),
                domain.getPensionInsStandCompenMonthly().map(PrimitiveValueBase::v).orElse(null),
                domain.getPensionInsGrade().map(PrimitiveValueBase::v).orElse(null)
        );
    }

}
