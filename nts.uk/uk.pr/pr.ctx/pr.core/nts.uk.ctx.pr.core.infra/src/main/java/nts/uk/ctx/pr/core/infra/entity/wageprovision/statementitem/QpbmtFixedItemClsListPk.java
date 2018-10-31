package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 固定項目区分一覧: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtFixedItemClsListPk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * 項目名コード
    */
    @Basic(optional = false)
    @Column(name = "ITEM_NAME_CD")
    public String itemNameCd;
    
}
