package nts.uk.ctx.pr.core.dom.wageprovision.statementitem;

import java.util.Optional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;

/**
* 固定項目区分一覧
*/
@Getter
public class FixedItemClassificationList extends AggregateRoot {
    
    /**
    * 項目名コード
    */
    private ItemNameCode itemNameCd;
    
    /**
    * 明細書出力区分
    */
    private SpecificationOutputCls specOutPutCls;
    
    /**
    * 合計対象項目区分
    */
    private TotalTargetItemCls totalTargetItemCls;
    
    /**
    * 修正禁止区分
    */
    private ModificationProhibitionCls modificationProhibitionCls;
    
    /**
    * 会計連動区分
    */
    private AccountingLinkageCls accountingLinkageCls;
    
    public FixedItemClassificationList(String itemNameCd, Integer specOutPutCls, Integer totalTargetItemCls, Integer modificationProhibitionCls, Integer accountingLinkageCls) {
        this.itemNameCd = new ItemNameCode(itemNameCd);
        this.specOutPutCls = EnumAdaptor.valueOf(specOutPutCls, SpecificationOutputCls.class);
        this.totalTargetItemCls = EnumAdaptor.valueOf(totalTargetItemCls, TotalTargetItemCls.class);
        this.modificationProhibitionCls = EnumAdaptor.valueOf(modificationProhibitionCls, ModificationProhibitionCls.class);
        this.accountingLinkageCls = EnumAdaptor.valueOf(accountingLinkageCls, AccountingLinkageCls.class);
    }
    
}
