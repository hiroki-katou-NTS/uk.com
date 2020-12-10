package nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SEmpHistoryImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.service.ExtraHolidayManagementOutput;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.shr.com.context.AppContexts;

@AllArgsConstructor
@Data
public class ExtraHolidayManagementDataDto {

	List<DataExtractDto> extraData;
	SEmpHistoryDto sEmpHistoryImport;
	ClosureEmploymentDto closureEmploy;
	String empSettingExpiredDate;
	String companySettingExpiredDate;
	SWkpHistDto wkHistory;
	String employeeCode;
	String employeeName;
	public static ExtraHolidayManagementDataDto convertToDto(ExtraHolidayManagementOutput extraHolidayManagementOutput){
		String cid = AppContexts.user().companyId();
		// From domain
		List<LeaveManagementData> listLeaveData = extraHolidayManagementOutput.getListLeaveData();
		List<CompensatoryDayOffManaData> listCompensatoryData = extraHolidayManagementOutput.getListCompensatoryData();
		List<LeaveComDayOffManagement> listLeaveComDayOffManagement = extraHolidayManagementOutput.getListLeaveComDayOffManagement();
		SEmpHistoryImport sEmpHistoryImport = extraHolidayManagementOutput.getSEmpHistoryImport();
		ClosureEmployment closureEmploy = extraHolidayManagementOutput.getClosureEmploy();
		// Dto
		List<DataExtractDto> listExtraData = new ArrayList<>();
		SEmpHistoryDto sEmpHistoryDto = null;
		ClosureEmploymentDto closureEmployDto = null;
		String empSettingExpiredDate = "";
		String companySettingExpiredDate = "";
		for(LeaveManagementData data : listLeaveData){
			DataExtractDto dto = DataExtractDto.convertFromLeaveDataToDto(0, data);
			//	if(listLeaveComDayOffManagement.stream().filter(o -> o.getLeaveID().equals(data.getID())).findFirst().isPresent()){
			//		dto.setLinked(1);
			//	} else {
			//		dto.setLinked(0);
			//	}
			if (GeneralDate.today().after(data.getExpiredDate()) || data.getSubHDAtr() == DigestionAtr.EXPIRED){
				dto.setExpired(data.getUnUsedDays().v());
			}else {
				dto.setRemain(data.getUnUsedDays().v());
			}
			listExtraData.add(dto);
		}
		for(CompensatoryDayOffManaData data : listCompensatoryData){
			DataExtractDto dto = DataExtractDto.convertFromCompensatoryDataToDto(1, data);
			//	if(listLeaveComDayOffManagement.stream().filter(o -> o.getComDayOffID().equals(data.getComDayOffID())).findFirst().isPresent()){
			//		dto.setLinked(1);
			//	} else {
			//		dto.setLinked(0);
			//	}
			dto.setRemain(data.getRemainDays().v());
			listExtraData.add(dto);
		}
		if (!Objects.isNull(extraHolidayManagementOutput.getSEmpHistoryImport())){
			String sid = extraHolidayManagementOutput.getSEmpHistoryImport().getEmployeeId();
			listExtraData = listExtraData.stream().filter(o -> o.getCID().equals(cid) && o.getSID().equals(sid)).collect(Collectors.toList());
		}
		listExtraData.sort((m1, m2)->{
			if (Objects.isNull(m1.getDayOffDate()) || Objects.isNull(m2.getDayOffDate())) return 1;
			else return m1.getDayOffDate().before(m2.getDayOffDate()) ? -1 : 1;
		});
		
		if (!Objects.isNull(sEmpHistoryImport)){
			sEmpHistoryDto = SEmpHistoryDto.convertToDto(sEmpHistoryImport);
		}
		if (!Objects.isNull(closureEmploy)){
			closureEmployDto = ClosureEmploymentDto.convertToDto(closureEmploy);
		}
		if (!Objects.isNull(extraHolidayManagementOutput.getCompensatoryLeaveEmSetting())){
			if (extraHolidayManagementOutput.getCompensatoryLeaveEmSetting().getIsManaged() == ManageDistinct.YES){
				empSettingExpiredDate = extraHolidayManagementOutput.getCompensatoryLeaveEmSetting().getCompensatoryAcquisitionUse().getExpirationTime().description;
			} else if (!Objects.isNull(extraHolidayManagementOutput.getCompensatoryLeaveComSetting())){
					if (extraHolidayManagementOutput.getCompensatoryLeaveComSetting().isManaged())
						companySettingExpiredDate = extraHolidayManagementOutput.getCompensatoryLeaveComSetting().getCompensatoryAcquisitionUse().getExpirationTime().description;
			}
		} else if (!Objects.isNull(extraHolidayManagementOutput.getCompensatoryLeaveComSetting())){
			if (extraHolidayManagementOutput.getCompensatoryLeaveComSetting().isManaged())
				companySettingExpiredDate = extraHolidayManagementOutput.getCompensatoryLeaveComSetting().getCompensatoryAcquisitionUse().getExpirationTime().description;
		}
		SWkpHistDto sWkpHist = null;
		if (!Objects.isNull(extraHolidayManagementOutput.getSWkpHistImport())){
			sWkpHist = SWkpHistDto.convertToDto(extraHolidayManagementOutput.getSWkpHistImport());
		}
		String employeeCode = "";
		String employeeName = "";
		if (!Objects.isNull(extraHolidayManagementOutput.getPersonEmpBasicInfoImport())){
			employeeCode = extraHolidayManagementOutput.getPersonEmpBasicInfoImport().getEmployeeCode();
			employeeName = extraHolidayManagementOutput.getPersonEmpBasicInfoImport().getBusinessName();
		}
		return new ExtraHolidayManagementDataDto(listExtraData, sEmpHistoryDto, closureEmployDto, empSettingExpiredDate, companySettingExpiredDate, sWkpHist, employeeCode, employeeName);
	}
}
