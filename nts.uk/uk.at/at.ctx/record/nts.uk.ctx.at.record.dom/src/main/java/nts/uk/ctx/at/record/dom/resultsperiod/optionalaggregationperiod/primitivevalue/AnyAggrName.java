package nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.primitivevalue;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 任意集計名称
 *
 * @author phongtq
 */
@StringMaxLength(30)
public class AnyAggrName extends StringPrimitiveValue<AnyAggrName> {

	private static final long serialVersionUID = -8533137874575093232L;

	/**
	 * コンストラクタ
	 *
	 * @param name 名称
	 */
	public AnyAggrName(String name) {
		super(name);
	}

}
