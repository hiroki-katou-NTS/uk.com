package nts.uk.ctx.bs.person.dom.person.info.currentaddress;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringRegEx;

@StringRegEx("^\\d{7}|(\\d{3}-\\d{4}$)")
public class PostalCode extends StringPrimitiveValue<PostalCode>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PostalCode(String postalCode){
		super(postalCode);
	}
}
