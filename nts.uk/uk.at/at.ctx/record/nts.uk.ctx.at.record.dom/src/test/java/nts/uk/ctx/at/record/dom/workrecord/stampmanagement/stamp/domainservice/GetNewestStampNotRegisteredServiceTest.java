package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampHelper;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecordHelper;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.GetNewestStampNotRegisteredService.Require;

/**
 * 
 * @author chungnt
 *
 */

@RunWith(JMockit.class)
public class GetNewestStampNotRegisteredServiceTest {

	@Injectable
	private Require require;
	
	@Test
	public void getters() {
		GetNewestStampNotRegisteredService getNewestStampNotRegisteredService = new GetNewestStampNotRegisteredService();
		NtsAssert.invokeGetters(getNewestStampNotRegisteredService);
	}
	
	@Test
	public void test_get_null() {
		
		GetNewestStampNotRegisteredService getNewestStampNotRegisteredService = new GetNewestStampNotRegisteredService();
		
		List<StampInfoDisp> disps = getNewestStampNotRegisteredService.get(require, new DatePeriod(GeneralDate.today(), GeneralDate.today()));
		
		assertThat(disps).isEmpty();
	}
	
	@Test
	public void test_get() {
		
		GetNewestStampNotRegisteredService getNewestStampNotRegisteredService = new GetNewestStampNotRegisteredService();
		
		new Expectations() {
			{	
				require.getStempRcNotResgistNumber(new DatePeriod(GeneralDate.today(), GeneralDate.today()));
				result = StampRecordHelper.getListStampRecord();
				
				require.getStempRcNotResgistNumberStamp("DUMMY",new DatePeriod(GeneralDate.today(), GeneralDate.today()));
				result = StampHelper.getListStampDefault();
			}
		};
		
		List<StampInfoDisp> disps = getNewestStampNotRegisteredService.get(require, new DatePeriod(GeneralDate.today(), GeneralDate.today()));
		
		assertThat(disps).isNotEmpty();
	}

}
