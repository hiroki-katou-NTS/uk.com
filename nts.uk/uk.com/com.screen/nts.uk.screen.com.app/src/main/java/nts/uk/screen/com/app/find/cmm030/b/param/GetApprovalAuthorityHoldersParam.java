package nts.uk.screen.com.app.find.cmm030.b.param;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class GetApprovalAuthorityHoldersParam {

	/**
	 * 職場ID
	 */
	private String workplaceId;
	
	/**
	 * 基準日
	 */
	private GeneralDate baseDate;
}
