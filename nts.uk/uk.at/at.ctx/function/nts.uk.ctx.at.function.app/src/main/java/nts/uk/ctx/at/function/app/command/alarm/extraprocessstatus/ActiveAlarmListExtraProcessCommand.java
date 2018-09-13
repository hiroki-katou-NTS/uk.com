package nts.uk.ctx.at.function.app.command.alarm.extraprocessstatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveAlarmListExtraProcessCommand {

	private String processStatusId;
	private int status;
}
