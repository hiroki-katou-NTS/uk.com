package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import lombok.RequiredArgsConstructor;
/**
 * 勤務予定の条件区分
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.表示設定
 * @author HieuLt
 *
 */
@RequiredArgsConstructor
public enum ConditionATRWorkSchedule {

	// 	0:保険加入状況	
	INSURANCE_STATUS(0, "保険加入状況"),

	// 	1:チーム		
	TEAM(1, "チーム"),
	
	//  2:ランク	 
	RANK (2, "ランク"),
	
	// 3:資格		 
	QUALIFICATION (3, "資格"),
	
	// 4:免許区分 
	LICENSE_ATR (4, "免許区分");


	public final int value;

	public final String name;

	/** The Constant values. */
	private final static ConditionATRWorkSchedule[] values = ConditionATRWorkSchedule.values();

	public static ConditionATRWorkSchedule valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ConditionATRWorkSchedule val : ConditionATRWorkSchedule.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
