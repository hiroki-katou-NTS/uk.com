package nts.uk.screen.at.app.dailyperformance.correction.loadupdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DataSessionDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DPPramLoadRowDto {
	
	DPPramLoadRow dpPramLoadRow;
	
	DataSessionDto dataSessionDto;
}
