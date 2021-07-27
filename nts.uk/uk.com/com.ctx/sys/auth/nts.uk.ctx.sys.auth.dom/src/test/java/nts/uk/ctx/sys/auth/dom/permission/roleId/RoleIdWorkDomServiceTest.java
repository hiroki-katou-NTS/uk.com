package nts.uk.ctx.sys.auth.dom.permission.roleId;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.AllArgsConstructor;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.permission.roleId.RoleIdWorkDomService.Require;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@RunWith(JMockit.class)
public class RoleIdWorkDomServiceTest {
	
	@Injectable
	private Require require;
	
	@Test
	public void testEmptySids() {
		List<String> sids = 
				Collections.emptyList();
		
		GeneralDate date = GeneralDate.today();
		
		Map<String, String> roles = RoleIdWorkDomService.get(require, sids, date);
		
		assertThat(roles.isEmpty()).isEqualTo(true);
		
	}
	
	
	

	@Test
	public void testSids() {
		List<String> sids = 
				Arrays.asList("e1", "e2", "e3");

		GeneralDate date = GeneralDate.today();
		new Expectations() {
			{
				// e1
				require.getUserIDByEmpID("e1");
				result = Optional.of("u1");
				
				require.getRoleSetFromUserId("u1", date);
						
				result = Optional.of(new RoleSet(
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						"r1",
						null
						));
				
				// e2
				require.getUserIDByEmpID("e2");
				result = Optional.of("u2");
				
				require.getRoleSetFromUserId("u2", date);
				result = Optional.of(new RoleSet(
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						"r2",
						null
						));
				
				// e3
				require.getUserIDByEmpID("e3");
				result = Optional.of("u3");
				
				require.getRoleSetFromUserId("u3", date);
				result = Optional.of(new RoleSet(
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						null,
						"r3",
						null
						));
				
			}
		};
		
		Map<String, String> roles = RoleIdWorkDomService.get(require, sids, date);
				
		assertThat(
				roles.entrySet()
				.stream()
				.map(x -> new Result(x.getKey(), x.getValue()))
				.collect(Collectors.toList())
						)
		.extracting(
				d -> d.getSid(),
				d -> d.getRole())
		.containsExactly(
				tuple("e1", "r1"),
				tuple("e2", "r2"),
				tuple("e3", "r3")
				);
		

		
	}
	
	
	
	@AllArgsConstructor
	public class Result {
		private String sid;
		private String role;
		
		public String getSid() {
			return sid;
		}
		
		public String getRole() {
			return role;
		}
	}

}
