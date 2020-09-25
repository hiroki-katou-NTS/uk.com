package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.appdeadlineset;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.申請締切設定.締切基準
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum DeadlineCriteria {
	/**
	 * 暦日
	 */
	CALENDAR_DAY(0, "暦日"),
	/**
	 * 稼働日
	 */
	WORKING_DAY(1, "稼働日");
	
	public final int value;
	
	public final String name;
}
