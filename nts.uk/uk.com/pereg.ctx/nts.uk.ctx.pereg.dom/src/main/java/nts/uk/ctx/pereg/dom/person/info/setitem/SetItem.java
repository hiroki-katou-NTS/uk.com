package nts.uk.ctx.pereg.dom.person.info.setitem;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.item.ItemType;
import nts.uk.ctx.pereg.dom.person.info.item.ItemTypeState;

@Getter
public class SetItem extends ItemTypeState {

	List<String> items;

	private SetItem(List<String> items) {
		super();
		this.itemType = ItemType.SET_ITEM;
		this.items = items;
	}

	public static SetItem createFromJavaType(List<String> items) {
		return new SetItem(items);
	}
}
