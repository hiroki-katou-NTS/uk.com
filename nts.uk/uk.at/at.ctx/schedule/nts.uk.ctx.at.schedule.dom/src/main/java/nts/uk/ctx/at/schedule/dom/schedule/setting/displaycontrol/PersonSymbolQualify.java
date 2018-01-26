package nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * TanLV
 *
 */
@StringMaxLength(2)
public class PersonSymbolQualify extends StringPrimitiveValue<PersonSymbolQualify> {
	private static final long serialVersionUID = 1L;

	public PersonSymbolQualify(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}