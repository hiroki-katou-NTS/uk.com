package nts.uk.ctx.pereg.pub.person.info.item;

import java.util.List;

import lombok.Getter;

@Getter
public class SetItemExport extends ItemTypeStateExport {
	
	private List<String> items;
	
	private SetItemExport(List<String> items) {
		super();
		this.itemType = ItemTypeExport.SET_ITEM.value;
		this.items = items;
	}

	public static SetItemExport createFromJavaType(List<String> items) {
		return new SetItemExport(items);
	}
}
