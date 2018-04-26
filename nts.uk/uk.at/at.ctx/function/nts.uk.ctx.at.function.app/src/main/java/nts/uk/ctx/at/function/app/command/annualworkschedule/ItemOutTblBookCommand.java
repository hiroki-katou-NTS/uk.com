package nts.uk.ctx.at.function.app.command.annualworkschedule;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class ItemOutTblBookCommand
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * コード
    */
    private String cd;
    
    /**
    * コード
    */
    private String setOutCd;
    
    /**
    * 並び順
    */
    private int sortBy;
    
    /**
    * 見出し名称
    */
    private String headingName;
    
    /**
    * 使用区分
    */
    private int useClass;
    
    /**
    * 値の出力形式
    */
    private int valOutFormat;
    
    private Long version;

}
