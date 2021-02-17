package nts.uk.ctx.office.dom.favorite.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.favorite.service.EmployeeListFromWpDomainService.Require;

@RunWith(JMockit.class)
public class EmployeeListFromWpDomainServiceTest {

	@Injectable
	private Require require;
	
	@Mocked
	private GeneralDate baseDate = GeneralDate.today();
	
	@Mocked
	private Map<String, String> workplaceId = new HashMap<String, String>();
	
	@Mocked
	private List<String> acquireTheWorkplace = new ArrayList<>();
	
	@Before
	public void beforeTest() {
		acquireTheWorkplace.add("mock-result");
		workplaceId.put("mock-sid", "mock-wkpId");
	}
	
	@After
	public void afterTest() {
		acquireTheWorkplace.clear();
		workplaceId.clear();
	}

	/**
	 * Test DS 職場IDから社員IDリストを取得
	 * WorkplaceId input is not empty
	 */
	@Test
	public void testWithWkpIdInput() {
		new Expectations() {
			{
				require.acquireToTheWorkplace(Arrays.asList("mock-wkpId"), baseDate);
				result = acquireTheWorkplace;
			}
		};
		
		val res = EmployeeListFromWpDomainService.getEmployeeIdList(require, "mock-sid", Arrays.asList("mock-wkpId"), baseDate);
		assertThat(res).isNotEmpty();
		assertThat(res.size()).isEqualTo(1);
		assertThat(res).isEqualTo(acquireTheWorkplace);
	}
	
	/**
	 * Test DS 職場IDから社員IDリストを取得
	 * WorkplaceId input is empty
	 */
	@Test
	public void testWithEmptyWkpIdInput() {
		acquireTheWorkplace.add("mock-result2");
		new Expectations() {
			{
				require.getWorkplaceId(Arrays.asList("mock-sid"), baseDate);
				result = workplaceId;
			}
			{
				require.acquireToTheWorkplace(Arrays.asList("mock-wkpId"), baseDate);
				result = acquireTheWorkplace;
			}
		};
		
		val res = EmployeeListFromWpDomainService.getEmployeeIdList(require, "mock-sid", Collections.emptyList(), baseDate);
		assertThat(res).isNotEmpty();
		assertThat(res.size()).isEqualTo(2);
		assertThat(res).isEqualTo(acquireTheWorkplace);
	}
}
