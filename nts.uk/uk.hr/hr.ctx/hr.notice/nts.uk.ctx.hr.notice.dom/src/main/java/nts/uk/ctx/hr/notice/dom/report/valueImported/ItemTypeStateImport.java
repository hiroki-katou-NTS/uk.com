package nts.uk.ctx.hr.notice.dom.report.valueImported;

import java.util.List;

import lombok.Getter;

@Getter
public class ItemTypeStateImport {

	protected int itemType;
	
	public static ItemTypeStateImport createSetItemDto(List<String> items) {
		return SetItemImport.createFromJavaType(items);
	};

	public static ItemTypeStateImport createSetTableItemDto(List<String> items) {
		return SetTableItemImport.createFromJavaType(items);
	};
	
	public static ItemTypeStateImport createSingleItemDto(DataTypeStateImport dataTypeState) {
		return SingleItemImport.createFromJavaType(dataTypeState);
	};
	
}