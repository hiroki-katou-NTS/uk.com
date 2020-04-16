package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.stamp.application.CheckErrorType;
import nts.uk.ctx.at.record.dom.stamp.application.PromptingMessage;
/**
 * 
 * @author tutk
 *
 */
public class DailyAttdErrorInfoTest {

	@Test
	public void getters() {
		DailyAttdErrorInfo dailyAttdErrorInfo = DomainServiceHeplper.getDailyAttdErrorInfoDefault();
		NtsAssert.invokeGetters(dailyAttdErrorInfo);
	}
	
	@Test
	public void testDailyAttdErrorInfo() {
		CheckErrorType checkErrorType = CheckErrorType.valueOf(1);//dummy
		PromptingMessage promptingMessage = DomainServiceHeplper.getPromptingMessageDefault();//dummy
		GeneralDate lastDateError = GeneralDate.today();//dummy
		List<Integer> listRequired = Arrays.asList(1,2,3);//dummy
		DailyAttdErrorInfo dailyAttdErrorInfo = new DailyAttdErrorInfo(checkErrorType, promptingMessage, lastDateError, listRequired);
		NtsAssert.invokeGetters(dailyAttdErrorInfo);
	}

}
