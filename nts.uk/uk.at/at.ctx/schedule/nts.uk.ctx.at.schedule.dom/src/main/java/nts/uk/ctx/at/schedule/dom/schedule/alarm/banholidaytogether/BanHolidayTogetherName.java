package nts.uk.ctx.at.schedule.dom.schedule.alarm.banholidaytogether;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 同日休日禁止名称
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.同日休日禁止.同日休日禁止名称
 * @author lan_lt
 *
 */
@StringMaxLength(20)
public class BanHolidayTogetherName extends StringPrimitiveValue<BanHolidayTogetherName>{

	/**	serialVersionUID */
	private static final long serialVersionUID = 1L;
	

	public BanHolidayTogetherName(String rawValue) {
		super(rawValue);
	}


}
