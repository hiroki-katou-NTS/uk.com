package nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.DayOffManagementDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.DayOffResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.ComDayOffManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DayOffManagementFinder {
	
	@Inject
	private ComDayOffManaDataRepository comDayOffManaDataRepository;
	
	@Inject
	private LeaveComDayOffManaRepository leaveComDayOffManaRepository;
	
	
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
		resultDayFreeMana = daysFreeOffMana.stream().map(p -> new DayOffManagementDto(p.getDayOffDate().getDayoffDate().orElse(null),p.getRemainDays().v().toString(),false,p.getComDayOffID())).collect(Collectors.toList());
		resultDaysOffMana = daysOffMana.stream().map(p -> new DayOffManagementDto(p.getDayOffDate().getDayoffDate().orElse(null),p.getRemainDays().v().toString(),true,p.getComDayOffID())).collect(Collectors.toList());
		List<DayOffManagementDto> dayOffManaRemove = new ArrayList<>();
		for (DayOffManagementDto dayOffMana : resultDaysOffMana) {
			for (DayOffManagementDto dayOffManaFree : resultDayFreeMana) {
				if(dayOffMana.getComDayOffId().equals(dayOffManaFree.getComDayOffId())) {
					dayOffManaRemove.add(dayOffManaFree);
				}
			}
			List<LeaveComDayOffManagement> leaveComDayOffManagement = leaveComDayOffManaRepository.getBycomDayOffID(dayOffMana.getComDayOffId(), dayOffMana.getDateHoliday());
			if(dayOffMana.getNumberDay().equals("0.0") && leaveComDayOffManagement.size() == 2) {
				dayOffMana.setNumberDay("0.5");
			} else if(dayOffMana.getNumberDay().equals("0.0") && leaveComDayOffManagement.size() == 1) {
				dayOffMana.setNumberDay(leaveComDayOffManagement.get(0).getAssocialInfo().getDayNumberUsed().v().toString());
			}
			
		}
		resultDayFreeMana.removeAll(dayOffManaRemove);
		dayOffAll.addAll(resultDaysOffMana);
		dayOffAll.addAll(resultDayFreeMana);
		Collections.sort(dayOffAll, new Comparator<DayOffManagementDto>() {
			  public int compare(DayOffManagementDto o1, DayOffManagementDto o2) {
				  if (o1.getDateHoliday() == null) {
				        return (o2.getDateHoliday() == null) ? 0 : -1;
				    }
				    if (o2.getDateHoliday() == null) {
				        return 1;
				    }
			      return o1.getDateHoliday().compareTo(o2.getDateHoliday());
			  }
		});
		dayOffResult.setListDayOff(dayOffAll);
		if(dayOffAll.isEmpty()) {
			dayOffResult.setErrorCode("Msg_1073");
		}
		return dayOffResult;
	}
	
}
