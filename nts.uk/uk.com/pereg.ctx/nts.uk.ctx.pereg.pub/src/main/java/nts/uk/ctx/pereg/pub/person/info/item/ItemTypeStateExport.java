package nts.uk.ctx.pereg.pub.person.info.item;

import java.util.List;

import lombok.Getter;

@Getter
public class ItemTypeStateExport {

	protected int itemType;
	
	public static ItemTypeStateExport createSetItemDto(List<String> items) {
		return SetItemExport.createFromJavaType(items);
	};

	public static ItemTypeStateExport createSetTableItemDto(List<String> items) {
		return SetTableItemExport.createFromJavaType(items);
	};
	
	public static ItemTypeStateExport createSingleItemDto(DataTypeStateExport dataTypeState) {
		return SingleItemExport.createFromJavaType(dataTypeState);
	};
	
}