package nts.uk.ctx.hr.develop.app.jmm018retire.dto;

import lombok.Data;

@Data
public class startDto {	
	private RelateMasterDto relateMaster;
	
	private MandatoryRetirementDto mandatoryRetirement;

	public startDto(RelateMasterDto relateMaster, MandatoryRetirementDto mandatoryRetirement) {
		super();
		this.relateMaster = relateMaster;
		this.mandatoryRetirement = mandatoryRetirement;
	}

}
