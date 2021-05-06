package nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 目安金額枠NO
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.目安金額.目安金額枠NO
 * @author lan_lt
 *
 */
@IntegerMinValue(1)
@IntegerMaxValue(5)
public class CriterionAmountNo extends IntegerPrimitiveValue<PrimitiveValue<Integer>> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new criterion amount no.
	 *
	 * @param rawValue the raw value
	 */
	public CriterionAmountNo(Integer rawValue) {
		super(rawValue);
	}

}
