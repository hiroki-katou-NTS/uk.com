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
	
	
	public List<DayOffManagementDto> getBySidWithReDay(String leaveId) {
		List<DayOffManagementDto> resultDaysOffMana = new ArrayList<>();
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		List<CompensatoryDayOffManaData> daysFreeOffMana = new ArrayList<>();
		daysFreeOffMana = comDayOffManaDataRepository.getBySidWithReDay(companyId, employeeId);
		List<CompensatoryDayOffManaData> daysOffMana = new ArrayList<>();
		daysOffMana = comDayOffManaDataRepository.getBySidComDayOffIdWithReDay(companyId, employeeId, leaveId);
		resultDaysOffMana = daysFreeOffMana.stream().map(p -> new DayOffManagementDto(p.getDayOffDate().toString(),p.getRemainDays().toString(),false)).collect(Collectors.toList());
		resultDaysOffMana = daysOffMana.stream().map(p -> new DayOffManagementDto(p.getDayOffDate().toString(),p.getRemainDays().toString(),true)).collect(Collectors.toList());
		return resultDaysOffMana;
	}
	
}
