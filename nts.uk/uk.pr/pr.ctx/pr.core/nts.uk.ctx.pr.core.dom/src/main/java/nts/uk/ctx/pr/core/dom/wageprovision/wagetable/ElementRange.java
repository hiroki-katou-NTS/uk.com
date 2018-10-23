package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

import java.util.Optional;

/**
 * 要素範囲
 */
@AllArgsConstructor
@Getter
public class ElementRange extends DomainObject{

    /**
     * 数値要素範囲
     */
    Optional<NumericElementRange> numericElementRange;

    public ElementRange (Integer stepIncrement, Integer rangeLowerLimit, Integer rangeUpperLimit) {
        if (stepIncrement == null && rangeLowerLimit == null && rangeUpperLimit == null) this.numericElementRange = Optional.empty();
        else this.numericElementRange = Optional.of(new NumericElementRange(stepIncrement, rangeLowerLimit, rangeUpperLimit));
    }
}
