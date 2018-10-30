package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 明細書レイアウト履歴: 主キー情報
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtStatementLayoutHistPk implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 会社ID
     */
    @Basic(optional = false)
    @Column(name = "CID")
    public String cid;

    /**
     * 明細書コード
     */
    @Basic(optional = false)
    @Column(name = "STATEMENT_CD")
    public String statementCd;
    /**
     * 履歴ID
     */
    @Basic(optional = false)
    @Column(name = "HIST_ID")
    public String histId;
}
