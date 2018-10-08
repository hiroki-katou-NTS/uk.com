package nts.uk.ctx.pr.core.infra.entity.wageprovision.companyuniformamount;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 給与会社単価: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtPayUnitPricePk implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 会社ID
     */
    @Basic(optional = false)
    @Column(name = "CID")
    public String cid;

    /**
    * コード
    */
    @Basic(optional = false)
    @Column(name = "CODE")
    public String code;

}
