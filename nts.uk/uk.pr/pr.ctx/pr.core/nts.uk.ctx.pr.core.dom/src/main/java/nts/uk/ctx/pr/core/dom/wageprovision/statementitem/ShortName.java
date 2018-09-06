package nts.uk.ctx.pr.core.dom.wageprovision.statementitem;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 項目名略名
 */
@StringMaxLength(12)
public class ShortName extends StringPrimitiveValue<ShortName> {

	private static final long serialVersionUID = 1L;

	public ShortName(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
