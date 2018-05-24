package nts.uk.ctx.at.record.app.find.remainingnumber.dayoffmanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.ComDayOffManaDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DayOffManagementFinder {
	
	@Inject
	private ComDayOffManaDataRepository comDayOffManaDataRepository;
	
	
	public List<DayOffManagementDto> getBySidWithReDay(String leaveId, String employeeId) {
		List<DayOffManagementDto> resultDaysOffMana = new ArrayList<>();
		List<DayOffManagementDto> resultDayFreeMana = new ArrayList<>();
		List<DayOffManagementDto> results = new ArrayList<>();
		String companyId = AppContexts.user().companyId();
		List<CompensatoryDayOffManaData> daysFreeOffMana = new ArrayList<>();
		daysFreeOffMana = comDayOffManaDataRepository.getBySidWithReDay(companyId, employeeId);
		List<CompensatoryDayOffManaData> daysOffMana = new ArrayList<>();
		daysOffMana = comDayOffManaDataRepository.getBySidComDayOffIdWithReDay(companyId, employeeId, leaveId);
		resultDayFreeMana = daysFreeOffMana.stream().map(p -> new DayOffManagementDto(p.getDayOffDate().getDayoffDate().get(),p.getRequireDays().v().toString(),false,p.getComDayOffID())).collect(Collectors.toList());
		resultDaysOffMana = daysOffMana.stream().map(p -> new DayOffManagementDto(p.getDayOffDate().getDayoffDate().get(),p.getRequireDays().v().toString(),true,p.getComDayOffID())).collect(Collectors.toList());
		results.addAll(resultDayFreeMana);
		results.addAll(resultDaysOffMana);
		return results;
	}
	
}
