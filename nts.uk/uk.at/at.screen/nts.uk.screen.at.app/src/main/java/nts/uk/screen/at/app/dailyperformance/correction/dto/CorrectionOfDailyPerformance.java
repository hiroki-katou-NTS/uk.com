package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.Data;

@Data
public class CorrectionOfDailyPerformance {
	
	private String cursorMovementDirection;
	
	private String displayFormat;
	
	private boolean display;
	
	private boolean displayItemNumberInGridHeader;
	
	private boolean displayThePersonalProfileColumn;
	
	private String previousDisplayItem;
}
