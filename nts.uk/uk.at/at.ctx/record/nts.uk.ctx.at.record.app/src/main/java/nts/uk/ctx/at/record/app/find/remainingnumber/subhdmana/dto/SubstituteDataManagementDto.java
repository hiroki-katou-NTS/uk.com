package nts.uk.ctx.at.record.app.find.remainingnumber.subhdmana.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SWkpHistImport;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.service.output.ExtraHolidayManagementOutput;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.service.output.SubstituteManagementOutput;

@AllArgsConstructor
@Data
public class SubstituteDataManagementDto {
	public SWkpHistDto wkHistory;
	public ExtraHolidayManagementDataDto extraHolidayManagementDataDto;
	
	public static SubstituteDataManagementDto convertToDto(SubstituteManagementOutput subDataOutput){
		SWkpHistImport sWkpHistImport = subDataOutput.getSWkpHistImport();
		ExtraHolidayManagementOutput extraHolidayManagementOutput = subDataOutput.getExtraHolidayManagementOutput();
		return new SubstituteDataManagementDto(SWkpHistDto.convertToDto(sWkpHistImport), ExtraHolidayManagementDataDto.convertToDto(extraHolidayManagementOutput));
	}
}
