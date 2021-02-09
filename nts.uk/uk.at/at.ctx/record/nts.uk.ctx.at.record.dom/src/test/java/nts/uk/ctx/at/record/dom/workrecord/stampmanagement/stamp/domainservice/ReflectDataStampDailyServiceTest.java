package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ExecutionTypeDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.EmbossingExecutionFlag;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.OutputCreateDailyOneDay;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampHelper;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.ReflectDataStampDailyService.Require;
/**
 * 
 * @author tutk
 *
 */

@RunWith(JMockit.class)
public class ReflectDataStampDailyServiceTest {
	
	String employeeId = "employeeId";
	String cid = "cid";
	GeneralDate date = GeneralDate.today();
	OutputCreateDailyOneDay outputCreateDailyOneDay1 = new OutputCreateDailyOneDay(new ArrayList<>(),
			null,
			new ArrayList<>());

	@Injectable
	private Require require;
	
	@Test
	public void testReflectDataStampDailyService() {
		Stamp stamp = StampHelper.getStampDefault();
		assertThat(ReflectDataStampDailyService.getJudgment(require, employeeId, stamp).isPresent()).isFalse();
	}
	
	
	// !dailyOneDay.getListErrorMessageInfo().isEmpty()
	
	@Test
	public void test_error_not_empty() {
		
		new Expectations() {
			{
				require.createDailyResult(cid,
						employeeId,
						date,
						ExecutionTypeDaily.CREATE,
						EmbossingExecutionFlag.ALL,
						null,
						null,
						null);
				
				result = outputCreateDailyOneDay1;
				
			}
		};
		
		String employeeId = "employeeId";
		Stamp stamp = StampHelper.getStampDefault();
		assertThat(ReflectDataStampDailyService.getJudgment(require, employeeId, stamp).isPresent()).isFalse();
	}

}
