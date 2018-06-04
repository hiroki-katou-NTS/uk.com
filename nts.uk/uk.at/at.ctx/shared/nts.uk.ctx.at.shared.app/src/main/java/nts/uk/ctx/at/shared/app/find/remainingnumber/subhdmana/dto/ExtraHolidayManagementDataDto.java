package nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SEmpHistoryImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.service.ExtraHolidayManagementOutput;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;

@AllArgsConstructor
@Data
public class ExtraHolidayManagementDataDto {

	List<DataExtractDto> extraData;
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
		List<DataExtractDto> listExtraData = new ArrayList<>();
		SEmpHistoryDto sEmpHistoryDto = null;
		ClosureEmploymentDto closureEmployDto = null;
		for(LeaveManagementData data : listLeaveData){
			DataExtractDto dto = DataExtractDto.convertFromLeaveDataToDto(0, data);
			if(listLeaveComDayOffManagement.stream().filter(o -> o.getLeaveID().equals(data.getID())).findFirst().isPresent()){
				dto.setLinked(1);
			} else {
				dto.setLinked(0);
			}
			if (data.getComDayOffDate().getDayoffDate().get().after(GeneralDate.today())){
				dto.setExpired(data.getUnUsedDays().v());
			} else {
				dto.setRemain(data.getUnUsedDays().v());
			}
			listExtraData.add(dto);
		}
		for(CompensatoryDayOffManaData data : listCompensatoryData){
			DataExtractDto dto = DataExtractDto.convertFromCompensatoryDataToDto(1, data);
			if(listLeaveComDayOffManagement.stream().filter(o -> o.getComDayOffID().equals(data.getComDayOffID())).findFirst().isPresent()){
				dto.setLinked(1);
			} else {
				dto.setLinked(0);
			}
			if (data.getDayOffDate().getDayoffDate().get().after(GeneralDate.today())){
				dto.setExpired(data.getRemainDays().v());
			} else {
				dto.setExpired(data.getRemainDays().v());
			}
			
			listExtraData.add(dto);
		}
		listExtraData.sort((m1, m2)->{
			return m1.getDayOffDate().before(m2.getDayOffDate()) ? -1 : 1;
		});
		
		if (!Objects.isNull(sEmpHistoryImport)){
			sEmpHistoryDto = SEmpHistoryDto.convertToDto(sEmpHistoryImport);
		}
		if (!Objects.isNull(closureEmploy)){
			closureEmployDto = ClosureEmploymentDto.convertToDto(closureEmploy);
		}
		return new ExtraHolidayManagementDataDto(listExtraData, sEmpHistoryDto, closureEmployDto);
	}
}
