package nts.uk.ctx.exio.dom.exi.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
* 受入項目（定型）
*/
@AllArgsConstructor
@Getter
@Setter
public class StdAcceptItem extends AggregateRoot
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
    
    public static StdAcceptItem createFromJavaType(Long version, String cid, String conditionSetCd, String categoryId, int acceptItemNumber, int systemType, int csvItemNumber, String csvItemName, int itemType, int categoryItemNo)
    {
        StdAcceptItem  stdAcceptItem =  new StdAcceptItem(cid, conditionSetCd, categoryId, acceptItemNumber, systemType, csvItemNumber, csvItemName, itemType,  categoryItemNo);
        stdAcceptItem.setVersion(version);
        return stdAcceptItem;
    }
    
}
