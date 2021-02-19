package nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.LeaveManaDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.LeaveManaResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class LeaveManaFinder {
	
	
	@Inject
	private LeaveManaDataRepository leaveManaDataRepository;
	
	@Inject
	private LeaveComDayOffManaRepository leaveComDayOffManaRepository;
	
	public LeaveManaResult getByComDayOffId(String comDayOffID, String employeeId) {
		List<LeaveManaDto>  resultLeaveMana = new ArrayList<>();
		List<LeaveManaDto> resultLeaveFreeMana = new ArrayList<>();
		List<LeaveManaDto> allLeaveMana = new ArrayList<>();
		LeaveManaResult result = new LeaveManaResult();
		String companyId = AppContexts.user().companyId();
		List<LeaveManagementData> leaveManaFree = new ArrayList<>();
		leaveManaFree = leaveManaDataRepository.getBySidWithsubHDAtr(companyId, employeeId, DigestionAtr.UNUSED.value);
		List<LeaveManagementData> leaveMana = new ArrayList<>();
		leaveMana = leaveManaDataRepository.getByComDayOffId(companyId, employeeId, comDayOffID);
		resultLeaveFreeMana = leaveManaFree.stream().map(p -> new LeaveManaDto(p.getComDayOffDate().getDayoffDate().orElse(null),p.getUnUsedDays().v().toString(), false,p.getID())).collect(Collectors.toList());
		resultLeaveMana = leaveMana.stream().map(p -> new LeaveManaDto(p.getComDayOffDate().getDayoffDate().orElse(null),p.getUnUsedDays().v().toString(), true,p.getID())).collect(Collectors.toList());
		List<LeaveManaDto> leaveDataRemove = new ArrayList<>();
		for (LeaveManaDto leaveManagementData : resultLeaveMana) {
			for (LeaveManaDto leaveManaDataFree : resultLeaveFreeMana) {
				if(leaveManagementData.getLeaveManaID().equals(leaveManaDataFree.getLeaveManaID())) {
					leaveDataRemove.add(leaveManaDataFree);
				}
			}
			List<LeaveComDayOffManagement> leaveComDayOffManagement = leaveComDayOffManaRepository.getByLeaveID(leaveManagementData.getLeaveManaID(), leaveManagementData.getDateHoliday());
			
			if(leaveComDayOffManagement.size() == 2 && leaveManagementData.getNumberDay().equals("0.0")) {
				leaveManagementData.setNumberDay("0.5");
			} else if (leaveComDayOffManagement.size() == 1 && leaveManagementData.getNumberDay().equals("0.0")) {
				leaveManagementData.setNumberDay(leaveComDayOffManagement.get(0).getAssocialInfo().getDateOfUse().toString());
			}
		}
		resultLeaveFreeMana.removeAll(leaveDataRemove);
		allLeaveMana.addAll(resultLeaveMana);
		allLeaveMana.addAll(resultLeaveFreeMana);
		Collections.sort(allLeaveMana, new Comparator<LeaveManaDto>() {
			  public int compare(LeaveManaDto o1, LeaveManaDto o2) {
				  if (o1.getDateHoliday() == null) {
				        return (o2.getDateHoliday() == null) ? 0 : -1;
				    }
				    if (o2.getDateHoliday() == null) {
				        return 1;
				    }
			      return o1.getDateHoliday().compareTo(o2.getDateHoliday());
			  }
		});
		result.setListLeaveMana(allLeaveMana);
		
		if(allLeaveMana.isEmpty()) {
			result.setErrorCode("Msg_1074");
		}
		return result;
	}
	
}
