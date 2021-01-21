package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.事前受付チェック方法
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum BeforeAddCheckMethod {
	
	/**
	 * 時刻でチェック
	 */
	CHECK_IN_TIME(0, "時刻でチェック"),
	
	/**
	 * 日数でチェック
	 */
	CHECK_IN_DAY(1, "日数でチェック");

	public final int value;
	
	public final String name;
}
