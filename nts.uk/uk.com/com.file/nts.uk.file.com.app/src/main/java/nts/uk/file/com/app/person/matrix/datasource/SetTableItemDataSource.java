package nts.uk.file.com.app.person.matrix.datasource;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.item.ItemType;

@Getter
public class SetTableItemDataSource extends ItemTypeStateDataSource {
	private List<String> items;

	private SetTableItemDataSource(List<String> items) {
		super();
		this.itemType = ItemType.TABLE_ITEM.value;
		this.items = items;
	}

	public static SetTableItemDataSource createFromJavaType(List<String> items) {
		return new SetTableItemDataSource(items);
	}
}