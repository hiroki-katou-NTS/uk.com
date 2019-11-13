package nts.uk.ctx.hr.notice.app.find.report;

import java.util.List;

import lombok.Getter;
@Getter
public class SetItemReport extends ItemReportTypeStateDto{
	private List<String> items;
	
	private SetItemReport(List<String> items) {
		//ItemType.SET_ITEM.valu
		super();
		this.itemType = 1;
		this.items = items;
	}

	public static SetItemReport createFromJavaType(List<String> items) {
		return new SetItemReport(items);
	}
	
	//	// 1:セット項目(SetItem)
	//SET_ITEM(1),
	// 2:単体項目(SingleItem)
	//SINGLE_ITEM(2),
	// 3:セット項目（テーブル表示） (TableItem)
	//TABLE_ITEM(3);
}
