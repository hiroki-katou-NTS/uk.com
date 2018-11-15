package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

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
* 要素項目（マスタ）
*/
@AllArgsConstructor
@Getter
public class MasterElementItem extends DomainObject
{
    
    /**
    * マスタコード
    */
    private MasterCode masterCode;
    
    public MasterElementItem(String masterCode) {
        this.masterCode = new MasterCode(masterCode);
    }
    
}
