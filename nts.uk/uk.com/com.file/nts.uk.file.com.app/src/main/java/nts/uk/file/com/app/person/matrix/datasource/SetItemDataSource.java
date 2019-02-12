package nts.uk.file.com.app.person.matrix.datasource;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.item.ItemType;
/**
 * SetItemDataSource
 * @author lanlt
 *
 */
@Getter
public class SetItemDataSource extends ItemTypeStateDataSource {
	private List<String> items;

	private SetItemDataSource(List<String> items) {
		super();
		this.itemType = ItemType.SET_ITEM.value;
		this.items = items;
	}

	public static SetItemDataSource createFromJavaType(List<String> items) {
		return new SetItemDataSource(items);
	}
}
