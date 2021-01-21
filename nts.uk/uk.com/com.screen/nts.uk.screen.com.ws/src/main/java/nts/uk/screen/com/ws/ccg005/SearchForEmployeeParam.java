package nts.uk.screen.com.ws.ccg005;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class SearchForEmployeeParam {
	
	private String keyWorks;
	
	private GeneralDate baseDate;
	
	private boolean emojiUsage;
}
