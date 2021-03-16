package nts.uk.screen.at.app.command.kdp.kdp003.a;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ExecutionAttr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ExecutionTypeDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.CreateDailyResultDomainServiceNew;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.OutputCreateDailyResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.CreateDailyResultsStamps;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLog;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;
import nts.uk.shr.com.context.AppContexts;

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
		
		CreateDailyResultsStampsRequireImpl require = new CreateDailyResultsStampsRequireImpl(createDailyResultDomainServiceNew);
		String cid = AppContexts.user().companyId();
		
		List<ErrorMessageInfo> errorMessageInfos = CreateDailyResultsStamps.create(require, cid, sid, Optional.of(date));
		
		return errorMessageInfos.stream()
				.map(m -> {
					return new CreateDailyAchievementsFromStampDto(
							m.getProcessDate(),
							m.getExecutionContent().value,
							m.getResourceID().v(),
							m.getMessageError().v());
				})
				.collect(Collectors.toList());
	}
	
	@AllArgsConstructor
	private class CreateDailyResultsStampsRequireImpl implements CreateDailyResultsStamps.Require {
		
		private CreateDailyResultDomainServiceNew createDailyResultDomainServiceNew;
		
		@Override
		public OutputCreateDailyResult createDataNewNotAsync(String employeeId, DatePeriod periodTime,
				ExecutionAttr executionAttr, String companyId, ExecutionTypeDaily executionType,
				Optional<EmpCalAndSumExeLog> empCalAndSumExeLog, Optional<Boolean> checkLock) {
			return this.createDailyResultDomainServiceNew.createDataNewNotAsync(employeeId, periodTime, executionAttr, companyId, executionType, empCalAndSumExeLog, checkLock);
		}
	}
}