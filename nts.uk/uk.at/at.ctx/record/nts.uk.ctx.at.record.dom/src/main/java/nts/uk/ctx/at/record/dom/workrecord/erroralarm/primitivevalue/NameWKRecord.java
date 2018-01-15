package nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
public class NameWKRecord extends StringPrimitiveValue<NameWKRecord> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NameWKRecord(String rawValue) {
		super(rawValue);
	}

	

}
