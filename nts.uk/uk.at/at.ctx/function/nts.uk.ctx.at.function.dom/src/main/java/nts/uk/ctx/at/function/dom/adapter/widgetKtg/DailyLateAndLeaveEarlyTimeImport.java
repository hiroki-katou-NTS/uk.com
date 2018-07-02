package nts.uk.ctx.at.function.dom.adapter.widgetKtg;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Getter
@Setter
public class DailyLateAndLeaveEarlyTimeImport {

	private GeneralDate date;
	
	private boolean Late1;
	
	private boolean LeaveEarly1;
	
	private boolean Late2;
	
	private boolean LeaveEarly2;
}
