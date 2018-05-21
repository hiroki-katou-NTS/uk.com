package nts.uk.ctx.at.record.dom.remainingnumber.dayoffmanagement;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
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
	
	public void updateDayOff(DayOffManagementData dayOffManagementData) {
		
		String companyId = AppContexts.user().companyId();
		Optional<SEmpHistoryImport> emHsIm = sysEmploymentHisAdapter.findSEmpHistBySid(companyId, dayOffManagementData.getEmployeeId(), GeneralDate.today());
		if (emHsIm.isPresent()) {
			val data = emHsIm.get();
			CompensatoryLeaveEmSetting compensatoryLeaveEmSetting = compensLeaveEmSetRepository.find(companyId, data.getEmploymentCode());
			
			if(compensatoryLeaveEmSetting.getCompensatoryAcquisitionUse().getPreemptionPermit().value == 0) {
				// todo
			}
		    
			if(dayOffManagementData.getDaysOffMana().size() == 0 ) {
				throw new BusinessException("Msg_738");
			} else if (dayOffManagementData.getDaysOffMana().size() == 1 && 
					dayOffManagementData.getDaysOffMana().get(0).getUseNumberDay().equals(ONE_DAY)) {
					throw new BusinessException("Msg_733");
			} else if (dayOffManagementData.getDaysOffMana().size() == 2) {
					if (dayOffManagementData.getDaysOffMana().get(0).getUseNumberDay().equals(ONE_DAY) &&
						dayOffManagementData.getDaysOffMana().get(1).getUseNumberDay().equals(HALF_DAY)) {
						throw new BusinessException("Msg_733");
					}
					if(compensatoryLeaveEmSetting.getCompensatoryAcquisitionUse().getPreemptionPermit().value == 0) {
						throw new BusinessException("Msg_739");
					}
			} else {
				throw new BusinessException("Msg_739");
			}
			
			List<LeaveComDayOffManagement> leavesComDay = leaveComDayOffManaRepository.getByLeaveID(dayOffManagementData.getLeaveId());
			if(leavesComDay.size() >=1 ) {
				
				// to do delete
			}
			
			// insert to data
			
			
		}
		
		
	}
	
}
