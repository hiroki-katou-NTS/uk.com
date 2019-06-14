package nts.uk.ctx.pr.core.dom.wageprovision.statementitem;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 英語名称
 */
@StringMaxLength(12)
public class EnglishName extends StringPrimitiveValue<EnglishName> {

	private static final long serialVersionUID = 1L;

	public EnglishName(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}