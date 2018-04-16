package nts.uk.ctx.pereg.dom.person.info.setitem;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.item.ItemType;
import nts.uk.ctx.pereg.dom.person.info.item.ItemTypeState;
@Getter
public class SetTableItem  extends ItemTypeState{
	List<String> items;

	private SetTableItem(List<String> items) {
		super();
		this.itemType = ItemType.TABLE_ITEM;
		this.items = items;
	}

	public static SetTableItem createFromJavaType(List<String> items) {
		return new SetTableItem(items);
	}
}
