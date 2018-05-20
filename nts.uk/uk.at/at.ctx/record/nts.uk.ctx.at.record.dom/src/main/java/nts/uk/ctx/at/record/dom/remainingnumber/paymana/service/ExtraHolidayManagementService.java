package nts.uk.ctx.at.record.dom.remainingnumber.paymana.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SEmpHistoryImport;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SysEmploymentHisAdapter;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.ComDayOffManaDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveManaDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.subhdmana.LeaveManagementData;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;

public class ExtraHolidayManagementService {
	
	@Inject
	private LeaveManaDataRepository leaveManaDataRepository;
	
	@Inject
	private ComDayOffManaDataRepository comDayOffManaDataRepository;
	
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;
	
	@Inject
	private SysEmploymentHisAdapter sysEmploymentHisAdapter;
	
	public void dataExtractionProcessing (String cid, String employeeId, int state, GeneralDate startDate, GeneralDate endDate){
		List<LeaveManagementData> listLeaveData = null;
		List<CompensatoryDayOffManaData> listCompensatoryData = null;
		GeneralDate baseDate = GeneralDate.today();
		if (!Objects.isNull(startDate) && !Objects.isNull(endDate)){
			listLeaveData = leaveManaDataRepository.getBySidWithsubHDAtrAndDateCondition(cid, employeeId, startDate, endDate);
			listCompensatoryData = comDayOffManaDataRepository.getBySidWithReDayAndDateCondition(cid, employeeId, startDate, endDate);
		} else {
			listLeaveData = leaveManaDataRepository.getBySidWithsubHDAtr(cid, employeeId, state);
			listCompensatoryData = comDayOffManaDataRepository.getBySidWithReDay(cid, employeeId);
		}
		if (listLeaveData.isEmpty() && listCompensatoryData.isEmpty()){
			throw new BusinessException("Msg_726");
		}
		Optional<SEmpHistoryImport> sEmpHistoryImport = sysEmploymentHisAdapter.findSEmpHistBySid(cid, employeeId, baseDate);
		if (sEmpHistoryImport.isPresent()){
			String sCd = sEmpHistoryImport.get().getEmploymentCode();
			Optional<ClosureEmployment> closureEmployment = closureEmploymentRepository.findByEmploymentCD(cid, sCd);
		}
		
	}
}
