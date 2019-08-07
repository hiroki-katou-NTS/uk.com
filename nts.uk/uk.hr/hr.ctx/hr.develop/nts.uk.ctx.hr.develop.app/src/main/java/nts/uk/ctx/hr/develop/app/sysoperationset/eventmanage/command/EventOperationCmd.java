package nts.uk.ctx.hr.develop.app.sysoperationset.eventmanage.command;

import java.math.BigInteger;

import lombok.Data;

@Data
public class EventOperationCmd {
	// イベントID
	private int eventId;
	// イベントを使用する
	private int useEvent;
	// 会社ID
	private BigInteger ccd;
}
