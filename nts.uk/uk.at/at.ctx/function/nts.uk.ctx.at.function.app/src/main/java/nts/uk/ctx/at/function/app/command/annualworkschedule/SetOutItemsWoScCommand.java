package nts.uk.ctx.at.function.app.command.annualworkschedule;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class SetOutItemsWoScCommand
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * コード
    */
    private int cd;
    
    /**
    * 名称
    */
    private String name;
    
    /**
    * 36協定時間を超過した月数を出力する
    */
    private int outNumExceedTime36Agr;
    
    /**
    * 表示形式
    */
    private int displayFormat;
    
    private Long version;

}
