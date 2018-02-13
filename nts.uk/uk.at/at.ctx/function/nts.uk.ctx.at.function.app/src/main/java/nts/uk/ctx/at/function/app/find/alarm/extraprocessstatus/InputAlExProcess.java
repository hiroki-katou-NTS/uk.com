package nts.uk.ctx.at.function.app.find.alarm.extraprocessstatus;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class InputAlExProcess {
	private GeneralDate startDate;
	/** 開始時刻 */
	private int startTime;
}
