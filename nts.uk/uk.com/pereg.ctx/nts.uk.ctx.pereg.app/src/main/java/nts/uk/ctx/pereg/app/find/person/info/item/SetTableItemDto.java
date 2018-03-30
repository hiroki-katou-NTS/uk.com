package nts.uk.ctx.pereg.app.find.person.info.item;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.item.ItemType;
@Getter
public class SetTableItemDto extends ItemTypeStateDto{
	private List<String> items;
	
	private SetTableItemDto(List<String> items) {
		super();
		this.itemType = ItemType.TABLE_ITEM.value;
		this.items = items;
	}

	public static SetTableItemDto createFromJavaType(List<String> items) {
		return new SetTableItemDto(items);
	}
}
