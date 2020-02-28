package nts.uk.ctx.hr.notice.dom.report.valueImported;

import lombok.Getter;

@Getter
public class SingleItemImport extends ItemTypeStateImport {

	protected DataTypeStateImport dataTypeState;

	private SingleItemImport(DataTypeStateImport dataTypeState) {
		super();
		this.itemType = ItemTypeImport.SINGLE_ITEM.value;
		this.dataTypeState = dataTypeState;
	}

	public static SingleItemImport createFromJavaType(DataTypeStateImport dataTypeState) {
		return new SingleItemImport(dataTypeState);
	}
}
