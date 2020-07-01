package nts.uk.ctx.at.request.dom.setting;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.利用区分
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum UseDivision {
	
	/**
	 * 利用しない
	 */
	NOT_USE(0, "利用しない"),
	
	/**
	 * 利用する
	 */
	TO_USE(1, "利用する");
	
	public final int value;
	
	public final String name;
}
