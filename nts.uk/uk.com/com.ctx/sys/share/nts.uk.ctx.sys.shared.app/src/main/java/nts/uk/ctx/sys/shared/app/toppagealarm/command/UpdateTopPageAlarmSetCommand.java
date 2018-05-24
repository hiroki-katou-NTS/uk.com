package nts.uk.ctx.sys.shared.app.toppagealarm.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTopPageAlarmSetCommand {
	private int alarmCategory;
	private int useAtr;
}
