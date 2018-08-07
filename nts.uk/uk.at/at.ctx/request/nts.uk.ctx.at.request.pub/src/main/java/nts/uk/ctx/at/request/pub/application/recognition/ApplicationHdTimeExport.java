package nts.uk.ctx.at.request.pub.application.recognition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
@AllArgsConstructor
public class ApplicationHdTimeExport {
	/**
	 * 年月日
	 */
	private GeneralDate date;
	
	/**
	 * total休出時間
	 */
	private int breakTime;
}
