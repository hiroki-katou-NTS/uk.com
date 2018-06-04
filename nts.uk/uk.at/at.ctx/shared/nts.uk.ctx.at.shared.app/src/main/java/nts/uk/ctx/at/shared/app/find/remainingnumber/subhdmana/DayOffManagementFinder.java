package nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.DayOffManagementDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.DayOffResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.ComDayOffManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DayOffManagementFinder {
	
	@Inject
	private ComDayOffManaDataRepository comDayOffManaDataRepository;
	
	
	public DayOffResult getBySidWithReDay(String leaveId, String employeeId) {
		List<DayOffManagementDto> resultDaysOffMana = new ArrayList<>();
		List<DayOffManagementDto> resultDayFreeMana = new ArrayList<>();
		List<DayOffManagementDto> dayOffAll = new ArrayList<>();
		DayOffResult dayOffResult = new DayOffResult();
		String companyId = AppContexts.user().companyId();
		List<CompensatoryDayOffManaData> daysFreeOffMana = new ArrayList<>();
		daysFreeOffMana = comDayOffManaDataRepository.getByReDay(companyId, employeeId);
		List<CompensatoryDayOffManaData> daysOffMana = new ArrayList<>();
		daysOffMana = comDayOffManaDataRepository.getBySidComDayOffIdWithReDay(companyId, employeeId, leaveId);
		resultDayFreeMana = daysFreeOffMana.stream().map(p -> new DayOffManagementDto(p.getDayOffDate().getDayoffDate().orElse(null),p.getRequireDays().v().toString(),false,p.getComDayOffID())).collect(Collectors.toList());
		resultDaysOffMana = daysOffMana.stream().map(p -> new DayOffManagementDto(p.getDayOffDate().getDayoffDate().orElse(null),p.getRequireDays().v().toString(),true,p.getComDayOffID())).collect(Collectors.toList());
		dayOffAll.addAll(resultDaysOffMana);
		dayOffAll.addAll(resultDayFreeMana);
		dayOffResult.setListDayOff(dayOffAll);
		if(dayOffAll.isEmpty()) {
			dayOffResult.setErrorCode("Msg_1073");
		}
		return dayOffResult;
	}
	
}
