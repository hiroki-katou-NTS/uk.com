package nts.uk.ctx.at.record.dom.workrecord.identificationstatus.algorithm;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.Identification;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.IdentityProcessUseSet;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.enums.SelfConfirmError;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentificationRepository;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentityProcessUseSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author thanhnx 日の本人確認を登録する
 */
@Stateless
public class RegisterIdentityConfirmDay {
	
	@Inject
	private IdentityProcessUseSetRepository identityProcessUseSetRepository;
	
	@Inject
	private CheckIdentityVerification checkIdentityVerification;
	
	@Inject
	private IdentificationRepository identificationRepository;
	
	@Inject
	private EmployeeDailyPerErrorRepository employeeDailyPerErrorRepository;
	
	@Inject
	private ErrorAlarmWorkRecordRepository errorAlarmWorkRecordRepository;
	
	public void registerIdentity(List<GeneralDate> dates) {
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		GeneralDate processingYmd = GeneralDate.today();
		Optional<IdentityProcessUseSet>  identityProcessOpt= identityProcessUseSetRepository.findByKey(companyId);
		if(identityProcessOpt.isPresent()){
			Optional<SelfConfirmError> canRegist = checkIdentityVerification.check(identityProcessOpt.get());
			if(canRegist.isPresent()){
				if(canRegist.get() == SelfConfirmError.CAN_CONFIRM_WHEN_ERROR){
					dates.forEach(date -> {
						identificationRepository.insert(new Identification(companyId, employeeId, processingYmd, date));
					});
				}else{
					dates.forEach(date -> {
						List<EmployeeDailyPerError> employeeDailyPerErrors = employeeDailyPerErrorRepository
								.findAll(employeeId, date);
						if (!employeeDailyPerErrors.isEmpty()) {
							List<ErrorAlarmWorkRecord> errorAlarmWorkRecords = errorAlarmWorkRecordRepository
									.getListErAlByListCodeError(companyId,
											employeeDailyPerErrors.stream()
													.map(x -> x.getErrorAlarmWorkRecordCode().v())
													.collect(Collectors.toList()));
							if (errorAlarmWorkRecords.isEmpty()) {
								identificationRepository
										.insert(new Identification(companyId, employeeId, processingYmd, date));
							}
						}
					});
				}
			}
		}
	}
}
