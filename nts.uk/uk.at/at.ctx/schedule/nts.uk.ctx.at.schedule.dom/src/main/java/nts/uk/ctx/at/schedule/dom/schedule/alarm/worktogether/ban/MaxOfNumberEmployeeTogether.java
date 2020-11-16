package nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 同時出勤上限人数
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.同時出勤.同時出勤禁止.同時出勤上限人数
 * @author lan_lt
 *
 */
@IntegerRange(min = 2, max = 10)
public class MaxOfNumberEmployeeTogether extends IntegerPrimitiveValue<MaxOfNumberEmployeeTogether>{
	/**serialVersionUID	 */
	private static final long serialVersionUID = 1L;
	
	public MaxOfNumberEmployeeTogether(Integer rawValue) {
		super(rawValue);
	}

}
