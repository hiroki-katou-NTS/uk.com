package nts.uk.ctx.exio.dom.exo.outcnddetail;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;


@StringMaxLength(2000)
public class ExterOutCdnSql extends StringPrimitiveValue<PrimitiveValue<String>> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExterOutCdnSql(String rawValue) {
		super(rawValue);
	}

}
