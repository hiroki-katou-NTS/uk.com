package nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SEmpHistoryImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SysEmploymentHisAdapter;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.ComDayOffManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveEmSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ExtraHolidayManagementService {
	
	@Inject
	private LeaveManaDataRepository leaveManaDataRepository;
	
	@Inject
	private ComDayOffManaDataRepository comDayOffManaDataRepository;
	
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;
	
	@Inject
	private SysEmploymentHisAdapter sysEmploymentHisAdapter;
	
	@Inject
	private LeaveComDayOffManaRepository leaveComDayOffManaRepository;
	
	@Inject
	private CompensLeaveEmSetRepository compensLeaveEmSetRepository;
	
	@Inject
	private CompensLeaveComSetRepository compensLeaveComSetRepository;
	
	public ExtraHolidayManagementOutput dataExtractionProcessing (int searchMode, String employeeId, GeneralDate startDate, GeneralDate endDate){
		String cid = AppContexts.user().companyId();
		List<LeaveManagementData> listLeaveData = null;
		List<CompensatoryDayOffManaData> listCompensatoryData = null;
		List<LeaveComDayOffManagement> listLeaveComDayOffManagement = new ArrayList<>();
		SEmpHistoryImport empHistoryImport = null;
		ClosureEmployment closureEmploy = null;
		CompensatoryLeaveEmSetting compenLeaveEmpSetting = null;
		CompensatoryLeaveComSetting compensatoryLeaveComSetting = null;
		GeneralDate baseDate = GeneralDate.today();
		if (searchMode == 0){
			listLeaveData = leaveManaDataRepository.getBySidNotUnUsed(cid, employeeId);
			listCompensatoryData = comDayOffManaDataRepository.getBySidWithReDay(cid, employeeId); 
		} else {
			listLeaveData = leaveManaDataRepository.getByDateCondition(cid, employeeId, startDate, endDate);
			listCompensatoryData = comDayOffManaDataRepository.getByDateCondition(cid, employeeId, startDate, endDate);
		}
		if (!listLeaveData.isEmpty() && !listCompensatoryData.isEmpty()){
			List<String> listLeaveID = listLeaveData.stream().map(x ->{
				return x.getID();
			}).collect(Collectors.toList());
			listLeaveComDayOffManagement = leaveComDayOffManaRepository.getByListComLeaveID(listLeaveID);
		}
		Optional<SEmpHistoryImport> sEmpHistoryImport = sysEmploymentHisAdapter.findSEmpHistBySid(cid, employeeId, baseDate);
		if (sEmpHistoryImport.isPresent()){
			empHistoryImport = sEmpHistoryImport.get();
			String sCd = empHistoryImport.getEmploymentCode();
			Optional<ClosureEmployment> closureEmployment = closureEmploymentRepository.findByEmploymentCD(cid, sCd);
			if (closureEmployment.isPresent()){
				closureEmploy = closureEmployment.get();
			}
		}
		if (!Objects.isNull(empHistoryImport)){
			compenLeaveEmpSetting = compensLeaveEmSetRepository.find(cid, empHistoryImport.getEmploymentCode());
		}
		compensatoryLeaveComSetting = compensLeaveComSetRepository.find(cid);
		return new ExtraHolidayManagementOutput(listLeaveData, listCompensatoryData, listLeaveComDayOffManagement, empHistoryImport, closureEmploy, compenLeaveEmpSetting, compensatoryLeaveComSetting);
	}
}
