package nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 賃金テーブル: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtWageTablePk implements Serializable
{
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
    public String wageTableCode;
    
}
