package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.speclayout.SpecificationLayoutHist;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 明細書レイアウト履歴
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_STATE_LAYOUT_HIST")
public class QpbmtStatementLayoutHist extends UkJpaEntity implements Serializable {
    /**
     * ID
     */
    @EmbeddedId
    public QpbmtStatementLayoutHistPk statementLayoutHistPk;
    /**
     * 開始日
     */
    @Basic(optional = false)
    @Column(name = "START_YEAR_MONTH")
    public int startYearMonth;

    /**
     * 終了日
     */
    @Basic(optional = false)
    @Column(name = "END_YEAR_MONTH")
    public int endYearMonth;

    @Override
    protected Object getKey() {
        return statementLayoutHistPk;
    }

}
