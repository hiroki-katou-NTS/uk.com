package nts.uk.shr.pereg.app.command;

import lombok.Value;

@Value
public class PeregDeleteCommand {
	private final String personId;
	private final String employeeId;
	private final String categoryId;
	private final int categoryType;
	private final String categoryCode;
	private final String categoryName;
	private final String recordId;
}
