package nts.uk.ctx.at.function.app.command.alarm.extraprocessstatus;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class DeleteAlarmListExtraProcessStatusCmd {
	/**ID*/
	private String extraProcessStatusID;
}
