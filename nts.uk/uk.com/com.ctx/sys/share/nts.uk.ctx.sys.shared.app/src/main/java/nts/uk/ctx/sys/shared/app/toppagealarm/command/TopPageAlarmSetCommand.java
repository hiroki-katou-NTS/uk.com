package nts.uk.ctx.sys.shared.app.toppagealarm.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopPageAlarmSetCommand {
	private int alarmCategory;
	private int useAtr;
}
