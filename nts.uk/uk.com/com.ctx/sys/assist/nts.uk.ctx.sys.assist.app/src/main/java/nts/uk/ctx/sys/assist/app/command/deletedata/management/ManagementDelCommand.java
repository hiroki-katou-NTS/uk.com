package nts.uk.ctx.sys.assist.app.command.deletedata.management;

import lombok.Value;

@Value
public class ManagementDelCommand
{
    
    /**
    * データ保存処理ID
    */
    private String delId;
    
    /**
    * 中断するしない
    */
    private int isInterruptedFlg;
    
    /**
    * カテゴリカウント
    */
    private int categoryCount;
    
    /**
    * カテゴリトータルカウント
    */
    private int categoryTotalCount;
    
    /**
    * エラー件数
    */
    private int errorCount;
    
    /**
    * 動作状態
    */
    private int operatingCondition;

}
