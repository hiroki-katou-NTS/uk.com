package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.FixedItemClassificationList;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
* 固定項目区分一覧
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_FIXED_ITEM_CLS_LIST")
public class QpbmtFixedItemClsList extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtFixedItemClsListPk fixedItemClsListPk;
    
    /**
    * 明細書出力区分
    */
    @Basic(optional = false)
    @Column(name = "SPEC_OUT_PUT_CLS")
    public int specOutPutCls;
    
    /**
    * 合計対象項目区分
    */
    @Basic(optional = false)
    @Column(name = "TOTAL_TARGET_ITEM_CLS")
    public int totalTargetItemCls;
    
    /**
    * 修正禁止区分
    */
    @Basic(optional = false)
    @Column(name = "MODIFICATION_PROHIBITION_CLS")
    public int modificationProhibitionCls;
    
    /**
    * 会計連動区分
    */
    @Basic(optional = false)
    @Column(name = "ACCOUNTING_LINKAGE_CLS")
    public int accountingLinkageCls;
    
    @Override
    protected Object getKey()
    {
        return fixedItemClsListPk;
    }

    public FixedItemClassificationList toDomain() {
        return new FixedItemClassificationList(this.fixedItemClsListPk.itemNameCd, this.specOutPutCls, this.totalTargetItemCls, this.modificationProhibitionCls, this.accountingLinkageCls);
    }
    public static QpbmtFixedItemClsList toEntity(FixedItemClassificationList domain) {
        return new QpbmtFixedItemClsList(new QpbmtFixedItemClsListPk(domain.getItemNameCd().v()),domain.getSpecOutPutCls().value, domain.getTotalTargetItemCls().value, domain.getModificationProhibitionCls().value, domain.getAccountingLinkageCls().value);
    }

}
