package nts.uk.screen.at.app.dailyperformance.correction.dto.remainnumber;
/**
 * 
 * @author HungTT - 年休残数
 *
 */

import lombok.Value;

@Value
public class AnnualLeaveDto {

	private boolean displayAnnualDay;
	
	private boolean displayAnnualTime;
	
	private Integer annualDay;
	
	private Integer annualTime;
	
}
