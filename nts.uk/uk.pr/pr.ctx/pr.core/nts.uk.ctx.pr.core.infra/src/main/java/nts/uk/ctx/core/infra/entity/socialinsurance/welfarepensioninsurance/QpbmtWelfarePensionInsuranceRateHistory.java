package nts.uk.ctx.core.infra.entity.socialinsurance.welfarepensioninsurance;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 厚生年金保険料率履歴
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_WELFARE_PEN_HIST")
public class QpbmtWelfarePensionInsuranceRateHistory extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @EmbeddedId
    public QpbmtWelfarePensionInsuranceRateHistoryPk welfarePenHistPk;

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

    @Override
    protected Object getKey() {
        return welfarePenHistPk;
    }

}
