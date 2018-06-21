package nts.uk.shr.com.permit;

import lombok.Value;

@Value
public class DescriptionOfAvailabilityPermission {

	private final int functionNo;
	private final String name;
	private final String explanation;
	private final int displayOrder;
	private final boolean defaultValue;
}
