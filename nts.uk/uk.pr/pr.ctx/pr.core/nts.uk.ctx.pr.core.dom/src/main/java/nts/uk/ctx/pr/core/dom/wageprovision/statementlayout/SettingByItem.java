package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
* 項目別設定
*/
@Getter
public class SettingByItem extends DomainObject
{
    
    /**
    * 項目位置
    */
    private int itemPosition;
    
    /**
    * 項目ID
    */
    private String itemId;
    
    public SettingByItem(int itemPosition, String itemId) {
        this.itemPosition = itemPosition;
        this.itemId = itemId;
    }
    
}
