package nts.uk.ctx.sys.auth.dom.wplmanagementauthority.domservice;



import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.auth.dom.permission.roleId.RoleIdWorkDomService;
import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.wplmanagementauthority.domservice.AprrovalWorkPlaceDomService.Require;

@RunWith(JMockit.class)
public class AprrovalWorkPlaceDomServiceTest {

	@Injectable
	private Require require;
	
	@Test
	public void test() {
		List<String> workPlaceIds = 
				Arrays.asList("w1", "w2", "w3");
		
		List<String> sids = 
				Arrays.asList("s1", "s2", "s3");
		
		GeneralDate date = GeneralDate.today();
		Map<String, String> inputResult = new HashMap<String, String>();
		inputResult.put("e1", "r1");
		inputResult.put("e2", "r2");
		inputResult.put("e3", "r3");
		new Expectations() {
			{
				require.getSids(
						workPlaceIds,
						new DatePeriod(date, date));
				result = sids;
				
				new MockUp<RoleIdWorkDomService>() {
					@Mock
					public Map<String, String> get(
							RoleIdWorkDomService.Require require,
							List<String> sids,
							GeneralDate date
							) {
						return inputResult;
					}
				};
				
				require.getRoles("cid");
				result = 
					Arrays.asList(
							new Role(
									"r1",
									null,
									null,
									null,
									null,
									null,
									null,
									null
									),
							new Role(
									"r2",
									null,
									null,
									null,
									null,
									null,
									null,
									null
									),
							new Role(
									"r3",
									null,
									null,
									null,
									null,
									null,
									null,
									null
									));
				
				
						
			}
		};
		
		
		List<String> sidsResult = AprrovalWorkPlaceDomService.get(require, workPlaceIds, date, "cid");
		
		assertThat(sidsResult)
			.extracting(
					d -> d)
			.containsExactly(
					"e1",
					"e2",
					"e3"
					);
		
	}
	
	@Test
	public void testNotInRole() {
		List<String> workPlaceIds = 
				Arrays.asList("w1", "w2", "w3");
		
		List<String> sids = 
				Arrays.asList("s1", "s2", "s3");
		
		GeneralDate date = GeneralDate.today();
		Map<String, String> inputResult = new HashMap<String, String>();
		inputResult.put("e1", "r1");
		inputResult.put("e2", "r2");
		inputResult.put("e3", "r3");
		new Expectations() {
			{
				require.getSids(
						workPlaceIds,
						new DatePeriod(date, date));
				result = sids;
				
				new MockUp<RoleIdWorkDomService>() {
					@Mock
					public Map<String, String> get(
							RoleIdWorkDomService.Require require,
							List<String> sids,
							GeneralDate date
							) {
						return inputResult;
					}
				};
				
				require.getRoles("cid");
				result = 
					Arrays.asList(
							new Role(
									"r4",
									null,
									null,
									null,
									null,
									null,
									null,
									null
									),
							new Role(
									"r5",
									null,
									null,
									null,
									null,
									null,
									null,
									null
									),
							new Role(
									"r6",
									null,
									null,
									null,
									null,
									null,
									null,
									null
									));
				
				
						
			}
		};
		
		
		List<String> sidsResult = AprrovalWorkPlaceDomService.get(require, workPlaceIds, date, "cid");
		
		assertThat(CollectionUtil.isEmpty(sidsResult)).isEqualTo(true);
	}

}
