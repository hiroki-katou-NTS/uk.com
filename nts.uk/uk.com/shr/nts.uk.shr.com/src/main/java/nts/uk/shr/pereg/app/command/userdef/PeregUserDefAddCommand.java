package nts.uk.shr.pereg.app.command.userdef;

import nts.uk.shr.pereg.app.command.ItemsByCategory;

public class PeregUserDefAddCommand extends PeregUserDefCommand {
	
	public PeregUserDefAddCommand(String newRecordId, ItemsByCategory itemsByCategory) {
		super(
				itemsByCategory.getCategoryId(),
				newRecordId,
				itemsByCategory.collectItemsDefinedByUser());
	}
}
