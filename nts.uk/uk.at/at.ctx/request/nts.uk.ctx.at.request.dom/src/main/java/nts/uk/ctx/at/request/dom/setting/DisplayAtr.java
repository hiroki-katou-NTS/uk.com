package nts.uk.ctx.at.request.dom.setting;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.表示区分
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum DisplayAtr {
	
	/**
	 * 表示しない
	 */
	NOT_DISPLAY(0, "表示しない"),
	
	/**
	 * 表示する
	 */
	DISPLAY(1, "表示する");
	
	public final int value;
	
	public final String name;
}
