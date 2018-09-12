package nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 厚生年金報酬月額範囲
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_MON_RANGE_WEL_PEN")
public class QpbmtMonthlyScopeOfWelfarePensionCompensation extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtMonthlyScopeOfWelfarePensionCompensationPk monRangeWelPenPk;

    @Override
    protected Object getKey() {
        return monRangeWelPenPk;
    }
}
