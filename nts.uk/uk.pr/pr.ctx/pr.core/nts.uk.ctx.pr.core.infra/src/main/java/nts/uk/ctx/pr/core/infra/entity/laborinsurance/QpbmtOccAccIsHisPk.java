package nts.uk.ctx.pr.core.infra.entity.laborinsurance;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 労災保険履歴: 主キー情報
 */
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtOccAccIsHisPk implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *会社ID
     */
    @Basic(optional = false)
    @Column(name = "CID")
    public String cid;

    /**
     *履歴ID
     */
    @Basic(optional = false)
    @Column(name = "HIS_ID")
    public String hisId;

}
