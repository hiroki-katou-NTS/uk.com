package nts.uk.ctx.at.request.pub.application.recognition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
