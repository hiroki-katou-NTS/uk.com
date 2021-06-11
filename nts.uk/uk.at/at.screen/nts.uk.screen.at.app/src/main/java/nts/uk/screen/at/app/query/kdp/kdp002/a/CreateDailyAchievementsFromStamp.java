package nts.uk.screen.at.app.query.kdp.kdp002.a;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

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
 * 打刻入力から日別実績を作成する
 * UKDesign.UniversalK.就業.KDP_打刻.KDP002_打刻入力(個人打刻).A:打刻入力(個人).メニュー別OCD.打刻入力から日別実績を作成する.打刻入力から日別実績を作成する
 * 
 * @author chungnt
 *
 */

@Stateless
public class CreateDailyAchievementsFromStamp {

	@Inject
	private CreateDailyResultDomainServiceNew createDailyResultDomainServiceNew;

	public CreateDailyAchievementsFromStampDto createDaily(CreateDailyParam param) {
		CreateDailyResultsStampsImp require = new CreateDailyResultsStampsImp();
		String cid = AppContexts.user().companyId();
		
		List<ErrorMessageInfo> result = CreateDailyResultsStamps.create(require, cid, param.sid, Optional.of(param.date));
		
		List<MessageError> messageErrors = result.stream().map(m -> {
			return new MessageError(m.getCompanyID(),
					m.getEmployeeID(),
					m.getProcessDate(),
					m.getExecutionContent().value,
					m.getResourceID().v(),
					m.getMessageError().v());
		}).collect(Collectors.toList());
		
		return new CreateDailyAchievementsFromStampDto(messageErrors);
	}

	private class CreateDailyResultsStampsImp implements CreateDailyResultsStamps.Require {

		@Override
		public OutputCreateDailyResult createDataNewNotAsync(String employeeId, DatePeriod periodTime,
				ExecutionAttr executionAttr, String companyId, ExecutionTypeDaily executionType,
				Optional<EmpCalAndSumExeLog> empCalAndSumExeLog, Optional<Boolean> checkLock) {
			return createDailyResultDomainServiceNew.createDataNewNotAsync(employeeId, periodTime, executionAttr,
					companyId, executionType, empCalAndSumExeLog, checkLock);
		}
	}
}
