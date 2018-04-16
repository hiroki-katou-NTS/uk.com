package nts.uk.ctx.at.request.pub.application.recognition;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class ApplicationOvertimeExport {
	/**
	 * 年月日
	 */
	private GeneralDate date;
	
	/**
	 * total残業時間
	 */
	private int totalOtHours;
}
