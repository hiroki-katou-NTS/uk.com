package nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 目安金額の値
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.目安金額.目安金額の値
 * @author lan_lt
 *
 */
@IntegerRange(min = 0, max = 99999999)
public class CriterionAmountValue extends IntegerPrimitiveValue<CriterionAmountValue> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new criterion amount.
	 *
	 * @param rawValue the raw value
	 */
	public CriterionAmountValue(Integer rawValue) {
		super(rawValue);
	}

}