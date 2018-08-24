package nts.uk.screen.at.app.dailyperformance.correction.dto.remainnumber;

import lombok.Value;

/**
 * 
 * @author HungTT - 代休残数
 *
 */

@Value
public class CompensatoryHolidayDto {

	private boolean displayCompensatoryDay;
	
	private boolean displayCompensatoryTime;
	
	private Integer compensatoryDay;
	
	private Integer compensatoryTime;
	
}
