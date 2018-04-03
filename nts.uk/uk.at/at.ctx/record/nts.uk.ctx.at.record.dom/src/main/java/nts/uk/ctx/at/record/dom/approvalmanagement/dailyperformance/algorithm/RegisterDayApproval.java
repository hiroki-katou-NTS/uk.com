package nts.uk.ctx.at.record.dom.approvalmanagement.dailyperformance.algorithm;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.approvalmanagement.ApprovalProcessingUseSetting;
import nts.uk.ctx.at.record.dom.approvalmanagement.check.CheckApprovalOperation;
import nts.uk.ctx.at.record.dom.approvalmanagement.enums.ConfirmationOfManagerOrYouself;
import nts.uk.ctx.at.record.dom.approvalmanagement.repository.ApprovalProcessingUseSettingRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.OpOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.OperationOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
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
	@Inject
	private OpOfDailyPerformance opOfDailyPerformance;
	
	public void registerDayApproval(ParamDayApproval param) {
		String companyId = AppContexts.user().companyId();
		Optional<ApprovalProcessingUseSetting> approvalSetting = approvalProcessingRepository.findByCompanyId(companyId);
		if(approvalSetting.isPresent()){
			Optional<ConfirmationOfManagerOrYouself> checkOpt = checkApprovalOperation.checkApproval(approvalSetting.get());
			if(checkOpt.isPresent()){
				if(checkOpt.get().value == ConfirmationOfManagerOrYouself.CAN_CHECK.value){
					//TODO insert column
				}else{
					param.getContentApproval().forEach(data -> {
						List<EmployeeDailyPerError> employeeDailyPerErrors = employeeDailyPerErrorRepository
								.findAll(param.getEmployeeId(), data.getDate());
						if (!employeeDailyPerErrors.isEmpty()) {
							List<ErrorAlarmWorkRecord> errorAlarmWorkRecords = errorAlarmWorkRecordRepository
									.getListErAlByListCodeError(companyId,
											employeeDailyPerErrors.stream()
													.map(x -> x.getErrorAlarmWorkRecordCode().v())
													.collect(Collectors.toList()));
							if (errorAlarmWorkRecords.isEmpty()) {
								//TODO insert column
							}
						}
					});
					
				}
			}
		}
	}

	public void registerDayApprovalOld(ParamDayApproval param) {
		String companyId = AppContexts.user().companyId();
		Optional<ApprovalProcessingUseSetting> approvalSetting = approvalProcessingRepository
				.findByCompanyId(companyId);
		if (approvalSetting.isPresent()) {
			OperationOfDailyPerformance operation = opOfDailyPerformance.find(new CompanyId(companyId));
			if (operation != null && operation.getFunctionalRestriction() != null) {
				if (operation.getFunctionalRestriction()
						.getSupervisorConfirmError().value == ConfirmationOfManagerOrYouself.CAN_CHECK.value) {
					// TODO insert column
				} else {
					param.getContentApproval().forEach(data -> {
						List<EmployeeDailyPerError> employeeDailyPerErrors = employeeDailyPerErrorRepository
								.findAll(param.getEmployeeId(), data.getDate());
						if (!employeeDailyPerErrors.isEmpty()) {
							List<ErrorAlarmWorkRecord> errorAlarmWorkRecords = errorAlarmWorkRecordRepository
									.getListErAlByListCodeError(companyId,
											employeeDailyPerErrors.stream()
													.map(x -> x.getErrorAlarmWorkRecordCode().v())
													.collect(Collectors.toList()));
							if (errorAlarmWorkRecords.isEmpty()) {
								// TODO insert column
							}
						}
					});

				}
			}
		}
	}
}
