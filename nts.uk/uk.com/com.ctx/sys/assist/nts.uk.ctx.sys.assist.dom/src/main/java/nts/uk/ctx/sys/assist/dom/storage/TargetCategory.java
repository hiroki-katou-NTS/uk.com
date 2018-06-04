package nts.uk.ctx.sys.assist.dom.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
* 対象カテゴリ
*/
@NoArgsConstructor
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
