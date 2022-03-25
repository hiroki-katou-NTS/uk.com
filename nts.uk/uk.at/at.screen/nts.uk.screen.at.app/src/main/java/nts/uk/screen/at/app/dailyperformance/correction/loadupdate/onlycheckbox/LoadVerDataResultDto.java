package nts.uk.screen.at.app.dailyperformance.correction.loadupdate.onlycheckbox;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DataSessionDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoadVerDataResultDto {

	LoadVerDataResult loadVerDataResult;
	
	DataSessionDto dataSessionDto;
}
