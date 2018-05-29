package nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.primitive;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(80)
//@StringCharType(CharType.ANY_HALF_WIDTH)
public class SelectionName extends StringPrimitiveValue<SelectionName>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SelectionName(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
