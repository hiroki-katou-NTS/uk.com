package nts.uk.ctx.hr.notice.dom.report.valueImported;

import java.util.List;

import lombok.Getter;
@Getter
public class SetTableItemImport extends ItemTypeStateImport{
	private List<String> items;
	
	private SetTableItemImport(List<String> items) {
		super();
		this.itemType = ItemTypeImport.TABLE_ITEM.value;
		this.items = items;
	}

	public static SetTableItemImport createFromJavaType(List<String> items) {
		return new SetTableItemImport(items);
	}
}
