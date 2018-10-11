package nts.uk.ctx.core.infra.entity.socialinsurance.healthinsurance;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceStandardMonthly;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 等級毎標準月額
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_HEALTH_STD_GRA_MON")
public class QpbmtHealthInsuranceStandardGradePerMonth extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtHealthInsuranceStandardGradePerMonthPk healthStdGraMonPk;

    /**
     * 標準月額
     */
    @Basic(optional = false)
    @Column(name = "STANDARD_MONTHLY_FEE")
    public long standardMonthlyFee;

    @Override
    protected Object getKey() {
        return healthStdGraMonPk;
    }

    public static List<QpbmtHealthInsuranceStandardGradePerMonth> toEntity(HealthInsuranceStandardMonthly domain) {
        return domain.getStandardGradePerMonth().stream().map(x -> new QpbmtHealthInsuranceStandardGradePerMonth(
                new QpbmtHealthInsuranceStandardGradePerMonthPk(domain.getTargetPeriod().start().v(), domain.getTargetPeriod().end().v(), x.getHealthInsuranceGrade()),
                x.getStandardMonthlyFee())).collect(Collectors.toList());
    }
}