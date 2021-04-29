package nts.uk.ctx.at.shared.dom.monthlyattditem;

import lombok.AllArgsConstructor;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤怠項目.月次の勤怠項目.月別実績が2件存在した場合の表示方法
 * 
 * @author LienPTK
 *
 */
@AllArgsConstructor
public enum DisplayMonthResultsMethod {

	// 1件目を表示する
	DISPLAY_FIRST_ITEM(1),
	
	// 2件目を表示する
	DISPLAY_SECOND_ITEM(2),
	
	// 合計した値を表示する
	DISPLAY_TOTAL_VALUE(3);

	public final int value;
}
