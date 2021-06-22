package nts.uk.ctx.sys.auth.dom.grant.rolesetjob;

import java.util.Arrays;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;

public class RoleSetGrantedJobTitleTest {
	@Test
	public void getters() {
		val roleSetGrantedJobTitle = new RoleSetGrantedJobTitle("cid",
				Arrays.asList(new RoleSetGrantedJobTitleDetail("roleSetCd", "jobTitleId", "cid")));
		NtsAssert.invokeGetters(roleSetGrantedJobTitle);
	}
}
