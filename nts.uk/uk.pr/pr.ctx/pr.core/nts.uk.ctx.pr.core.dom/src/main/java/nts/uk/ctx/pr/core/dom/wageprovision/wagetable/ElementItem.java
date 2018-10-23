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
* 要素項目
*/
@AllArgsConstructor
@Getter
public class ElementItem extends DomainObject
{
    
    /**
    * 要素項目(マスタ)
    */
    private Optional<MasterElementItem> masterElementItem;
    
    /**
    * 要素項目(数値)
    */
    private Optional<NumericElementItem> numericElementItem;
    
    public ElementItem(String masterCode, Integer frameNumber, Integer frameLowerLimit, Integer frameUpperLimit) {
        this.masterElementItem = masterCode == null ? Optional.empty() : Optional.of(new MasterElementItem(masterCode));
        if (frameNumber == null && frameLowerLimit == null && frameUpperLimit == null) this.numericElementItem = Optional.empty();
        else this.numericElementItem = Optional.of(new NumericElementItem(frameNumber, frameLowerLimit, frameUpperLimit));
    }
    
}
