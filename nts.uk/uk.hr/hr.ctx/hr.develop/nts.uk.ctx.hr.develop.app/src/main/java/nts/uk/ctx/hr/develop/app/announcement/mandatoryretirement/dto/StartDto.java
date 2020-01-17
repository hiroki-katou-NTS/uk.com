package nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StartDto {	
	
	private RelateMasterDto relateMaster;
	
	private MandatoryRetirementRegulationDto mandatoryRetirement;
}
