package nts.uk.screen.at.ws.dailyperformance.correction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.layer.app.command.JavaTypeResult;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DataSessionDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReleaseClosureDto {
	JavaTypeResult<String> result;
	
	DataSessionDto dataSessionDto;
}
