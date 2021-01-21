package nts.uk.screen.com.ws.ccg005.attendance;

import java.util.List;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.screen.com.app.find.ccg005.attendance.information.EmpIdParam;

@Getter
public class AttendanceInformationParam {
	
	private List<EmpIdParam> empIds;

	private GeneralDate baseDate;
	
	private boolean emojiUsage;
}
