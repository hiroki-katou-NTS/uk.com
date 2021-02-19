package nts.uk.ctx.at.shared.dom.employeeworkway.businesstype;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
public class BusinessTypeName extends StringPrimitiveValue<PrimitiveValue<String>>{

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public BusinessTypeName(String rawValue) {
		super(rawValue);
	}
}
