package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.Value;

/**
 * 
 * @author HungTT - 積休残数
 *
 */

@Value
public class ReserveLeaveDto {

	private boolean manageRemainNumber;
	
	private Double remainNumber; 
	
}
