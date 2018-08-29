package nts.uk.ctx.exio.infra.entity.monsalabonus.laborinsur;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
     *
     */
    @Basic(optional = false)
    @Column(name = "CID")
    public String cid;

    /**
     *
     */
    @Basic(optional = false)
    @Column(name = "HIS_ID")
    public String hisId;

}
