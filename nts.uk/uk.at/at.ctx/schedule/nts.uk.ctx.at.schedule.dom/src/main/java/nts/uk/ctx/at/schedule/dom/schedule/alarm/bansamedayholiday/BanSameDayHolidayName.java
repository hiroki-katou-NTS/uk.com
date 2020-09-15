package nts.uk.ctx.at.schedule.dom.schedule.alarm.bansamedayholiday;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 同日休日禁止名称
 * @author lan_lt
 *
 */
@StringMaxLength(10)
public class BanSameDayHolidayName extends StringPrimitiveValue<BanSameDayHolidayName>{

	/**	serialVersionUID */
	private static final long serialVersionUID = 1L;
	

	public BanSameDayHolidayName(String rawValue) {
		super(rawValue);
	}


}
