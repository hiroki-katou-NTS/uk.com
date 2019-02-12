package nts.uk.file.com.app.person.matrix.datasource;

import java.util.List;

import lombok.Getter;

@Getter
public class ItemTypeStateDataSource {
	protected int itemType;
	
	public static ItemTypeStateDataSource createSetItemDataSource(List<String> items) {
		return SetItemDataSource.createFromJavaType(items);
	};

	public static ItemTypeStateDataSource createSetTableItemDataSource(List<String> items) {
		return SetTableItemDataSource.createFromJavaType(items);
	};
	
	public static ItemTypeStateDataSource createSingleItemDataSource(DataTypeStateDataSource dataTypeState) {
		return SingleItemDataSource.createFromJavaType(dataTypeState);
	};
}
