package nts.uk.ctx.at.schedule.dom.schedule.alarm.banholidaytogether;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 同日出勤下限人数
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.同日休日禁止.同日出勤下限人数
 * @author lan_lt
 *
 */
@IntegerRange(min = 0, max = 9)
public class MinNumberEmployeeTogether extends IntegerPrimitiveValue<MinNumberEmployeeTogether>{

	/**serialVersionUID	 */
	private static final long serialVersionUID = 1L;
	
	public MinNumberEmployeeTogether(Integer rawValue) {
		super(rawValue);
	}

}
