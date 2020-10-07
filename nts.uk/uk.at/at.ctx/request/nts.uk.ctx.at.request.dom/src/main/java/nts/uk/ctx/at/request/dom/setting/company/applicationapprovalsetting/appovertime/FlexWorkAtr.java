package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業申請設定.フレックス勤務者区分
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum FlexWorkAtr {
	
	/**
	 * フレックス時間勤務
	 */
	FLEX_TIME(1, "フレックス時間勤務"),
	
	/**
	 * フレックス時間勤務以外
	 */
	OTHER(0, "フレックス時間勤務以外");
	
	public final int value;
	
	public final String name;
}
