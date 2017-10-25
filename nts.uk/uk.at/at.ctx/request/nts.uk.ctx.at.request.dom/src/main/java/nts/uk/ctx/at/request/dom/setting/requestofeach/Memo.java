package nts.uk.ctx.at.request.dom.setting.requestofeach;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 備考
 * @author dudt
 *
 */
@StringMaxLength(100)
public class Memo extends StringPrimitiveValue<Memo> {

	public Memo(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
