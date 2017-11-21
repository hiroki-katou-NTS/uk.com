package nts.uk.shr.pereg.app.command.userdef;

import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.command.ItemsByCategory;

public class PeregUserDefAddCommand extends PeregUserDefCommand {
	
	public PeregUserDefAddCommand(String employeeId, String personId, String newRecordId, ItemsByCategory itemsByCategory) {
		super(
				itemsByCategory.getCategoryCd(),
				newRecordId,
				employeeId,
				personId,
				itemsByCategory.collectItemsDefinedByUser());
	}
}
