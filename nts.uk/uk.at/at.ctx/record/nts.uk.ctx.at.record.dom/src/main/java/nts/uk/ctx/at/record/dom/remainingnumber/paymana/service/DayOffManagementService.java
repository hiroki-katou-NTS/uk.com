package nts.uk.ctx.at.record.dom.remainingnumber.paymana.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.base.TargetSelectionAtr;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.DayOffManagementData;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.DaysOffMana;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SEmpHistoryImport;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SysEmploymentHisAdapter;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveComDayOffManaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveEmSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DayOffManagementService {
	
	@Inject
	private SysEmploymentHisAdapter sysEmploymentHisAdapter;
	
	@Inject
	private CompensLeaveEmSetRepository compensLeaveEmSetRepository;
	
	@Inject
	private LeaveComDayOffManaRepository leaveComDayOffManaRepository;
	
	public static final String ONE_DAY = "1.0日";
	public static final String HALF_DAY = "0.5日";
	
	public List<String> updateDayOff(DayOffManagementData dayOffManagementData) {
		List<String> response = new ArrayList<>();
		String companyId = AppContexts.user().companyId();
		Optional<SEmpHistoryImport> emHsIm = sysEmploymentHisAdapter.findSEmpHistBySid(companyId, dayOffManagementData.getEmployeeId(), GeneralDate.today());
		if (emHsIm.isPresent()) {
			val data = emHsIm.get();
			CompensatoryLeaveEmSetting compensatoryLeaveEmSetting = compensLeaveEmSetRepository.find(companyId, data.getEmploymentCode());
			
			if(compensatoryLeaveEmSetting.getCompensatoryAcquisitionUse().getPreemptionPermit().value == 0) {
				// todo
			}
		    
			if(dayOffManagementData.getDaysOffMana().size() == 0 ) {
				response.add("Msg_738");
			} else if (dayOffManagementData.getDaysOffMana().size() == 1 && 
					dayOffManagementData.getDaysOffMana().get(0).getRemainDays().equals(ONE_DAY)) {
					response.add("Msg_733");
			} else if (dayOffManagementData.getDaysOffMana().size() == 2) {
					if (dayOffManagementData.getDaysOffMana().get(0).getRemainDays().equals(ONE_DAY) &&
							compensatoryLeaveEmSetting.getCompensatoryAcquisitionUse().getPreemptionPermit().value == 0) {
						response.add("Msg_733");
					}
					if(dayOffManagementData.getDaysOffMana().get(0).getRemainDays().equals(HALF_DAY) &&
							!dayOffManagementData.getDaysOffMana().get(0).getRemainDays().equals(HALF_DAY) ) {
						response.add("Msg_739");
					}
			} else {
				response.add("Msg_739");
			}
			
			List<LeaveComDayOffManagement> leavesComDay = leaveComDayOffManaRepository.getByLeaveID(dayOffManagementData.getLeaveId());
			if(leavesComDay.size() >=1 ) {
				
				// delete  List LeaveComDayOff
				leaveComDayOffManaRepository.deleteByLeaveId(dayOffManagementData.getLeaveId());
			}
			
				// insert List LeaveComDayOff
				List<DaysOffMana> daysOff = dayOffManagementData.getDaysOffMana();
				List<LeaveComDayOffManagement> entitiesLeave = daysOff.stream().map(item->
				  new LeaveComDayOffManagement(dayOffManagementData.getLeaveId(), item.getComDayOffID(), new BigDecimal(item.getRemainDays()),TargetSelectionAtr.MANUAL.value,TargetSelectionAtr.MANUAL.value)).collect(Collectors.toList());
				leaveComDayOffManaRepository.insertAll(entitiesLeave);
		}
		
		return response;
		
	}
	
}
