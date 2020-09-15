package nts.uk.ctx.at.schedule.dom.schedule.alarm.bansamedayholiday;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
/**
 * 同日休日禁止コード
 * @author lan_lt
 *
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(4)
public class BanSameDayHolidayCode extends CodePrimitiveValue<BanSameDayHolidayCode>{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public BanSameDayHolidayCode(String rawValue) {
		super(rawValue);
	}

}
