/**
 * 2:17:45 PM Jul 21, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author hungnm
 *
 */
/* */
@StringMaxLength(20)
public class ErrorAlarmWorkRecordName extends StringPrimitiveValue<ErrorAlarmWorkRecordName> {

	private static final long serialVersionUID = 1L;

	public ErrorAlarmWorkRecordName(String rawValue) {
		super(rawValue);
	}

}
