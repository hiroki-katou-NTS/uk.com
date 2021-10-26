package nts.uk.screen.at.app.command.kdp.kdp003.a;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.CreateDailyResultDomainServiceNew;

/**
 * 
 * @author vuongnv
 * Note: 打刻入力から日別実績を作成する
 */
@Stateless
public class RegisterStampInputWithEmployeeIdCommandHandler  {
	
	@Inject
	private CreateDailyResultDomainServiceNew createDailyResultDomainServiceNew;

	public List<CreateDailyAchievementsFromStampDto> get(String sid, GeneralDate date) {
		return null;
		
//		CreateDailyResultsStampsRequireImpl require = new CreateDailyResultsStampsRequireImpl(createDailyResultDomainServiceNew);
//		String cid = AppContexts.user().companyId();
//		
//		List<ErrorMessageInfo> errorMessageInfos = CreateDailyResultsStamps.create(require, cid, sid, Optional.of(date));
//		
//		return errorMessageInfos.stream()
//				.map(m -> {
//					return new CreateDailyAchievementsFromStampDto(
//							m.getProcessDate(),
//							m.getExecutionContent().value,
//							m.getResourceID().v(),
//							m.getMessageError().v());
//				})
//				.collect(Collectors.toList());
	}
	
//	@AllArgsConstructor
//	private class CreateDailyResultsStampsRequireImpl implements CreateDailyResultsStamps.Require {
//		
//		private CreateDailyResultDomainServiceNew createDailyResultDomainServiceNew;
//		
//		@Override
//		public OutputCreateDailyResult createDataNewNotAsync(String employeeId, DatePeriod periodTime,
//				ExecutionAttr executionAttr, String companyId, ExecutionTypeDaily executionType,
//				Optional<EmpCalAndSumExeLog> empCalAndSumExeLog, Optional<Boolean> checkLock) {
//			return this.createDailyResultDomainServiceNew.createDataNewNotAsync(employeeId, periodTime, executionAttr, companyId, executionType, empCalAndSumExeLog, checkLock);
//		}
//	}
}