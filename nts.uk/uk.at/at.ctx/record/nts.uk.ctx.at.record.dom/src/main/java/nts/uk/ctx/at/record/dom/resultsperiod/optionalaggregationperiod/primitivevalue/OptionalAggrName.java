package nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.primitivevalue;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 任意集計名称
 * @author phongtq
 *
 */

@StringMaxLength(30)
public class OptionalAggrName extends StringPrimitiveValue<PrimitiveValue<String>>{

	public OptionalAggrName(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;

}
