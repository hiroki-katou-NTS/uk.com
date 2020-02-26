package nts.uk.ctx.pr.core.infra.entity.wageprovision.formula;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtDetailCalculationFormulaPk {
    private static final long serialVersionUID = 1L;

    /**
     * 会社ID
     */
    @Basic(optional = false)
    @Column(name = "CID")
    public String cid;

    /**
     * 計算式コード
     */
    @Basic(optional = false)
    @Column(name = "FORMULA_CD")
    public String formulaCode;

    /**
     * 履歴ID
     */
    @Basic(optional = false)
    @Column(name = "HIST_ID")
    public String historyID;

    /**
     * 構成順
     */
    @Basic(optional = false)
    @Column(name = "ELEMENT_ORDER")
    public int elementOrder;

}
