package nts.uk.screen.at.app.dailymodify.mobile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DataSessionDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DPMobileAdUpParamDto {

	DPMobileAdUpParam dpMobileAdUpParam;
	
	DataSessionDto dataSessionDto;
}
