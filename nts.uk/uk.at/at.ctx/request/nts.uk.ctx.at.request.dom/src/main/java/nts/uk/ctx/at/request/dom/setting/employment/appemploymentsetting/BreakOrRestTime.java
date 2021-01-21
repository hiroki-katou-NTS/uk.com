package nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * @author loivt
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.雇用別.雇用別申請承認設定.振休振出区分
 */
@AllArgsConstructor
public enum BreakOrRestTime {
	
	/**
	 * 振休
	 */
	RESTTIME(0, "振休"),
	
	/**
	 * 振出 
	 */
	BREAKTIME(1, "振出");

	public final int value;
	
	public final String name;
	
}
