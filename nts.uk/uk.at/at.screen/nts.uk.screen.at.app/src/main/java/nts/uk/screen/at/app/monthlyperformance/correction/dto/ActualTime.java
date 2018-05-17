package nts.uk.screen.at.app.monthlyperformance.correction.dto;

import lombok.Value;
import nts.arc.time.GeneralDate;
/**
 * 実績期間
 */
@Value
public class ActualTime {
	
	private GeneralDate startDate;
	private GeneralDate endDate;
	
}
