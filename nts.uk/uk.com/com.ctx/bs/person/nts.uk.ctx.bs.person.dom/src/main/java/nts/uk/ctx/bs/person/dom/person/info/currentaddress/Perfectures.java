package nts.uk.ctx.bs.person.dom.person.info.currentaddress;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;


@StringMaxLength(value = 36)
public class Perfectures extends StringPrimitiveValue<Perfectures>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Perfectures(String perfectures){
		super(perfectures);
	}
}
