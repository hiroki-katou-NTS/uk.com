package nts.uk.screen.com.app.find.cmm030.a.param;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class DisplayEmployeeApproversParam {
	
	/**
	 * 社員ID
	 */
	private String sid;

	/**
	 * 基準日
	 */
	private GeneralDate baseDate;
}
