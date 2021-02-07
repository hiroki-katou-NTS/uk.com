package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ExecutionAttr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ExecutionTypeDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.OutputCreateDailyResult;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.ProcessState;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.CreateDailyResultsStamps.Require;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.ErrMessageResource;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionContent;

/**
 * 
 * @author chungnt
 *
 */
@RunWith(JMockit.class)
public class CreateDailyResultsStampsTest {

	@Injectable
	private Require require;
	
	/**
	 * !date.isPresent()
	 */
	@Test
	public void test_date_null() {
		
		List<ErrorMessageInfo> errors = CreateDailyResultsStamps.create(require, "DUMMY", "DUMMY", Optional.empty());
		assertThat(errors).isEmpty();
	}
	
	/**
	 * date.isPresent()
	 */
	@Test
	public void test_lstEmpId_empty() {
		String employeeId = "DUMMY";
		String companyId = "DUMMY";
		
		DatePeriod datePeriod = new DatePeriod(GeneralDate.today(), GeneralDate.today());
		
		List<ErrorMessageInfo> infos = new ArrayList<>();
		
		ErrorMessageInfo info1 = new ErrorMessageInfo(companyId,
				employeeId,
				GeneralDate.today(),
				ExecutionContent.DAILY_CREATION,
				new ErrMessageResource("DUMMY"),
				new ErrMessageContent("This is Message"));
		
		infos.add(info1);
		
		
		OutputCreateDailyResult dailyResult = new OutputCreateDailyResult(ProcessState.SUCCESS, infos);
		
		new Expectations() {
			{
				require.createDataNewNotAsync(employeeId,
						datePeriod,
						ExecutionAttr.MANUAL,
						companyId, 
						ExecutionTypeDaily.IMPRINT, 
						Optional.empty(), 
						Optional.empty());
				
				result = dailyResult;
				
			}
		};
		
		List<ErrorMessageInfo> errors = CreateDailyResultsStamps.create(require, companyId, employeeId, Optional.empty());
		assertThat(errors).isNotEmpty();
		assertThat(errors.get(0).getEmployeeID()).isEqualTo(employeeId);
		assertThat(errors.get(0).getCompanyID()).isEqualTo(companyId);
		assertThat(errors.get(0).getMessageError()).isEqualTo("This is Message");
		assertThat(errors.get(0).getExecutionContent()).isEqualTo(ExecutionContent.DAILY_CREATION);
		assertThat(errors.get(0).getProcessDate()).isEqualTo(GeneralDate.today());
	}
	
}
