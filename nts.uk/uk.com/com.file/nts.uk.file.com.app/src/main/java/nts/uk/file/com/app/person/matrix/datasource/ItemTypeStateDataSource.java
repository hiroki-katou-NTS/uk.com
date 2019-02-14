package nts.uk.file.com.app.person.matrix.datasource;

import java.util.List;

import lombok.Getter;

@Getter
public class ItemTypeStateDataSource {
	private int itemType;
	private List<String> items;
	private DataTypeStateDataSource dataTypeState;
	
}
