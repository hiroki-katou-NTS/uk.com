package nts.uk.file.com.app.person.matrix.datasource;

import java.util.List;

import lombok.Getter;

/**
 * GridHeaderData
 * @author lanlt
 *
 */
@Getter
public class GridHeaderData {
	private String itemId;
	private int itemOrder;
	private String itemCode;
	private String itemParentCode;
	private String itemName;
	private ItemTypeStateDataSource itemTypeState;
	private boolean required;
	private String resourceId;
	private List<GridHeaderData> childs;
}
