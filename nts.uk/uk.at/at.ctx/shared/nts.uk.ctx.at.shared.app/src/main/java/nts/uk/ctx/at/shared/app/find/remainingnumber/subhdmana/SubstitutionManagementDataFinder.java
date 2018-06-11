package nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.ExtraHolidayManagementDataDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.SubDataSearchConditionDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.SubstituteDataManagementDto;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.service.ExtraHolidayManagementOutput;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.service.ExtraHolidayManagementService;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.service.SubstituteManagementOutput;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.service.SubstitutionManagementService;

@Stateless
public class SubstitutionManagementDataFinder {
	
	@Inject
	private SubstitutionManagementService substitutionManagementService;
	
	@Inject
	private ExtraHolidayManagementService extraHolidayManagementService;
	
	public SubstituteDataManagementDto getSubstituteManagementData(SubDataSearchConditionDto dto){
		SubstituteManagementOutput subDataOutput = substitutionManagementService.activationProcess(dto.getStartDate(), dto.getEndDate());
		return SubstituteDataManagementDto.convertToDto(subDataOutput);
	}
	
	public ExtraHolidayManagementDataDto getExtraHolidayManagementData(SubDataSearchConditionDto dto){
		ExtraHolidayManagementOutput extraHolidayOutput = extraHolidayManagementService.dataExtractionProcessing(dto.getSearchMode(), dto.getEmployeeId(), dto.getStartDate(), dto.getEndDate());
		return ExtraHolidayManagementDataDto.convertToDto(extraHolidayOutput);
	}
}
