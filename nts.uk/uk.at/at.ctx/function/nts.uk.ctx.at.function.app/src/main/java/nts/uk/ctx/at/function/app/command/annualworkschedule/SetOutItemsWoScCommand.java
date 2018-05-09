package nts.uk.ctx.at.function.app.command.annualworkschedule;

import java.util.List;

import lombok.Value;

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
    private String cd;
    
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
    
    private List<ItemOutTblBookCommand> listItemOutput;

}
