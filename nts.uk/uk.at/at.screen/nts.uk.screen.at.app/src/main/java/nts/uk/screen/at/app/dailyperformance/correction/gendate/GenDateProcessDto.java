package nts.uk.screen.at.app.dailyperformance.correction.gendate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DataSessionDto;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GenDateProcessDto {
	GenDateDto genDateDto;
	
	DataSessionDto dataSessionDto;
}
