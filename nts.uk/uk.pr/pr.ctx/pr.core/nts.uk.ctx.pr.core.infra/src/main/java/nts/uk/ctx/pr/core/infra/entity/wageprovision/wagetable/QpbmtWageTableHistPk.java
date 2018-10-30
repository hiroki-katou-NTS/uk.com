package nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 賃金テーブル履歴: 主キー情報
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtWageTableHistPk implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 会社ID
     */
    @Basic(optional = false)
    @Column(name = "CID")
    public String cid;

    /**
     * 賃金テーブルコード
     */
    @Basic(optional = false)
    @Column(name = "WAGE_TABLE_CODE")
    private String wageTblCode;
    /**
     * 履歴ID
     */
    @Basic(optional = false)
    @Column(name = "HIST_ID")
    public String histId;
}
