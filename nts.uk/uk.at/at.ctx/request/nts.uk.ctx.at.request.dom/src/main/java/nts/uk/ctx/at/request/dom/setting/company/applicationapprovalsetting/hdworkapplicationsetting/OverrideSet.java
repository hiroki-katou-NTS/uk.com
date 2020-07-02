package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.休日出勤申請設定.実績超過打刻優先設定
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum OverrideSet {
	
	/**
	 * システム時刻優先
	 */
	SYSTEM_TIME_PRIORITY(0, "システム時刻優先"),
	
	/**
	 * 退勤時刻優先
	 */
	TIME_OUT_PRIORITY(1, "退勤時刻優先");
	
	public final int value;
	
	public final String name;
}
