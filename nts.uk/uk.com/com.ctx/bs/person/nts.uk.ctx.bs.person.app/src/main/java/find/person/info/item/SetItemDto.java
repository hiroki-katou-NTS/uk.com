package find.person.info.item;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.bs.person.dom.person.info.item.ItemType;

@Getter
public class SetItemDto extends ItemTypeStateDto {
	
	private List<String> items;
	
	private SetItemDto(List<String> items) {
		super();
		this.itemType = ItemType.SET_ITEM.value;
		this.items = items;
	}

	public static SetItemDto createFromJavaType(List<String> items) {
		return new SetItemDto(items);
	}
}
