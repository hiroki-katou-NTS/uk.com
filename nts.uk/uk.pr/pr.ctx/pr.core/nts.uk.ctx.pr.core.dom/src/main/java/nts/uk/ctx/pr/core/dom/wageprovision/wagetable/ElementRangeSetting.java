package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.util.Optional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;

import javax.xml.bind.Element;

/**
* 要素範囲設定
*/
@Getter
@AllArgsConstructor
public class ElementRangeSetting extends AggregateRoot {
    
    /**
    * 第一要素範囲
    */
    private ElementRange firstElementRange;
    
    /**
    * 第二要素範囲
    */
    private Optional<ElementRange> secondElementRange;
    
    /**
    * 第三要素範囲
    */
    private Optional<ElementRange> thirdElementRange;
    
    /**
    * 履歴ID
    */
    private String historyID;
    
    public ElementRangeSetting(Integer firstStepIncrement, Integer firstRangeLowerLimit, Integer firstRangeUpperLimit,
                               Integer secondStepIncrement, Integer secondRangeLowerLimit, Integer secondRangeUpperLimit,
                               Integer thirdStepIncrement, Integer thirdRangeLowerLimit, Integer thirdRangeUpperLimit, String historyID) {
        this.firstElementRange = new ElementRange(firstStepIncrement, firstRangeLowerLimit, firstRangeUpperLimit);
        this.thirdElementRange = Optional.of(new ElementRange(secondStepIncrement, secondRangeLowerLimit, secondRangeUpperLimit));
        this.secondElementRange = Optional.of(new ElementRange(thirdStepIncrement, thirdRangeLowerLimit, thirdRangeUpperLimit));
        this.historyID = historyID;
    }

    public ElementRangeSetting(ElementRange secondElementRange, ElementRange thirdElementRange, ElementRange firstElementRange, String historyID) {
        this.firstElementRange = secondElementRange;
        this.thirdElementRange = Optional.of(thirdElementRange);
        this.secondElementRange = Optional.of(secondElementRange);
        this.historyID = historyID;
    }

}
