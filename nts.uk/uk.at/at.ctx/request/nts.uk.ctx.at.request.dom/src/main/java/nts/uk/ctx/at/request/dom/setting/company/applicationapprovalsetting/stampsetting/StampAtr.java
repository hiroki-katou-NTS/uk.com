package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.打刻申請設定.打刻分類
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum StampAtr {
	
	/**
	 * 出勤/退勤
	 */
	ATTENDANCE_RETIREMENT(0, "出勤/退勤"),
	
	/**
	 * 外出/戻り
	 */
	GOING_OUT_RETURNING(1, "外出/戻り"),
	
	/**
	 * 育児外出/育児戻り
	 */
	CHILDCARE_OUT_RETURN(2, "育児外出/育児戻り"),
	
	/**
	 * 応援入/応援出
	 */
	SUPPORT_IN_SUPPORT_OUT(3, "応援入/応援出"),
	
	/**
	 * 介護外出/介護戻り
	 */
	OUT_OF_CARE_RETURN_OF_CARE(4, "介護外出/介護戻り"),
	
	/**
	 * 休憩
	 */
	BREAK(5, ""),
	
	/**
	 * レコーダーイメージ
	 */
	RECORDER_IMAGE(6, "");
	
	public final int value;
	
	public final String name;
}
