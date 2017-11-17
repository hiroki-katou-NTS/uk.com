package nts.uk.shr.pereg.app.command.userdef;

import java.util.List;

import lombok.Getter;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.command.ItemsByCategory;

@Getter
public abstract class PeregUserDefCommand {

	/** category ID */
	private final String categoryId;
	
	/** Record Id, but this is null when new record */
	private final String recordId;
	
	private String employeeId;
	
	private String personId;
	
	/** input items */
	private final List<ItemValue> items;
	
	public PeregUserDefCommand(ItemsByCategory itemsByCategory) {
		this(itemsByCategory.getCategoryCd(), itemsByCategory.getRecordId(), itemsByCategory.collectItemsDefinedByUser());
	}
	
	public PeregUserDefCommand(String categoryId, String recordId, List<ItemValue> items) {
		this.categoryId = categoryId;
		this.recordId = recordId;
		this.items = items;
	}
	
	public PeregUserDefCommand(String categoryId, String recordId, String employeeId, String personId,List<ItemValue> items) {
		this.categoryId = categoryId;
		this.recordId = recordId;
		this.employeeId = employeeId;
		this.personId = personId;
		this.items = items;
	}
}
