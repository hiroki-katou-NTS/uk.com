package nts.uk.file.com.app.approvalmanagement.workroot;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class ApproversQuery {

	/**
	 * 基準日
	 */
	private GeneralDate baseDate;
}
