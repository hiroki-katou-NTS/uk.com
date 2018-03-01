package nts.uk.ctx.exio.app.find.exi.item;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exi.item.StdAcceptItem;

/**
* 受入項目（定型）
*/
@AllArgsConstructor
@Value
public class StdAcceptItemDto
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 条件設定コード
    */
    private String conditionSetCd;
    
    /**
    * カテゴリID
    */
    private String categoryId;
    
    /**
    * 受入項目番号
    */
    private int acceptItemNumber;
    
    /**
    * システム種類
    */
    private int systemType;
    
    /**
    * CSV項目番号
    */
    private int csvItemNumber;
    
    /**
    * CSV項目名
    */
    private String csvItemName;
    
    /**
    * 項目型
    */
    private int itemType;
    
    /**
    * カテゴリ項目NO
    */
    private int categoryItemNo;
    
    
    private Long version;
    public static StdAcceptItemDto fromDomain(StdAcceptItem domain)
    {
        return new StdAcceptItemDto(domain.getCid(), domain.getConditionSetCd(), domain.getCategoryId(), domain.getAcceptItemNumber(), domain.getSystemType(), domain.getCsvItemNumber(), domain.getCsvItemName(), domain.getItemType(), domain.getCategoryItemNo(), domain.getVersion());
    }
    
}
