package nts.uk.file.com.app.person.matrix.datasource;

import lombok.Getter;
import lombok.Setter;

/**
 * GridHeaderData
 * @author lanlt
 *
 */
@Getter
public class GridHeaderData {
	private String itemCode;
	@Setter
	private String itemName;
	private String itemParentCode;
	private ItemTypeStateDataSource itemTypeState;
	private boolean required;
	private int itemOrder;
}
