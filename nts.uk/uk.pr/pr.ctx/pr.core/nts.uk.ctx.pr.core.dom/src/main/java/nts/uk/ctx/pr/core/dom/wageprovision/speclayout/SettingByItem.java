package nts.uk.ctx.pr.core.dom.wageprovision.speclayout;

import java.util.Optional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;

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
