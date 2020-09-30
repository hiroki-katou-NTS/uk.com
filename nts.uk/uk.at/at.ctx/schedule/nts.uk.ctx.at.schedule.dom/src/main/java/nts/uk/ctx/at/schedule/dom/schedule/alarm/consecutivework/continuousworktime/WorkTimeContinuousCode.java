package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.continuousworktime;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;
/**
 * 就業時間帯連続コード
 * @author lan_lt
 *
 */
@StringCharType(CharType.NUMERIC)
@ZeroPaddedCode
@StringMaxLength(3)
public class WorkTimeContinuousCode extends CodePrimitiveValue<WorkTimeContinuousCode>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public WorkTimeContinuousCode(String rawValue) {
		super(rawValue);
	}


}
