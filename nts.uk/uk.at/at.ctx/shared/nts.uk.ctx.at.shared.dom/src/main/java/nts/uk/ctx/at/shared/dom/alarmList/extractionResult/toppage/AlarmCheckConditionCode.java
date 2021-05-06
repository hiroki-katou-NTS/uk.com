package nts.uk.ctx.at.shared.dom.alarmList.extractionResult.toppage;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * アラームチェック条件コード
 */
@StringMaxLength(3)
@StringCharType(CharType.ALPHA_NUMERIC)
@ZeroPaddedCode
public class AlarmCheckConditionCode extends CodePrimitiveValue<AlarmCheckConditionCode> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new role code.
	 *
	 * @param rawValue the raw value
	 */
	public AlarmCheckConditionCode(String rawValue) {
		super(rawValue);
	}

}
