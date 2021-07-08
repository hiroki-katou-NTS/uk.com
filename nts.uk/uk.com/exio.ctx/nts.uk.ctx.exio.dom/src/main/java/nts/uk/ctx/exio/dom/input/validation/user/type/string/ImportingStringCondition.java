package nts.uk.ctx.exio.dom.input.validation.user.type.string;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 受入条件文字列 
 */
@StringMaxLength(100)
public class ImportingStringCondition extends StringPrimitiveValue<ImportingStringCondition>{

	private static final long serialVersionUID = 1L;

	public ImportingStringCondition(String rawValue) {
		super(rawValue);
	}

}
