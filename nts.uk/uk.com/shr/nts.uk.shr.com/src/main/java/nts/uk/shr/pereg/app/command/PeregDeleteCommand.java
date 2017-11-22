package nts.uk.shr.pereg.app.command;

import lombok.Value;

@Value
public class PeregDeleteCommand {

	private final String personId;
	private final String employeeId;
	private final String categoryId;
	private final String recordId;
}
