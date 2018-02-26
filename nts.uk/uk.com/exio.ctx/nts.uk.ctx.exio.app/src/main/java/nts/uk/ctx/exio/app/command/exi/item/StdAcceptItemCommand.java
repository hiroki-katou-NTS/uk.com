package nts.uk.ctx.exio.app.command.exi.item;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class StdAcceptItemCommand
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
    private String categoryItemNo;
    
    private Long version;

}
