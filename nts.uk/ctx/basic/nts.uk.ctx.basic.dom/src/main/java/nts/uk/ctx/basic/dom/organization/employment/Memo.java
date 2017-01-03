package nts.uk.ctx.basic.dom.organization.employment;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(500)
public class Memo extends StringPrimitiveValue<Memo> {

	public Memo(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/**serialVersionUID	 */
	private static final long serialVersionUID = 1L;

}
