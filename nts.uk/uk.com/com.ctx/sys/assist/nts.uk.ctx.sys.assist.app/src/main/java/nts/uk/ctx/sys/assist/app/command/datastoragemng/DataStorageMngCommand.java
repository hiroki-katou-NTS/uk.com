package nts.uk.ctx.sys.assist.app.command.datastoragemng;

import lombok.Value;

@Value
public class DataStorageMngCommand
{
    
    /**
    * データ保存処理ID
    */
    private String storeProcessingId;
    
    /**
    * 中断するしない
    */
    private int doNotInterrupt;
    
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
