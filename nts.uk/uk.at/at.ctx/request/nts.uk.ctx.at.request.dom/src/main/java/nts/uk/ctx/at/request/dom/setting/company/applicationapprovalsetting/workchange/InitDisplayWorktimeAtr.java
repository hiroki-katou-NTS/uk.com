package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.workchange;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.勤務変更申請設定.勤務時間の初期表示
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum InitDisplayWorktimeAtr {
	
	/**
	 * 定時
	 */
	FIXEDTIME(0, "定時"),
	
	/**
	 * 空白
	 */
	BLANK(1, "空白");
	
	public final int value;
	
	public final String name;
}
