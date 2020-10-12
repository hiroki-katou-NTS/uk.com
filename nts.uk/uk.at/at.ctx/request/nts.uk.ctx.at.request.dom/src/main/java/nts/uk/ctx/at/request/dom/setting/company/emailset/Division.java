package nts.uk.ctx.at.request.dom.setting.company.emailset;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.メール設定.区分
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum Division {
	
	/**
	 * 申請承認
	 */
	APPLICATION_APPROVAL(0, "申請承認"),
	
	/**
	 * 差し戻し
	 */
	REMAND(1, "差し戻し"),
	
	/**
	 * 残業指示
	 */
	OVERTIME_INSTRUCTION(2, "残業指示"),
	
	/**
	 * 休出指示
	 */
	HOLIDAY_WORK_INSTRUCTION(3, "休出指示");
	
	public final int value;
	
	public final String name;
}
