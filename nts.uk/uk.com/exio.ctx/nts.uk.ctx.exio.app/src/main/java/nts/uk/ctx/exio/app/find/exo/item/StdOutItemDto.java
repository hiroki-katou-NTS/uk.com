package nts.uk.ctx.exio.app.find.exo.item;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItem;

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
    
    
    public static StdOutItemDto fromDomain(StandardOutputItem domain)
    {
        return new StdOutItemDto(domain.getCid(), domain.getOutputItemCode().v(), domain.getConditionSettingCode().v(), domain.getOutputItemName().v(), domain.getItemType().value);
    }
    
}
