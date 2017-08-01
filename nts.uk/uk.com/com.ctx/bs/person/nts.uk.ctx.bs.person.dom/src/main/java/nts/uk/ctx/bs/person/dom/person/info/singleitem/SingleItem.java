package nts.uk.ctx.bs.person.dom.person.info.singleitem;

import lombok.Getter;
import nts.uk.ctx.bs.person.dom.person.info.item.ItemType;
import nts.uk.ctx.bs.person.dom.person.info.item.ItemTypeState;

@Getter
public class SingleItem extends ItemTypeState {

	protected DataTypeObject dataTypeObject;

	public SingleItem() {
		super();
		this.itemType = ItemType.SINGLE_ITEM;
	}
}
