package nts.uk.ctx.pr.core.dom.wageprovision.statementitem;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * その他言語名称
 */
@StringMaxLength(12)
public class OtherLanguageName extends StringPrimitiveValue<OtherLanguageName> {

	private static final long serialVersionUID = 1L;

	public OtherLanguageName(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}