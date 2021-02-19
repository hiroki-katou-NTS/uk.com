package nts.uk.ctx.sys.assist.dom.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
* 対象カテゴリ
*/
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    
    /**
     * システム種類
     */
    private SystemType systemType;
}
