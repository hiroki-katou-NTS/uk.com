package nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.primitive;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(10)
@StringCharType(CharType.ALPHA_NUMERIC)
public class SelectionCdAlphaNumeric extends SelectionCD{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SelectionCdAlphaNumeric(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
