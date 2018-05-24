package nts.uk.ctx.at.record.dom.remainingnumber.paymana.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.remainingnumber.base.TargetSelectionAtr;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.LeaveMana;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.LeaveManagementData;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveComDayOffManaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveManaDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveManagementDataAgg;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class LeaveManagementService {

	
	@Inject
	private LeaveComDayOffManaRepository leaveComDayOffManaRepository;
	
	@Inject
	private LeaveManaDataRepository leaveManaDataRepository;
	
	public static final String ONE_DAY = "1.0";
	public static final String HALF_DAY = "0.5";
	
	public List<String> updateDayOff(LeaveManagementData leaveManagementData) {
		
		List<String> response = new ArrayList<>();
		String companyId = AppContexts.user().companyId();

		if (leaveManagementData.getLeaveMana().size() == 0) {
			response.add("Msg_738");
		} else if (leaveManagementData.getLeaveMana().size() == 1
				&& !leaveManagementData.getLeaveMana().get(0).getRemainDays().equals(ONE_DAY)) {
			response.add("Msg_733");
		} else if (leaveManagementData.getLeaveMana().size() == 2) {

			if (leaveManagementData.getLeaveMana().get(0).getRemainDays().equals(ONE_DAY)) {

				response.add("Msg_733");

			} else if (leaveManagementData.getLeaveMana().get(0).getRemainDays().equals(HALF_DAY)) {
				if (!leaveManagementData.getLeaveMana().get(1).getRemainDays().equals(HALF_DAY)) {
					response.add("Msg_739");
				}
			}
		} else if (leaveManagementData.getLeaveMana().size() >= 3) {
			response.add("Msg_739");
		}

		if (response.isEmpty()) {

			List<LeaveComDayOffManagement> leavesComDay = leaveComDayOffManaRepository
					.getBycomDayOffID(leaveManagementData.getComDayOffID());

			// update Ustr current leave
			List<LeaveManagementDataAgg> leaveManaUpdate = new ArrayList<>();
			leaveManaUpdate = leaveManaDataRepository.getByComDayOffId(companyId, leaveManagementData.getEmployeeId(),
					leaveManagementData.getComDayOffID());
			List<String> currentLeaveMana = leaveManaUpdate.stream().map(item -> new String(item.getID()))
					.collect(Collectors.toList());

			if (!currentLeaveMana.isEmpty()) {
				leaveManaDataRepository.updateNotByLeaveIds(currentLeaveMana);

			}

			if (leavesComDay.size() >= 1) {

				// delete List LeaveComDayOff
				leaveComDayOffManaRepository.deleteByComDayOffID(leaveManagementData.getComDayOffID());
			}

			// insert List LeaveComDayOff
			List<LeaveMana> leaveMana = leaveManagementData.getLeaveMana();
			List<LeaveComDayOffManagement> entitiesLeave = leaveMana.stream()
					.map(item -> new LeaveComDayOffManagement(item.getLeaveManaID(),
							leaveManagementData.getComDayOffID(), new BigDecimal(item.getRemainDays()), 0,
							TargetSelectionAtr.MANUAL.value))
					.collect(Collectors.toList());
			leaveComDayOffManaRepository.insertAll(entitiesLeave);

			// update Leave
			List<String> listId = leaveMana.stream().map(item -> new String(item.getLeaveManaID()))
					.collect(Collectors.toList());
			if (!listId.isEmpty()) {
				leaveManaDataRepository.updateByLeaveIds(listId);
			}
			response.add("Msg_15");
		}
		
		return response;
		
	}
	
}
