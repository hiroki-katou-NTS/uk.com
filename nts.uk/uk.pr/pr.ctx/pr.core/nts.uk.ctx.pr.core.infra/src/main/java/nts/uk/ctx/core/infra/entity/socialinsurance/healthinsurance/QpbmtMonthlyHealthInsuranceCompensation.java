package nts.uk.ctx.core.infra.entity.socialinsurance.healthinsurance;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.core.dom.socialinsurance.healthinsurance.MonthlyHealthInsuranceCompensation;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 健康保険報酬月額範囲
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_MON_HEALTH_INS_COM")
public class QpbmtMonthlyHealthInsuranceCompensation extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtMonthlyHealthInsuranceCompensationPk monHealthInsComPk;

    @Override
    protected Object getKey() {
        return monHealthInsComPk;
    }

    public static QpbmtMonthlyHealthInsuranceCompensation toEntity(MonthlyHealthInsuranceCompensation domain) {
        return new QpbmtMonthlyHealthInsuranceCompensation(new QpbmtMonthlyHealthInsuranceCompensationPk(domain.getTargetPeriod().start().v(), domain.getTargetPeriod().end().v()));
    }
}
