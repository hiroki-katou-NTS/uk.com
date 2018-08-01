package nts.uk.ctx.at.request.dom.setting.workplace;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
@StringMaxLength(500)
public class Memo500 extends StringPrimitiveValue<Memo500>{
	public Memo500(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
