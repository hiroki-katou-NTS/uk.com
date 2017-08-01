package nts.uk.ctx.bs.person.dom.person.info.setitem;

import java.util.List;

import nts.uk.ctx.bs.person.dom.person.info.item.ItemType;
import nts.uk.ctx.bs.person.dom.person.info.item.ItemTypeState;


public class SetItem extends ItemTypeState{
	
	List<String> items;

	public SetItem(List<String> items) {
		super();
		this.itemType = ItemType.SET_ITEM;
		this.items = items;
	}
	
	
}
