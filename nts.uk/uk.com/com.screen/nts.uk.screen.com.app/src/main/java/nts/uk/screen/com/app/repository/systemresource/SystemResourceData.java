package nts.uk.screen.com.app.repository.systemresource;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SystemResourceData {
	
	private String resourceId;
	
	private String resourceContent;
}
