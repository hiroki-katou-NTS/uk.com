package nts.uk.shr.com.primitive;

import nts.arc.primitive.UpperCaseAlphaNumericPrimitiveValue;
import nts.arc.primitive.constraint.StringRegEx;

@StringRegEx("^[a-zA-Z]{1,3}[1-9]{1}[0-9]{0,6}$")
public class OutputCell extends UpperCaseAlphaNumericPrimitiveValue<OutputCell> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public OutputCell(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}