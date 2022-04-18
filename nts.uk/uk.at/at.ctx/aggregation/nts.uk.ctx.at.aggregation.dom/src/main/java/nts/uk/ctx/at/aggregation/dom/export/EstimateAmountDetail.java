package nts.uk.ctx.at.aggregation.dom.export;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         目安金額詳細
 */
@AllArgsConstructor
@Getter
public class EstimateAmountDetail {

	/**
	 * 目安金額枠NO
	 */
	private int amountFrameNo;

	/**
	 * 目安金額
	 */
	private int amount;

	/**
	 * 枠別の扱い
	 */
	private String treatmentByFrameColor;

}
