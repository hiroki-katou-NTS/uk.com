package nts.uk.ctx.sys.assist.dom.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
* 対象カテゴリ
*/
@AllArgsConstructor
@Getter
public class TargetCategory
{
    
    /**
    * データ保存処理ID
    */
    private String storeProcessingId;
    
    /**
    * カテゴリID
    */
    private String categoryId;
    
    
}
