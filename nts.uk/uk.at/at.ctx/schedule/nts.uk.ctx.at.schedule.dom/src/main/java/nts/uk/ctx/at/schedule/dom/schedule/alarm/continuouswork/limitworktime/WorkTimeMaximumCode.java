package nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.limitworktime;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
/**
 * 就業時間帯上限コード
 * @author lan_lt
 *
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(3)
public class WorkTimeMaximumCode extends CodePrimitiveValue<WorkTimeMaximumCode>{

	private static final long serialVersionUID = 1L;

	
	public WorkTimeMaximumCode(String rawValue) {
		super(rawValue);
	}


}
