package nts.uk.ctx.hr.develop.app.sysoperationset.eventmanage.command;

import lombok.Data;

@Data
public class EventOperationCmd {
	// イベントID
	private int eventId;
	// イベントを使用する
	private int useEvent;
}
