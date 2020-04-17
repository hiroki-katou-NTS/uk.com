package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampHelper;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampFunctionAvailableService.Require;
/**
 * 
 * @author tutk
 *
 */
@RunWith(JMockit.class)
public class StampFunctionAvailableServiceTest {

	@Injectable
	private Require require;
	
	/**
	 * require.getListStampCard(employeeId) is empty
	 */
	@Test
	public void testStampFunctionAvailableService_1() {
		String employeeId = "employeeId";//dummy
		
		new Expectations() {
			{
				require.getListStampCard(anyString);
				result = new ArrayList<>();
			}
		};
		assertThat(StampFunctionAvailableService.decide(require, employeeId)).isFalse();		
	}
	
	/**
	 * require.getListStampCard(employeeId) not empty
	 */
	@Test
	public void testStampFunctionAvailableService_2() {
		String employeeId = "employeeId";//dummy
		
		new Expectations() {
			{
				require.getListStampCard(anyString);
				result = Arrays.asList(StampHelper.getStampCardByInput("stampCardId", "stampNumber", GeneralDate.today()));
			}
		};
		assertThat(StampFunctionAvailableService.decide(require, employeeId)).isTrue();		
	}

}
