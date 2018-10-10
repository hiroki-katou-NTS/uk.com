package nts.uk.ctx.pr.core.infra.entity.emprsdttaxinfo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 社員住民税納付先情報: 主キー情報
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtEmpRsdtTaxPayeePk implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 社員ID
     */
    @Basic(optional = false)
    @Column(name = "SID")
    public String sid;

    /**
     * 履歴ID
     */
    @Basic(optional = false)
    @Column(name = "HIST_ID")
    public String histId;

}
