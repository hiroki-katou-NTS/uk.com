package nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SWkpHistImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.service.ExtraHolidayManagementOutput;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.service.SubstituteManagementOutput;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

@AllArgsConstructor
@Data
public class SubstituteDataManagementDto {
	public SWkpHistDto wkHistory;
	public ExtraHolidayManagementDataDto extraHolidayManagementDataDto;
	
	public static SubstituteDataManagementDto convertToDto(SubstituteManagementOutput subDataOutput){
		SWkpHistImport sWkpHistImport = subDataOutput.getSWkpHistImport();
		SWkpHistDto sWkpHist = null;
		if (!Objects.isNull(sWkpHistImport)){
			sWkpHist = SWkpHistDto.convertToDto(sWkpHistImport);
		}
		ExtraHolidayManagementOutput extraHolidayManagementOutput = subDataOutput.getExtraHolidayManagementOutput();
		
		return new SubstituteDataManagementDto(sWkpHist, ExtraHolidayManagementDataDto.convertToDto(extraHolidayManagementOutput));
	}
}
