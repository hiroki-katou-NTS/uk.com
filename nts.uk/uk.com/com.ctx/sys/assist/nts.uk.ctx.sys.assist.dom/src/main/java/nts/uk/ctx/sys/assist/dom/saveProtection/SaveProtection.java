package nts.uk.ctx.sys.assist.dom.saveProtection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 保存保護
*/
@AllArgsConstructor
@Getter
public class SaveProtection extends AggregateRoot
{
    
    /**
    * カテゴリID
    */
    private int categoryId;
    
    /**
    * 補正区分
    */
    private int correctClasscification;
    
    /**
    * 置き換え列
    */
    private String replaceColumn;
    
    /**
    * テーブルNo
    */
    private int tableNo;
    
    
}
