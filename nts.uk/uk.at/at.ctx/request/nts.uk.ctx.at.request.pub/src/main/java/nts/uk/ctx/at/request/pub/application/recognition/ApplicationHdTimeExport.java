package nts.uk.ctx.at.request.pub.application.recognition;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class ApplicationHdTimeExport {
	/**
	 * 年月日
	 */
	private GeneralDate date;
	
	/**
	 * total残業時間
	 */
	private int breakTime;
}
