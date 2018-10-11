package nts.uk.ctx.core.infra.entity.socialinsurance.healthinsurance;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.HealthInsuranceStandardMonthly;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 健康保険標準月額
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_HEALTH_INS_STD_MON")
public class QpbmtHealthInsuranceStandardMonthly extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtHealthInsuranceStandardMonthlyPk healthInsStdMonPk;

    @Override
    protected Object getKey() {
        return healthInsStdMonPk;
    }

    public static QpbmtHealthInsuranceStandardMonthly toEntity(HealthInsuranceStandardMonthly domain) {
        return new QpbmtHealthInsuranceStandardMonthly(new QpbmtHealthInsuranceStandardMonthlyPk(domain.getTargetPeriod().start().v(), domain.getTargetPeriod().end().v()));
    }
}
