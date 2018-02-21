package nts.uk.ctx.at.function.app.command.alarm.extraprocessstatus;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class DeleteAlarmListExtraProcessStatusCmd {
	/**会社ID */
	private String companyID;
	/** 開始年月日*/
	private GeneralDate startDate;
	/** 開始時刻 */
	private int startTime;
}
