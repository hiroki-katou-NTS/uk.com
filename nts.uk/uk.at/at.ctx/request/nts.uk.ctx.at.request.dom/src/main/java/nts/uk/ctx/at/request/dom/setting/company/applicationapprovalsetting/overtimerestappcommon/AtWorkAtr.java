package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業休出申請共通設定.出退勤時刻初期表示区分
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum AtWorkAtr {
	
	/**
	 * 表示しない
	 */
	NOTDISPLAY(0, "表示しない"),
	
	/**
	 * 実績から出退勤を初期表示する
	 */
	DISPLAY(1, "実績から出退勤を初期表示する"),
	
	/**
	 * 出勤は始業時刻、退勤は実績の退勤を初期表示する
	 */
	AT_START_WORK_OFF_PERFORMANCE(2, "出勤は始業時刻、退勤は実績の退勤を初期表示する"),
	
	/**
	 * 出勤は始業時刻、退勤は終業時刻を初期表示する
	 */
	AT_START_WORK_OFF_ENDWORK(3, "出勤は始業時刻、退勤は終業時刻を初期表示する");
	
	public final int value;
	
	public final String name;

}
