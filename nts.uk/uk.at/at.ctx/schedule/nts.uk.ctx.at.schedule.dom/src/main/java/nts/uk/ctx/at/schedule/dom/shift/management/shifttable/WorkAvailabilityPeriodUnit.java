package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * シフト勤務単位
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.シフト表.シフト勤務単位
 * @author hiroko_miura
 *
 */
@RequiredArgsConstructor
public enum WorkAvailabilityPeriodUnit {
	/**　一週間	 */
	WEEKLY(0),
	
	/** 一ヶ月間 */
	MONTHLY(1);
	
	public final int value;
	
	public static WorkAvailabilityPeriodUnit of(int value) {
		return EnumAdaptor.valueOf(value, WorkAvailabilityPeriodUnit.class);
	}
}
