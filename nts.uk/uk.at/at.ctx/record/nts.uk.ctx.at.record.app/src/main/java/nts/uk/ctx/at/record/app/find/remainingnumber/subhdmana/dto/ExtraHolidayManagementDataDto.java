package nts.uk.ctx.at.record.app.find.remainingnumber.subhdmana.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SEmpHistoryImport;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveManagementData;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.service.ExtraHolidayManagementOutput;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;

@AllArgsConstructor
@Data
public class ExtraHolidayManagementDataDto {

	List<LeaveDataDto> listLeaveData;
	List<CompensatoryDataDto> listCompensatoryData;
	List<LeaveComDayOffManaDto> listLeaveComDayOffManagement;
	SEmpHistoryDto sEmpHistoryImport;
	ClosureEmploymentDto closureEmploy;

	public static ExtraHolidayManagementDataDto convertToDto(ExtraHolidayManagementOutput extraHolidayManagementOutput){
		// From domain
		List<LeaveManagementData> listLeaveData = extraHolidayManagementOutput.getListLeaveData();
		List<CompensatoryDayOffManaData> listCompensatoryData = extraHolidayManagementOutput.getListCompensatoryData();
		List<LeaveComDayOffManagement> listLeaveComDayOffManagement = extraHolidayManagementOutput.getListLeaveComDayOffManagement();
		SEmpHistoryImport sEmpHistoryImport = extraHolidayManagementOutput.getSEmpHistoryImport();
		ClosureEmployment closureEmploy = extraHolidayManagementOutput.getClosureEmploy();
		// Dto
		List<LeaveDataDto> listLeaveDataDto = null;
		List<CompensatoryDataDto> listCompensatoryDataDto = null;
		List<LeaveComDayOffManaDto> listLeaveComDayOffManagementDto = null;
		SEmpHistoryDto sEmpHistoryDto = null;
		ClosureEmploymentDto closureEmployDto = null;
		
		if (!listLeaveData.isEmpty()){
			listLeaveDataDto = extraHolidayManagementOutput.getListLeaveData().stream().map(x ->{
				return LeaveDataDto.convertToDto(x);
			}).collect(Collectors.toList());
		}
		if (!listCompensatoryData.isEmpty()){
			listCompensatoryDataDto = listCompensatoryData.stream().map(x ->{
				return CompensatoryDataDto.convertToDto(x);
			}).collect(Collectors.toList());
		}
		if (!listLeaveComDayOffManagement.isEmpty()){
			listLeaveComDayOffManagementDto = listLeaveComDayOffManagement.stream().map(x ->{
				return LeaveComDayOffManaDto.convertToDto(x);
			}).collect(Collectors.toList());
		}
		if (!Objects.isNull(sEmpHistoryImport)){
			sEmpHistoryDto = SEmpHistoryDto.convertToDto(sEmpHistoryImport);
		}
		if (!Objects.isNull(closureEmploy)){
			closureEmployDto = ClosureEmploymentDto.convertToDto(closureEmploy);
		}
		return new ExtraHolidayManagementDataDto(listLeaveDataDto, listCompensatoryDataDto, listLeaveComDayOffManagementDto, sEmpHistoryDto, closureEmployDto);
	}
}
