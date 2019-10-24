package nts.uk.screen.at.app.dailyperformance.correction.mobile;

import lombok.Getter;
import nts.arc.time.GeneralDate;
@Getter
public class ErrorAlarmParamMob {
	/**社員ID*/
	private String employeeId;
	/**日*/
	private GeneralDate date;
	/**エラーアラームコード*/
	private String errCode;
}
