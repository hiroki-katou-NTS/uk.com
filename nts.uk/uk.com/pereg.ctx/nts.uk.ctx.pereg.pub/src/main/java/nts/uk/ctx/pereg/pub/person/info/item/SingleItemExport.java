package nts.uk.ctx.pereg.pub.person.info.item;

import lombok.Getter;

@Getter
public class SingleItemExport extends ItemTypeStateExport {

	protected DataTypeStateExport dataTypeState;

	private SingleItemExport(DataTypeStateExport dataTypeState) {
		super();
		this.itemType = ItemTypeExport.SINGLE_ITEM.value;
		this.dataTypeState = dataTypeState;
	}

	public static SingleItemExport createFromJavaType(DataTypeStateExport dataTypeState) {
		return new SingleItemExport(dataTypeState);
	}
}
