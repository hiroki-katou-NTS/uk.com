package nts.uk.ctx.at.request.pub.application.recognition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
