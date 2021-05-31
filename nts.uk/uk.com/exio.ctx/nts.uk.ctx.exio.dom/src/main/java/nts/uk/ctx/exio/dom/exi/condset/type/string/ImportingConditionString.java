package nts.uk.ctx.exio.dom.exi.condset.type.string;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 受入条件文字列 
 */
@StringMaxLength(100)
public class ImportingConditionString extends StringPrimitiveValue<ImportingConditionString>{

	private static final long serialVersionUID = 1L;

	public ImportingConditionString(String rawValue) {
		super(rawValue);
	}

}
