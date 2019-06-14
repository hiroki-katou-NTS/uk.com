package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
     * 開始年月
     */
    @Basic(optional = false)
    @Column(name = "START_YM")
    public int startYearMonth;

    /**
     * 終了年月
     */
    @Basic(optional = false)
    @Column(name = "END_YM")
    public int endYearMonth;

    /**
     * 明細書レイアウトパターン
     */
    @Basic(optional = false)
    @Column(name = "LAYOUT_PATTERN")
    public int layoutPattern;

    @Override
    protected Object getKey() {
        return statementLayoutHistPk;
    }
}
