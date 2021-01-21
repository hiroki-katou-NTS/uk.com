package nts.uk.screen.com.ws.ccg005.display.information;

import java.util.List;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class DisplayInfoAfterSelectParam {
	
	private List<String> wkspIds;
	
	private GeneralDate baseDate;
	
	private boolean emojiUsage;
}
