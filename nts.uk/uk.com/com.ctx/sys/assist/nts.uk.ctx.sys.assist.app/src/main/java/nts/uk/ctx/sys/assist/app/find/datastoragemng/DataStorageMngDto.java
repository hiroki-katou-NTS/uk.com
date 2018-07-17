package nts.uk.ctx.sys.assist.app.find.datastoragemng;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.sys.assist.dom.storage.DataStorageMng;

/**
* データ保存動作管理
*/
@AllArgsConstructor
@Value
public class DataStorageMngDto
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
    
    
    public static DataStorageMngDto fromDomain(DataStorageMng domain)
    {
        return new DataStorageMngDto(domain.getStoreProcessingId(), domain.getDoNotInterrupt().value, domain.getCategoryCount(), domain.getCategoryTotalCount(), domain.getErrorCount(), domain.getOperatingCondition().value);
    }
    
}
