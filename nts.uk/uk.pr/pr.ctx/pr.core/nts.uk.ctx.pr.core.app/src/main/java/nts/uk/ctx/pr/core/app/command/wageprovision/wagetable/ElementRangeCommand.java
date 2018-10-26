package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementRange;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.NumericElementRange;

/**
 * 要素範囲
 */

@NoArgsConstructor
@Data
public class ElementRangeCommand {
    // Merge domain 要素範囲, 数値要素範囲
    /**
     * 数値要素範囲.きざみ単位
     */
    private Integer stepIncrement;

    /**
     * 数値要素範囲.範囲下限
     */
    private Integer rangeLowerLimit;

    /**
     * 数値要素範囲.範囲上限
     */
    private Integer rangeUpperLimit;

    public ElementRange fromCommandToDomain() {
        return new ElementRange(stepIncrement, rangeLowerLimit, rangeUpperLimit);
    }

    public static ElementRangeCommand fromNumericElementToDto(NumericElementRange domain) {
        ElementRangeCommand dto = new ElementRangeCommand();
        dto.stepIncrement = domain.getStepIncrement().v();
        dto.rangeLowerLimit = domain.getRangeLowerLimit().v();
        dto.rangeUpperLimit = domain.getRangeUpperLimit().v();
        return dto;
    }
}
