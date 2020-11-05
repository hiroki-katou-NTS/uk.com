package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 希望休日の上限日数
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.勤務希望.希望休日の上限日数
 * @author hiroko_miura
 *
 */
@IntegerRange(min =0, max =15)
public class HolidayAvailabilityMaxdays extends IntegerPrimitiveValue<HolidayAvailabilityMaxdays> {

	private static final long serialVersionUID = 1L;
	
	public HolidayAvailabilityMaxdays (int rawValue) {
		super (rawValue);
	}
}
