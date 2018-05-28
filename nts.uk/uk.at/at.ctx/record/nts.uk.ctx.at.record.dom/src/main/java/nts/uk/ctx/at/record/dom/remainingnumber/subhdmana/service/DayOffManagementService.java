package nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.remainingnumber.base.TargetSelectionAtr;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.ComDayOffManaDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.DayOffManagementData;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.DaysOffMana;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveComDayOffManaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DayOffManagementService {
	
	@Inject
	private LeaveComDayOffManaRepository leaveComDayOffManaRepository;
	
	@Inject
	private ComDayOffManaDataRepository comDayOffManaDataRepository;
	
	
	public static final String ONE_DAY = "1.0";
	public static final String HALF_DAY = "0.5";
	
	public List<String> updateDayOff(DayOffManagementData dayOffManagementData) {
		List<String> response = new ArrayList<>();
		String companyId = AppContexts.user().companyId();
		
		if(dayOffManagementData.getNumberDayParam().equals(ONE_DAY)) {
			if (dayOffManagementData.getDaysOffMana().size() == 0) {
				response.add("Msg_738");
			} else if (dayOffManagementData.getDaysOffMana().size() == 1
					&& !dayOffManagementData.getDaysOffMana().get(0).getRemainDays().equals(ONE_DAY)) {
				response.add("Msg_733");
			} else if (dayOffManagementData.getDaysOffMana().size() == 2) {

				if (dayOffManagementData.getDaysOffMana().get(0).getRemainDays().equals(ONE_DAY) ||
						dayOffManagementData.getDaysOffMana().get(1).getRemainDays().equals(ONE_DAY)) {

					response.add("Msg_739");

				} else if (dayOffManagementData.getDaysOffMana().get(0).getRemainDays().equals(HALF_DAY) ||
						dayOffManagementData.getDaysOffMana().get(1).getRemainDays().equals(HALF_DAY)) {
					if (!dayOffManagementData.getDaysOffMana().get(1).getRemainDays().equals(HALF_DAY) ||
							!dayOffManagementData.getDaysOffMana().get(0).getRemainDays().equals(HALF_DAY)) {
						response.add("Msg_733");
					}
				}
			} else if (dayOffManagementData.getDaysOffMana().size() >= 3) {
				response.add("Msg_739");
			}
		
		} else if(dayOffManagementData.getNumberDayParam().equals(HALF_DAY)) {
			
			if(dayOffManagementData.getDaysOffMana().size() == 0) {
				response.add("Msg_738");
			} else if (dayOffManagementData.getDaysOffMana().size() == 1 &&
					!dayOffManagementData.getDaysOffMana().get(0).getRemainDays().equals(HALF_DAY)) {
				response.add("Msg_733");
			} else if(dayOffManagementData.getDaysOffMana().size() >=2){
				response.add("Msg_739");
			}
			
		}
		
		

		if (response.isEmpty()) {

			List<LeaveComDayOffManagement> leavesComDay = leaveComDayOffManaRepository
					.getByLeaveID(dayOffManagementData.getLeaveId());

			
			List<CompensatoryDayOffManaData> daysOffMana = new ArrayList<>();
			daysOffMana = comDayOffManaDataRepository.getBySidComDayOffIdWithReDay(companyId,
					dayOffManagementData.getEmployeeId(), dayOffManagementData.getLeaveId());
			List<String> currentComDaySelect = daysOffMana.stream().map(item -> new String(item.getComDayOffID()))
					.collect(Collectors.toList());
			
			// update remainDays by current selected
			if (!currentComDaySelect.isEmpty()) {
				comDayOffManaDataRepository.updateReDayReqByComDayId(currentComDaySelect);
			}
			
			// delete List LeaveComDayOff
			if (leavesComDay.size() >= 1) {
				leaveComDayOffManaRepository.deleteByLeaveId(dayOffManagementData.getLeaveId());
			}

			// insert List LeaveComDayOff
			List<DaysOffMana> daysOff = dayOffManagementData.getDaysOffMana();
			List<LeaveComDayOffManagement> entitiesLeave = daysOff.stream()
					.map(item -> new LeaveComDayOffManagement(dayOffManagementData.getLeaveId(), item.getComDayOffID(),
							new BigDecimal(item.getRemainDays()), 0, TargetSelectionAtr.MANUAL.value))
					.collect(Collectors.toList());
			leaveComDayOffManaRepository.insertAll(entitiesLeave);

			List<String> comDayIds = daysOff.stream().map(item -> new String(item.getComDayOffID()))
					.collect(Collectors.toList());
			// update remainDays by new ComDayOff
			if (!comDayIds.isEmpty()) {
				comDayOffManaDataRepository.updateReDayByComDayId(comDayIds);
			}
			response.add("Msg_15");
		}

		return response;
		
	}
	
	
}
