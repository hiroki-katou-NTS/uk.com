/**
 * 2:17:33 PM Jul 21, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * @author hungnm
 *
 */
/* 勤務実績のエラーアラームコード */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(4)
@ZeroPaddedCode
public class ErrorAlarmWorkRecordCode extends StringPrimitiveValue<ErrorAlarmWorkRecordCode> {

	private static final long serialVersionUID = 1L;

	public ErrorAlarmWorkRecordCode(String rawValue) {
		super(rawValue);
	}
}