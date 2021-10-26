package nts.uk.ctx.exio.dom.input.setting;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * 受入設定名称
 *
 */
@SuppressWarnings("serial")
@StringMaxLength(40)
public class ExternalImportName extends StringPrimitiveValue<ExternalImportName> {

	public ExternalImportName(String rawValue) {
		super(rawValue);
	}
}
