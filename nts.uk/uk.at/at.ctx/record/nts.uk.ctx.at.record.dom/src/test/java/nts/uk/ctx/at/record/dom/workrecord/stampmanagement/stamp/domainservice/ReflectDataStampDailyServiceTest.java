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
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ExecutionTypeDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.EmbossingExecutionFlag;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.OutputCreateDailyOneDay;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampHelper;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.ReflectDataStampDailyService.Require;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.ErrMessageResource;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.OutputTimeReflectForWorkinfo;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionContent;
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
	List<ErrorMessageInfo> errorMessageInfos = new ArrayList<>();
	OutputCreateDailyOneDay outputCreateDailyOneDay1 = new OutputCreateDailyOneDay(new ArrayList<>(),
			new IntegrationOfDaily(employeeId,
					date,
					new WorkInfoOfDailyAttendance(),
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null),
			new ArrayList<>());
	
	OutputCreateDailyOneDay outputCreateDailyOneDay2 = new OutputCreateDailyOneDay(errorMessageInfos,
			new IntegrationOfDaily(employeeId,
					date,
					new WorkInfoOfDailyAttendance(),
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null),
			new ArrayList<>());
	
	ErrorMessageInfo error = new ErrorMessageInfo(cid,
			employeeId,
			date,
			ExecutionContent.DAILY_CALCULATION,
			new ErrMessageResource(cid),
			new ErrMessageContent("This is Message"));
	
	OutputTimeReflectForWorkinfo info = new OutputTimeReflectForWorkinfo();
	

	@Injectable
	private Require require;
	
	@Test
	public void test_error_not_empty() {
	
		Stamp stamp = StampHelper.getStampDefault();
		ChangeDailyAttendance changeDailyAtt = new  ChangeDailyAttendance(true,
				true, 
				true,
				true,
				true);
		
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
				
				require.get(cid,
						employeeId,
						date,
						outputCreateDailyOneDay1.getIntegrationOfDaily().getWorkInformation());
				
				result = info;
				
				require.reflectStamp(stamp,
						info.getStampReflectRangeOutput(),
						outputCreateDailyOneDay1.getIntegrationOfDaily(),
						changeDailyAtt);
				
				result = errorMessageInfos;
			}
		};
		
		Optional<GeneralDate> optional = ReflectDataStampDailyService.getJudgment(require, cid, employeeId, stamp);
		
		assertThat(optional.isPresent()).isFalse();

	}
	
	@Test
	public void test_error_empty() {
		
		errorMessageInfos.add(error);
		Stamp stamp = StampHelper.getStampDefault();
		
		OutputCreateDailyOneDay outputCreateDailyOneDay2 = new OutputCreateDailyOneDay(errorMessageInfos,
				new IntegrationOfDaily(employeeId,
						date,
						new WorkInfoOfDailyAttendance(),
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						null),
				new ArrayList<>());
		
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
				
				result = outputCreateDailyOneDay2;
				
			}
		};

		Optional<GeneralDate> optional = ReflectDataStampDailyService.getJudgment(require, cid, employeeId, stamp);

		assertThat(optional.isPresent()).isFalse();
		assertThat(optional).isEmpty();
	}

}