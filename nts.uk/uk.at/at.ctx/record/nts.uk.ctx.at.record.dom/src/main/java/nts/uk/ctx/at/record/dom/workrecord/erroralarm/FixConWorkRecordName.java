package nts.uk.ctx.at.record.dom.workrecord.erroralarm;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 名称
 * @author tutk
 *
 */
@StringMaxLength(20)
public class FixConWorkRecordName extends StringPrimitiveValue<FixConWorkRecordName> {

	public FixConWorkRecordName(String rawValue) {
		super(rawValue);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
