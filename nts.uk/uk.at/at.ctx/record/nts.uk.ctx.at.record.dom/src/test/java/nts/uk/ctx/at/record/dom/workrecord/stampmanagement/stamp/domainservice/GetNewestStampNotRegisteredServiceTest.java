package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
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
	
	@Injectable
	private nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.RetrieveNoStampCardRegisteredService.Require require2;
	
	@Test
	public void test() {
		DatePeriod period = new DatePeriod(GeneralDate.today(), GeneralDate.today());
		new Expectations() {
			{
				require2.getStempRcNotResgistNumber(period);
				result = new ArrayList<>();
				require2.getStempRcNotResgistNumberStamp(period);
				result = new ArrayList<>();
			}
		};
		
		GetNewestStampNotRegisteredService a = new GetNewestStampNotRegisteredService();
		
		List<StampInfoDisp> list = a.get(require, period);
		
		assertThat(list).isEmpty();
	}
	
	@Test
	public void test1() {
		DatePeriod period = new DatePeriod(GeneralDate.today(), GeneralDate.today());
		
		new Expectations() {
			{
				require2.getStempRcNotResgistNumber(period);
				result = 
				require2.getStempRcNotResgistNumberStamp(period);
				result = new ArrayList<>();
			}
		};
		
	}

}
