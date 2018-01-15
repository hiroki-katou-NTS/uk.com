package nts.uk.ctx.at.request.dom.application;

import lombok.AllArgsConstructor;

/**
 * 就業ルート区分
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum EmploymentRootAtr {
	
	// 共通
	COMMON(0, "共通"),
	
	// 申請
	APPLICATION(1, "申請"),
	
	// 確認
	CONFIRMATION(2, "確認"),
	
	// 任意項目
	ANYITEM(3, "任意項目");
	
	public final int value;
	
	public final String name;
}
