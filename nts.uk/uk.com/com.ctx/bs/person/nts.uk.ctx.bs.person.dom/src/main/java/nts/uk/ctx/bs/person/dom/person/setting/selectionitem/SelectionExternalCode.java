package nts.uk.ctx.bs.person.dom.person.setting.selectionitem;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min = 1, max = 10)
public class SelectionExternalCode extends IntegerPrimitiveValue<SelectionExternalCode>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SelectionExternalCode(int rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
