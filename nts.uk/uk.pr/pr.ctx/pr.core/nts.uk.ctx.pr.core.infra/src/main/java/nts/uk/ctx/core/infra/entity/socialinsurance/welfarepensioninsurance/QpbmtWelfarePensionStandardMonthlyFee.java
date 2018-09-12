package nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 厚生年金標準月額
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_WELFARE_STD_MON_FEE")
public class QpbmtWelfarePensionStandardMonthlyFee extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtWelfarePensionStandardMonthlyFeePk welfareStdMonFeePk;

    @Override
    protected Object getKey() {
        return welfareStdMonFeePk;
    }
}
