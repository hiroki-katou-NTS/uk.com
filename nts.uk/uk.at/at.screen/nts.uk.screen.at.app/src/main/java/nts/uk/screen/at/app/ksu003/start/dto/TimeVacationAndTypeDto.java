package nts.uk.screen.at.app.ksu003.start.dto;

import lombok.Value;

@Value
public class TimeVacationAndTypeDto {
	//時間休暇種類 : TimeVacationType
	private int typeVacation; 
	//時間休暇 
	private TimeVacationDto timeVacation; 

}
