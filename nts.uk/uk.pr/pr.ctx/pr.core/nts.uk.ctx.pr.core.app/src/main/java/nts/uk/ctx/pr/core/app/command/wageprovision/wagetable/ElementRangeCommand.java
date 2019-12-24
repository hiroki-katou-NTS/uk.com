package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementRange;

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
    private BigDecimal stepIncrement;

    /**
     * 数値要素範囲.範囲下限
     */
    private BigDecimal rangeLowerLimit;

    /**
     * 数値要素範囲.範囲上限
     */
    private BigDecimal rangeUpperLimit;

	public ElementRange fromCommandToDomain() {
		return new ElementRange(stepIncrement, rangeLowerLimit, rangeUpperLimit);
	}

}
