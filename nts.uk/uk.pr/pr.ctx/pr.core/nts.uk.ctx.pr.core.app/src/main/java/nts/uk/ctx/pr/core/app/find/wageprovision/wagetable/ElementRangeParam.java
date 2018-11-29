package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 要素範囲
 */

@NoArgsConstructor
@Data
public class ElementRangeParam {
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

}
