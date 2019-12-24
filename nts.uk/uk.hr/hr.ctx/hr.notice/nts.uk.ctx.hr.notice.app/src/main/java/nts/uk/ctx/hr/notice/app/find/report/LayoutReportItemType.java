package nts.uk.ctx.hr.notice.app.find.report;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum LayoutReportItemType {
	/*0 :  項目*/
	ITEM(0), 
	
	/*1 :  一覧表*/
	LIST(1),

	/*2 :  区切り線*/
	SeparatorLine(2);
	
	public final int value;
}
