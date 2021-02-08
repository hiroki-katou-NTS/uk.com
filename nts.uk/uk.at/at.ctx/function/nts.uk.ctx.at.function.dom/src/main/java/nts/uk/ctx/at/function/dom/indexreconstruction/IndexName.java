package nts.uk.ctx.at.function.dom.indexreconstruction;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.ctx.at.function.dom.annualworkschedule.primitivevalue.OutItemsWoScName;

/**
 *	 インデックス名
 */
@StringMaxLength(64)
public class IndexName extends StringPrimitiveValue<OutItemsWoScName> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new index name.
	 *
	 * @param rawValue the raw value
	 */
	public IndexName(String rawValue) {
		super(rawValue);
	}

}
