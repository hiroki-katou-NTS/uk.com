package nts.uk.ctx.pereg.pub.person.info.item;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ItemTypeExport {
	// 1:セット項目(SetItem)
	SET_ITEM(1),

	// 2:単体項目(SingleItem)
	SINGLE_ITEM(2),
	
	// 3:セット項目（テーブル表示） (TableItem)
	TABLE_ITEM(3);

	public final int value;
}
