package nts.uk.ctx.at.record.dom.approvalmanagement.dailyperformance.algorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.ApprovalStatusAdapter;
import nts.uk.ctx.at.record.dom.approvalmanagement.ApprovalProcessingUseSetting;
import nts.uk.ctx.at.record.dom.approvalmanagement.ApprovalStatusOfDailyPerfor;
import nts.uk.ctx.at.record.dom.approvalmanagement.check.CheckApprovalOperation;
import nts.uk.ctx.at.record.dom.approvalmanagement.enums.ConfirmationOfManagerOrYouself;
import nts.uk.ctx.at.record.dom.approvalmanagement.repository.ApprovalProcessingUseSettingRepository;
import nts.uk.ctx.at.record.dom.approvalmanagement.repository.ApprovalStatusOfDailyPerforRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author thanhnx <<Public>> 日の承認を登録する
 */
@Stateless
public class RegisterDayApproval {

	@Inject
	private ApprovalProcessingUseSettingRepository approvalProcessingRepository;
	@Inject
	private CheckApprovalOperation checkApprovalOperation;
	@Inject
	private EmployeeDailyPerErrorRepository employeeDailyPerErrorRepository;
	@Inject
	private ErrorAlarmWorkRecordRepository errorAlarmWorkRecordRepository;
	// code old
//	@Inject
//	private OpOfDailyPerformance opOfDailyPerformance;
	
	@Inject
	private ApprovalStatusOfDailyPerforRepository approvalStatusOfDailyPerforRepository;

	@Inject
	private ApprovalStatusAdapter approvalStatusAdapter;

	public void registerDayApproval(ParamDayApproval param) {
		if(param.getContentApproval().isEmpty()) return;
		String companyId = AppContexts.user().companyId();
		Map<Pair<String, GeneralDate>, GeneralDate> employeeIdInsert = new HashMap<>();
		Map<Pair<String, GeneralDate>, GeneralDate> employeeIdRealse = new HashMap<>();
		Map<Pair<String, GeneralDate>, GeneralDate> employeeIdRealseAll = new HashMap<>();
		Optional<ApprovalProcessingUseSetting> approvalSetting = approvalProcessingRepository
				.findByCompanyId(companyId);
		if (!approvalSetting.isPresent())
			return;
		Optional<ConfirmationOfManagerOrYouself> checkOpt = checkApprovalOperation.checkApproval(approvalSetting.get());
		if (!checkOpt.isPresent())
			return;
		if (checkOpt.get().value == ConfirmationOfManagerOrYouself.CAN_CHECK.value) {
			param.getContentApproval().forEach(x -> {
				if(x.isFlagRemmoveAll()){
					employeeIdRealseAll.put(Pair.of(x.getEmployeeId(), x.getDate()), x.getDate());
				}
				else if (x.isStatus()) {
					employeeIdInsert.put(Pair.of(x.getEmployeeId(), x.getDate()), x.getDate());
				} else {
					employeeIdRealse.put(Pair.of(x.getEmployeeId(), x.getDate()), x.getDate());
				}
			});
		} else {
			for (ContentApproval data : param.getContentApproval()) {
				List<EmployeeDailyPerError> employeeDailyPerErrors = employeeDailyPerErrorRepository
						.find(param.getEmployeeId(), data.getDate());
				boolean isNotError = true;
				if (!employeeDailyPerErrors.isEmpty()) {
					List<ErrorAlarmWorkRecord> errorAlarmWorkRecords = errorAlarmWorkRecordRepository
							.getListErAlByListCodeError(companyId, employeeDailyPerErrors.stream()
									.map(x -> x.getErrorAlarmWorkRecordCode().v()).collect(Collectors.toList()));
					if (!errorAlarmWorkRecords.isEmpty()) {
						isNotError = false;
					}
				}
				if (isNotError) {
					if(data.isFlagRemmoveAll()){
						employeeIdRealseAll.put(Pair.of(data.getEmployeeId(), data.getDate()), data.getDate());
					}
					else if (data.isStatus()) {
						employeeIdInsert.put(Pair.of(data.getEmployeeId(), data.getDate()), data.getDate());
					} else {
						employeeIdRealse.put(Pair.of(data.getEmployeeId(), data.getDate()), data.getDate());
					}
				}
			}
		}
		// release status == false
		if (!employeeIdRealse.isEmpty())
			approvalStatusAdapter.releaseApproval(param.getEmployeeId(),
					employeeIdRealse.values().stream().collect(Collectors.toList()),
					employeeIdRealse.keySet().stream().map(x -> x.getKey()).collect(Collectors.toList()), 1, companyId);
		// register status == true
		if (!employeeIdInsert.isEmpty())
			approvalStatusAdapter.registerApproval(param.getEmployeeId(),
					employeeIdInsert.values().stream().collect(Collectors.toList()),
					employeeIdInsert.keySet().stream().map(x -> x.getKey()).collect(Collectors.toList()), 1, companyId);
		
		if(!employeeIdRealseAll.isEmpty()){
			employeeIdRealseAll.entrySet().forEach(x ->{
				Optional<ApprovalStatusOfDailyPerfor> dailyPerforOpt= approvalStatusOfDailyPerforRepository.find(x.getKey().getLeft(), x.getKey().getRight());
				if(dailyPerforOpt.isPresent()){
					approvalStatusAdapter.cleanApprovalRootState(dailyPerforOpt.get().getRootInstanceID(), 1);
				}
			});
		}
	}

//	public void registerDayApprovalOldaaa(ParamDayApproval param) {
//		String companyId = AppContexts.user().companyId();
//		Optional<ApprovalProcessingUseSetting> approvalSetting = approvalProcessingRepository
//				.findByCompanyId(companyId);
//		Map<Pair<String, GeneralDate>, GeneralDate> employeeIdInsert = new HashMap<>();
//		Map<Pair<String, GeneralDate>, GeneralDate> employeeIdRealse = new HashMap<>();
//		if (!approvalSetting.isPresent())
//			return;
//		OperationOfDailyPerformance operation = opOfDailyPerformance.find(new CompanyId(companyId));
//		if (operation == null || operation.getFunctionalRestriction() == null)
//			return;
//		if (operation.getFunctionalRestriction()
//				.getSupervisorConfirmError().value == ConfirmationOfManagerOrYouself.CAN_CHECK.value) {
//			param.getContentApproval().forEach(x -> {
//				if (x.isStatus()) {
//					employeeIdInsert.put(Pair.of(x.getEmployeeId(), x.getDate()), x.getDate());
//				} else {
//					employeeIdRealse.put(Pair.of(x.getEmployeeId(), x.getDate()), x.getDate());
//				}
//			});
//		} else {
//			for (ContentApproval data : param.getContentApproval()) {
//				List<EmployeeDailyPerError> employeeDailyPerErrors = employeeDailyPerErrorRepository
//						.find(param.getEmployeeId(), data.getDate());
//				boolean isNotError = true;
//				if (!employeeDailyPerErrors.isEmpty()) {
//					List<ErrorAlarmWorkRecord> errorAlarmWorkRecords = errorAlarmWorkRecordRepository
//							.getListErAlByListCodeError(companyId, employeeDailyPerErrors.stream()
//									.map(x -> x.getErrorAlarmWorkRecordCode().v()).collect(Collectors.toList()));
//					if (!errorAlarmWorkRecords.isEmpty()) {
//						isNotError = false;
//					}
//				}
//				if (isNotError) {
//					if (data.isStatus()) {
//						employeeIdInsert.put(Pair.of(data.getEmployeeId(), data.getDate()), data.getDate());
//					} else {
//						employeeIdRealse.put(Pair.of(data.getEmployeeId(), data.getDate()), data.getDate());
//					}
//				}
//			}
//		}
//		// release status == false
//		if (!employeeIdRealse.isEmpty())
//			approvalStatusAdapter.releaseApproval(param.getEmployeeId(),
//					employeeIdRealse.values().stream().collect(Collectors.toList()),
//					employeeIdRealse.keySet().stream().map(x -> x.getKey()).collect(Collectors.toList()), 1, companyId);
//		// register status == true
//		if (!employeeIdInsert.isEmpty())
//			approvalStatusAdapter.registerApproval(param.getEmployeeId(),
//					employeeIdInsert.values().stream().collect(Collectors.toList()),
//					employeeIdInsert.keySet().stream().map(x -> x.getKey()).collect(Collectors.toList()), 1, companyId);
//	}
}
