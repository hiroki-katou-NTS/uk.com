package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 締切週
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.シフト表.締切週
 * @author hiroko_miura
 *
 */

@RequiredArgsConstructor
public enum DeadlineWeekAtr {

	/** 先週 */
	ONE_WEEK_AGO(0),
	
	/** 先々週 */
	TWO_WEEK_AGO(1);
	
	public final int value;
	
	public static DeadlineWeekAtr of(int value) {
		return EnumAdaptor.valueOf(value, DeadlineWeekAtr.class);
	}
}
