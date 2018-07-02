package nts.uk.ctx.exio.app.command.exo.outitem;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class StdOutItemCommand
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
    
    private Long version;

}
