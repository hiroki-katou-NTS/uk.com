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
	public String leaveSettingExpiredDate;
	public String compenSettingEmpExpiredDate;
	
	public static SubstituteDataManagementDto convertToDto(SubstituteManagementOutput subDataOutput){
		SWkpHistImport sWkpHistImport = subDataOutput.getSWkpHistImport();
		SWkpHistDto sWkpHist = null;
		String leaveSettingExpiredDate = "";
		String compenSettingEmpExpiredDate = "";
		if (!Objects.isNull(sWkpHistImport)){
			sWkpHist = SWkpHistDto.convertToDto(sWkpHistImport);
		}
		ExtraHolidayManagementOutput extraHolidayManagementOutput = subDataOutput.getExtraHolidayManagementOutput();
		if (!Objects.isNull(subDataOutput.getCompensatoryLeaveEmSetting())){
			if (subDataOutput.getCompensatoryLeaveEmSetting().getIsManaged() == ManageDistinct.YES){
				leaveSettingExpiredDate = subDataOutput.getCompensatoryLeaveEmSetting().getCompensatoryAcquisitionUse().getExpirationTime().name();
			} else if (!Objects.isNull(subDataOutput.getCompensatoryLeaveComSetting())){
					if (subDataOutput.getCompensatoryLeaveComSetting().isManaged())
					compenSettingEmpExpiredDate = subDataOutput.getCompensatoryLeaveComSetting().getCompensatoryAcquisitionUse().getExpirationTime().description;
			}
		} else if (!Objects.isNull(subDataOutput.getCompensatoryLeaveComSetting())){
			if (subDataOutput.getCompensatoryLeaveComSetting().isManaged())
			compenSettingEmpExpiredDate = subDataOutput.getCompensatoryLeaveComSetting().getCompensatoryAcquisitionUse().getExpirationTime().description;
		}
		return new SubstituteDataManagementDto(sWkpHist, ExtraHolidayManagementDataDto.convertToDto(extraHolidayManagementOutput), leaveSettingExpiredDate, compenSettingEmpExpiredDate);
	}
}
