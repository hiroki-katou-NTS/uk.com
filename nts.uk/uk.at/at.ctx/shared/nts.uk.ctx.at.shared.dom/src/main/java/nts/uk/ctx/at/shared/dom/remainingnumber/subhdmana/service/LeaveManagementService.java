package nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.remainingnumber.base.TargetSelectionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.ComDayOffManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeavesManaData;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class LeaveManagementService {

	
	@Inject
	private LeaveComDayOffManaRepository leaveComDayOffManaRepository;
	
	@Inject
	private LeaveManaDataRepository leaveManaDataRepository;
	
	@Inject
	private ComDayOffManaDataRepository comDayOffManaDataRepository;
	
	public static final String ONE_DAY = "1.0";
	public static final String HALF_DAY = "0.5";
	
	public List<String> updateDayOff(LeaveManaData leaveManagementData) {
		
		List<String> response = new ArrayList<>();
		String companyId = AppContexts.user().companyId();

		if (leaveManagementData.getLeaveMana().size() == 0) {
			response.add("Msg_736");
		} else if (leaveManagementData.getLeaveMana().size() == 1
				&& Integer.parseInt(leaveManagementData.getLeaveMana().get(0).getRemainDays()) > Integer.parseInt(leaveManagementData.getNumberDayParam())) {
			response.add("Msg_734");
		} else if (leaveManagementData.getLeaveMana().size() == 2) {
			if(leaveManagementData.getLeaveMana().get(0).getRemainDays().equals(HALF_DAY)) {
				if(!leaveManagementData.getLeaveMana().get(1).getRemainDays().equals(HALF_DAY)) {
					response.add("Msg_734");
				} else {
					if(!leaveManagementData.getNumberDayParam().equals(ONE_DAY)) {
						response.add("Msg_734");
					}
				}
			} else if(leaveManagementData.getLeaveMana().get(0).getRemainDays().equals(ONE_DAY)) {
				response.add("Msg_743");
			}
			
			
		} else if (leaveManagementData.getLeaveMana().size() >= 3) {
			response.add("Msg_743");
		}

		if (response.isEmpty()) {

			List<LeaveComDayOffManagement> leavesComDay = leaveComDayOffManaRepository
					.getBycomDayOffID(leaveManagementData.getComDayOffID());

			
			List<LeaveManagementData> leaveManaUpdate = this.leaveManaDataRepository.getByComDayOffId(companyId, leaveManagementData.getEmployeeId(),
					leaveManagementData.getComDayOffID());
			
			
			
			List<String> currentLeaveMana = leaveManaUpdate.stream().map(LeaveManagementData::getID)
					.collect(Collectors.toList());
			// update Sub by current leave
			Double occurrentDay = 0.0;
			Boolean check = false;
			
			for (int i = 0; i < leaveManaUpdate.size(); i++) {
				occurrentDay = occurrentDay + leaveManaUpdate.get(i).getOccurredDays().v();
			}
			
			if(occurrentDay == Double.parseDouble(leaveManagementData.getNumberDayParam())) {
				check = true;
			}
			if (!currentLeaveMana.isEmpty()) {
				leaveManaDataRepository.updateSubByLeaveId(currentLeaveMana,check);

			}
			
			// delete List LeaveComDayOff
			if (leavesComDay.size() >= 1) {
				leaveComDayOffManaRepository.deleteByComDayOffID(leaveManagementData.getComDayOffID());
			}

			// insert List LeaveComDayOff
			List<LeavesManaData> leaveMana = leaveManagementData.getLeaveMana();
			List<LeaveComDayOffManagement> entitiesLeave = leaveMana.stream()
					.map(item -> new LeaveComDayOffManagement(item.getLeaveManaID(),
							leaveManagementData.getComDayOffID(), new Double(item.getRemainDays()), 0,
							TargetSelectionAtr.MANUAL.value))
					.collect(Collectors.toList());
			leaveComDayOffManaRepository.insertAll(entitiesLeave);

			// update Sub by new Leave
			List<String> listId = leaveMana.stream().map(LeavesManaData::getLeaveManaID)
					.collect(Collectors.toList());
			if (!listId.isEmpty()) {
				leaveManaDataRepository.updateByLeaveIds(listId);
			}
			
			// update

			Double remainDay = 0.0;
			Double remainDayNew = 0.0;
			Optional<CompensatoryDayOffManaData> comDayOff = comDayOffManaDataRepository
					.getBycomdayOffId(leaveManagementData.getComDayOffID());
			for (int i = 0; i < leaveManagementData.getLeaveMana().size(); i++) {
				remainDayNew = remainDayNew
						+ Double.parseDouble(leaveManagementData.getLeaveMana().get(i).getRemainDays());
			}

			if (comDayOff.isPresent()) {
				remainDay = comDayOff.get().getRequireDays().v();
				comDayOffManaDataRepository.updateRemainDay(leaveManagementData.getComDayOffID(),
						remainDayNew - remainDay);
			}
			
			response.add("Msg_15");
		}
		
		return response;
		
	}
	
}
