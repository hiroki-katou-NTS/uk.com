package nts.uk.ctx.pereg.dom.person.info.item;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pereg.dom.person.info.setitem.SetItem;
import nts.uk.ctx.pereg.dom.person.info.setitem.SetTableItem;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.SingleItem;

public class ItemTypeState extends AggregateRoot {
	@Getter
	protected ItemType itemType;

	public static ItemTypeState createSetItem(List<String> items) {
		return SetItem.createFromJavaType(items);
	};
	
	public static ItemTypeState createSetTableItem(List<String> items) {
		return SetTableItem.createFromJavaType(items);
	};
	

	public static ItemTypeState createSingleItem(DataTypeState dataTypeState) {
		return SingleItem.createFromJavaType(dataTypeState);
	};
}
