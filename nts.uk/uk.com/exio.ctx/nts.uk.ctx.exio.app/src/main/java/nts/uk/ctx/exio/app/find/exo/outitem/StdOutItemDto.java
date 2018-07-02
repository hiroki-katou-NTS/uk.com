package nts.uk.ctx.exio.app.find.exo.outitem;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exo.outitem.StdOutItem;

/**
* 出力項目(定型)
*/
@AllArgsConstructor
@Value
public class StdOutItemDto
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 出力項目コード
    */
    private String outItemCd;
    
    /**
    * 条件設定コード
    */
    private String condSetCd;
    
    /**
    * 出力項目名
    */
    private String outItemName;
    
    /**
    * 項目型
    */
    private int itemType;
    
    
    public static StdOutItemDto fromDomain(StdOutItem domain)
    {
        return new StdOutItemDto(domain.getCid(), domain.getOutItemCd(), domain.getCondSetCd(), domain.getOutItemName(), domain.getItemType());
    }
    
}
