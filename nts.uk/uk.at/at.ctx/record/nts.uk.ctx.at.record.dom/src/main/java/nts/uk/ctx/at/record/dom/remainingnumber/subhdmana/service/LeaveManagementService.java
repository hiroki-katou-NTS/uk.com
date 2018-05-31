package nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.remainingnumber.base.TargetSelectionAtr;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveComDayOffManaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveManaData;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeavesManaData;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveManaDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveManagementData;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class LeaveManagementService {

	
	@Inject
	private LeaveComDayOffManaRepository leaveComDayOffManaRepository;
	
	@Inject
	private LeaveManaDataRepository leaveManaDataRepository;
	
	public static final String ONE_DAY = "1.0";
	public static final String HALF_DAY = "0.5";
	
	public List<String> updateDayOff(LeaveManaData leaveManagementData) {
		
		List<String> response = new ArrayList<>();
		String companyId = AppContexts.user().companyId();

		if (leaveManagementData.getLeaveMana().size() == 0) {
			response.add("Msg_736");
		} else if (leaveManagementData.getLeaveMana().size() == 1
				&& !leaveManagementData.getLeaveMana().get(0).getRemainDays().equals(leaveManagementData.getNumberDayParam())) {
			response.add("Msg_734");
		} else if (leaveManagementData.getLeaveMana().size() == 2) {
			if(leaveManagementData.getLeaveMana().get(0).getRemainDays().equals(HALF_DAY)) {
				if(!leaveManagementData.getLeaveMana().get(1).getRemainDays().equals(HALF_DAY)) {
					response.add("Msg_734");
				} else {
					if(leaveManagementData.getNumberDayParam().equals(ONE_DAY)) {
						response.add("Msg_734");
					}
				}
			} else if(leaveManagementData.getLeaveMana().get(0).getRemainDays().equals(ONE_DAY)) {
				response.add("Msg_743");
			}
			
			/*if(leaveManagementData.getNumberDayParam().equals(HALF_DAY)) {
				response.add("Msg_734");
			}
			
			if (leaveManagementData.getLeaveMana().get(0).getRemainDays().equals(ONE_DAY)
					|| leaveManagementData.getLeaveMana().get(1).getRemainDays().equals(ONE_DAY)) {

				response.add("Msg_743");

			} else if (leaveManagementData.getLeaveMana().get(0).getRemainDays().equals(HALF_DAY)
					|| leaveManagementData.getLeaveMana().get(1).getRemainDays().equals(HALF_DAY)) {
				if (!leaveManagementData.getLeaveMana().get(0).getRemainDays().equals(HALF_DAY)
						|| !leaveManagementData.getLeaveMana().get(1).getRemainDays().equals(HALF_DAY)) {
					response.add("Msg_734");
				}
			}*/
		} else if (leaveManagementData.getLeaveMana().size() >= 3) {
			response.add("Msg_743");
		}

		if (response.isEmpty()) {

			List<LeaveComDayOffManagement> leavesComDay = leaveComDayOffManaRepository
					.getBycomDayOffID(leaveManagementData.getComDayOffID());

			
			List<LeaveManagementData> leaveManaUpdate = this.leaveManaDataRepository.getByComDayOffId(companyId, leaveManagementData.getEmployeeId(),
					leaveManagementData.getComDayOffID());
			
			List<String> updateUnUsedDay = new ArrayList<>();
			for (int i = 0; i < leaveManaUpdate.size(); i++) {
				if(leaveManaUpdate.get(i).getOccurredDays().v() != Double.parseDouble(leaveManagementData.getNumberDayParam())) {
					updateUnUsedDay.add(leaveManaUpdate.get(i).getID());
					leaveManaUpdate.remove(i);
				}
			}
			// update unUsedDay when not used 
			if(!updateUnUsedDay.isEmpty()) {
				leaveManaDataRepository.updateUnUseDayLeaveId(updateUnUsedDay);
			}
			
			List<String> currentLeaveMana = leaveManaUpdate.stream().map(LeaveManagementData::getID)
					.collect(Collectors.toList());
			// update Sub by current leave
			if (!currentLeaveMana.isEmpty()) {
				leaveManaDataRepository.updateSubByLeaveId(currentLeaveMana);

			}
			
			// delete List LeaveComDayOff
			if (leavesComDay.size() >= 1) {
				leaveComDayOffManaRepository.deleteByComDayOffID(leaveManagementData.getComDayOffID());
			}

			// insert List LeaveComDayOff
			List<LeavesManaData> leaveMana = leaveManagementData.getLeaveMana();
			List<LeaveComDayOffManagement> entitiesLeave = leaveMana.stream()
					.map(item -> new LeaveComDayOffManagement(item.getLeaveManaID(),
							leaveManagementData.getComDayOffID(), new BigDecimal(item.getRemainDays()), 0,
							TargetSelectionAtr.MANUAL.value))
					.collect(Collectors.toList());
			leaveComDayOffManaRepository.insertAll(entitiesLeave);

			// update Sub by new Leave
			List<String> listId = leaveMana.stream().map(LeavesManaData::getLeaveManaID)
					.collect(Collectors.toList());
			if (!listId.isEmpty()) {
				leaveManaDataRepository.updateByLeaveIds(listId);
			}
			response.add("Msg_15");
		}
		
		return response;
		
	}
	
}
