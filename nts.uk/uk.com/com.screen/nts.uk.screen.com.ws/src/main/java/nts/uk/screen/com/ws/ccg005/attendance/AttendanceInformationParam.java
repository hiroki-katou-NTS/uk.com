package nts.uk.screen.com.ws.ccg005.attendance;

import java.util.List;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class AttendanceInformationParam {
	private List<String> sids;
	
	private List<String> pids;
	
	private GeneralDate baseDate;
	
	private boolean emojiUsage;
}
