package nts.uk.ctx.at.record.dom.workrecord.operationsetting;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(100)
public class Comment extends StringPrimitiveValue<Comment>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Comment(String rawValue) {
		super(rawValue);
	}

}
