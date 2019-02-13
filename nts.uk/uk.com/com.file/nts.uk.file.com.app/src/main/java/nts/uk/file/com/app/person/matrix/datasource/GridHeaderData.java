package nts.uk.file.com.app.person.matrix.datasource;

import lombok.Getter;

/**
 * GridHeaderData
 * @author lanlt
 *
 */
@Getter
public class GridHeaderData {
	private String itemCode;
	private String itemName;
	private String itemParentCode;
	private ItemTypeStateDataSource itemTypeState;
	private boolean required;
	private int itemOrder;
}
