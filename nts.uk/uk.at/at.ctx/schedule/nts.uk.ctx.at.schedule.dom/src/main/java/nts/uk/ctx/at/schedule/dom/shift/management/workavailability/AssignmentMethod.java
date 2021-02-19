package nts.uk.ctx.at.schedule.dom.shift.management.workavailability;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 勤務希望の指定方法
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.勤務希望.勤務希望の指定方法
 * @author dan_pv
 *
 */
@RequiredArgsConstructor
public enum AssignmentMethod {

	HOLIDAY(0), // 休日

	SHIFT(1), // シフト

	TIME_ZONE(2); // 時間帯

	public final int value;
	
	public static AssignmentMethod of(int value) {
		return EnumAdaptor.valueOf(value, AssignmentMethod.class);
	}
}
