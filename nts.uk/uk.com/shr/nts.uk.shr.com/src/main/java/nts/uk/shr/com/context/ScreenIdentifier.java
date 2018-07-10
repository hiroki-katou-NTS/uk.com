package nts.uk.shr.com.context;

import lombok.Value;

@Value
public class ScreenIdentifier {
	private final String programId;
	private final String screenId;
	private final String queryString;
}
