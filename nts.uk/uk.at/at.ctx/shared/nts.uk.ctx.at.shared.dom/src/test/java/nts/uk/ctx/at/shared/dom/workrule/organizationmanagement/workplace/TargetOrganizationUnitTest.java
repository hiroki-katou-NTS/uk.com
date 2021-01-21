package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class TargetOrganizationUnitTest {

	@Test
	public void getters() {
		TargetOrganizationUnit data = TargetOrganizationUnit.WORKPLACE;
		NtsAssert.invokeGetters(data);
	}

	@Test
	public void test() {
		TargetOrganizationUnit data = TargetOrganizationUnit.valueOf(0);
		assertThat(data).isEqualTo(TargetOrganizationUnit.WORKPLACE);
		data = TargetOrganizationUnit.valueOf(1);
		assertThat(data).isEqualTo(TargetOrganizationUnit.WORKPLACE_GROUP);
	}

}
