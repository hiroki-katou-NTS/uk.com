package nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto;

import lombok.Data;

@Data
public class StartDto {	
	private RelateMasterDto relateMaster;
	
	private MandatoryRetirementRegulationDto mandatoryRetirement;

	public StartDto(RelateMasterDto relateMaster, MandatoryRetirementRegulationDto mandatoryRetirement) {
		super();
		this.relateMaster = relateMaster;
		this.mandatoryRetirement = mandatoryRetirement;
	}

}
