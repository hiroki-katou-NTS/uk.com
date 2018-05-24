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
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.ComDayOffManaDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveComDayOffManaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveEmSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
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
	
	@Inject
	private ComDayOffManaDataRepository comDayOffManaDataRepository;
	
	@Inject
	private CompensLeaveComSetRepository compensLeaveComSetRepository;
	
	public static final String ONE_DAY = "1.0";
	public static final String HALF_DAY = "0.5";
	
	public List<String> updateDayOff(DayOffManagementData dayOffManagementData) {
		int permission = 0;
		List<String> response = new ArrayList<>();
		String companyId = AppContexts.user().companyId();
		Optional<SEmpHistoryImport> emHsIm = sysEmploymentHisAdapter.findSEmpHistBySid(companyId, dayOffManagementData.getEmployeeId(), GeneralDate.today());
		if (emHsIm.isPresent()) {
			val data = emHsIm.get();
			
			// permission 
			CompensatoryLeaveEmSetting compensatoryLeaveEmSetting = compensLeaveEmSetRepository.find(companyId, data.getEmploymentCode());
			CompensatoryLeaveComSetting compensatoryLeaveComSetting = compensLeaveComSetRepository.find(companyId);
			
			if(compensatoryLeaveEmSetting.getCompensatoryAcquisitionUse().getPreemptionPermit().value == 0) {
				permission = compensatoryLeaveComSetting.getCompensatoryAcquisitionUse().getPreemptionPermit().value;
				
			} else {
				permission = compensatoryLeaveEmSetting.getCompensatoryAcquisitionUse().getPreemptionPermit().value;
			}
		    
			if(dayOffManagementData.getDaysOffMana().size() == 0 ) {
				response.add("Msg_738");
			} else if (dayOffManagementData.getDaysOffMana().size() == 1 && 
					dayOffManagementData.getDaysOffMana().get(0).getRemainDays().equals(ONE_DAY)) {
					response.add("Msg_733");
			} else if (dayOffManagementData.getDaysOffMana().size() == 2) {
					
				if (dayOffManagementData.getDaysOffMana().get(0).getRemainDays().equals(ONE_DAY)) {
					
						response.add("Msg_733");
					
				} else if (dayOffManagementData.getDaysOffMana().get(0).getRemainDays().equals(HALF_DAY)) {
					if (!dayOffManagementData.getDaysOffMana().get(1).getRemainDays().equals(HALF_DAY)) {
						response.add("Msg_739");
					}
				}
			} else if (dayOffManagementData.getDaysOffMana().size() >= 3) {
				response.add("Msg_739");
			}
			
			if(response.isEmpty()) {
				
				List<LeaveComDayOffManagement> leavesComDay = leaveComDayOffManaRepository
						.getByLeaveID(dayOffManagementData.getLeaveId());
				if (leavesComDay.size() >= 1) {

					// delete List LeaveComDayOff
					leaveComDayOffManaRepository.deleteByLeaveId(dayOffManagementData.getLeaveId());
				}

				// insert List LeaveComDayOff
				List<DaysOffMana> daysOff = dayOffManagementData.getDaysOffMana();
				List<LeaveComDayOffManagement> entitiesLeave = daysOff.stream()
						.map(item -> new LeaveComDayOffManagement(dayOffManagementData.getLeaveId(), item.getComDayOffID(),
								new BigDecimal(item.getRemainDays()), 0,
								TargetSelectionAtr.MANUAL.value))
						.collect(Collectors.toList());
				leaveComDayOffManaRepository.insertAll(entitiesLeave);
				
				List<String> comDayIds = daysOff.stream().map(item -> new String(item.getComDayOffID())).collect(Collectors.toList());
				comDayOffManaDataRepository.updateReDayByComDayId(comDayIds);
				
				response.add("Msg_15");
			}
			
		}
		
		return response;
		
	}
	
	
}
