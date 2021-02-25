package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.打刻申請設定.外出種類
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum GoOutType {
	
	/**
	 * 私用
	 */
	PRIVATE(0, "私用"),
	
	/**
	 * 公用
	 */
	OFFICE(1, "公用"),
	
	/**
	 * 有償
	 */
	COMPENSATION(2, "有償"),
	
	/**
	 * 組合
	 */
	UNION(3, "組合");
	
	public final int value;
	
	public final String name; 
}
