package nts.uk.ctx.sys.assist.dom.storage;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(260)
public class SaveFileName extends StringPrimitiveValue<SaveFileName>{

	public SaveFileName(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
