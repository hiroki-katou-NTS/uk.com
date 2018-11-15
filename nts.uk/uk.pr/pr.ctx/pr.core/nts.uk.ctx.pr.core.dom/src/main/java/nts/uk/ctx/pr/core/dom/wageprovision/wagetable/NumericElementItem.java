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
* 要素項目（数値）
*/
@AllArgsConstructor
@Getter
public class NumericElementItem extends DomainObject
{
    
    /**
    * 枠番
    */
    private FrameNumber frameNumber;
    
    /**
    * 当該枠下限
    */
    private FrameLowerLimit frameLowerLimit;
    
    /**
    * 当該枠上限
    */
    private FrameUpperLimit frameUpperLimit;
    
    public NumericElementItem(Integer frameNumber, Integer frameLowerLimit, Integer frameUpperLimit) {
        this.frameNumber = new FrameNumber(frameNumber);
        this.frameLowerLimit = new FrameLowerLimit(frameLowerLimit);
        this.frameUpperLimit = new FrameUpperLimit(frameUpperLimit);
    }
    
}
