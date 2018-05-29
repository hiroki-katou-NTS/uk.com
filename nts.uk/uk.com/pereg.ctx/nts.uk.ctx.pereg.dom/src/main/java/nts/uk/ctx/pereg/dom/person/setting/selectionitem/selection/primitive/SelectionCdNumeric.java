package nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.primitive;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(10)
@StringCharType(CharType.NUMERIC)
public class SelectionCdNumeric extends SelectionCD{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SelectionCdNumeric(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
