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
	
	/** input items */
	private final List<ItemValue> items;
	
	public PeregUserDefCommand(ItemsByCategory itemsByCategory) {
		this.categoryId = itemsByCategory.getCategoryId();
		this.recordId = itemsByCategory.getRecordId();
		this.items = itemsByCategory.collectItemsDefinedByUser();
	}
}
