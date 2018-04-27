package nts.uk.ctx.sys.assist.dom.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
* データ保存動作管理
*/
@AllArgsConstructor
@Getter
public class DataStorageMng extends AggregateRoot
{
    
    /**
    * データ保存処理ID
    */
    private String storeProcessingId;
    
    /**
    * 中断するしない
    */
    private NotUseAtr doNotInterrupt;
    
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
    private OperatingCondition operatingCondition;
    
    
}
