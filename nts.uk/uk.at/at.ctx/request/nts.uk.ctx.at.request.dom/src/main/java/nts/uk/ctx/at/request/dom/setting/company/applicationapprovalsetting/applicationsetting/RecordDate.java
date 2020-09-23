package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.承認ルートの基準日
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum RecordDate {
	
	/**
	 * システム日付時点
	 */
	SYSTEM_DATE(0, "システム日付時点"),
	
	/**
	 * 申請対象日時点
	 */
	APP_DATE(1, "申請対象日時点");
	
	public final int value;
	
	public final String name;
	
}
