package nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(500)
public class MemoSelection extends StringPrimitiveValue<MemoSelection>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MemoSelection(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
