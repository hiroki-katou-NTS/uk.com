package nts.uk.ctx.at.aggregation.dom.schedulecounter.budget.laborcost;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 人件費予算金額
 * @author kumiko_otake
 */
@IntegerRange(min = 0, max = 99999999)
public class LaborCostBudgetAmount extends IntegerPrimitiveValue<LaborCostBudgetAmount> {

	/** serialVersionUID **/
	private static final long serialVersionUID = 1L;

	/** Constructor **/
	public LaborCostBudgetAmount(Integer rawValue) {
		super(rawValue);
	}

}
