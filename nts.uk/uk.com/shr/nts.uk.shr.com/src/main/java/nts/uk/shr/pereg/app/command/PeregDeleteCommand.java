package nts.uk.shr.pereg.app.command;

import java.util.List;

import lombok.Value;
import nts.uk.shr.pereg.app.ItemValue;

@Value
public class PeregDeleteCommand {
	private final String personId;
	private final String employeeId;
	private final String categoryId;
	private final int categoryType;
	private final String categoryCode;
	private final String categoryName;
	private final String recordId;
	// add for log delete
	private final List<ItemValue> inputs;
}
