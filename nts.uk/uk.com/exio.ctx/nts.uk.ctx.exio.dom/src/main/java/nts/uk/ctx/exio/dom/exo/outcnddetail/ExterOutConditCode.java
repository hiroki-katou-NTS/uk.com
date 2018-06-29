package nts.uk.ctx.exio.dom.exo.outcnddetail;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;


@StringMaxLength(3)
public class ExterOutConditCode  extends StringPrimitiveValue<PrimitiveValue<String>>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public ExterOutConditCode(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}


}
