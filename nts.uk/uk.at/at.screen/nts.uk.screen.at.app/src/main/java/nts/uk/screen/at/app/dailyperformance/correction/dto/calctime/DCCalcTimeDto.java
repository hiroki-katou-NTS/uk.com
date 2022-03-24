package nts.uk.screen.at.app.dailyperformance.correction.dto.calctime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DataSessionDto;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DCCalcTimeDto {
	
	DCCalcTime dcCalcTime;
	
	DataSessionDto dataSessionDto; 
}
