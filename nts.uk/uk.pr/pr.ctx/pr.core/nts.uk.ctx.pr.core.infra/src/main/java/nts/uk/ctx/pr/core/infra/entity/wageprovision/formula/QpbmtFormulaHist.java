package nts.uk.ctx.pr.core.infra.entity.wageprovision.formula;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 計算式履歴
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_FORMULA_HIST")
public class QpbmtFormulaHist extends UkJpaEntity implements Serializable {
    /**
     * ID
     */
    @EmbeddedId
    public QpbmtFormulaHistPk formulaHistPk;
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
        return formulaHistPk;
    }
}
