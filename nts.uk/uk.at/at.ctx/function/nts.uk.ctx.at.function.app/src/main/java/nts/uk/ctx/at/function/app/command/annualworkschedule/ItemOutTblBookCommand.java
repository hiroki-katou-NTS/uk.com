package nts.uk.ctx.at.function.app.command.annualworkschedule;

import lombok.Value;

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
    private String setOutCd;
    
    /**
    * コード
    */
    private String cd;
    
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
