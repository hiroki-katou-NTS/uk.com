package nts.uk.ctx.pereg.pub.person.info.item;

import java.util.List;

import lombok.Getter;
@Getter
public class SetTableItemExport extends ItemTypeStateExport{
	private List<String> items;
	
	private SetTableItemExport(List<String> items) {
		super();
		this.itemType = ItemTypeExport.TABLE_ITEM.value;
		this.items = items;
	}

	public static SetTableItemExport createFromJavaType(List<String> items) {
		return new SetTableItemExport(items);
	}
}
