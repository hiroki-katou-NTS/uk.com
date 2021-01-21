package nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.雇用別.雇用別申請承認設定.出張申請の勤務種類
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum BusinessTripAppWorkType {
	
	/**
	 * 出勤日
	 */
	WORK_DAY(0, "出勤日"),
	
	/**
	 * 休日
	 */
	HOLIDAY(1, "休日");

	public final int value;
	
	public final String name;
	
}
