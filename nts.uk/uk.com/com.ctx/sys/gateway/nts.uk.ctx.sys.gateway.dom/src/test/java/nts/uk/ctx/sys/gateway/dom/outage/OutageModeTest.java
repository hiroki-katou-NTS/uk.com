package nts.uk.ctx.sys.gateway.dom.outage;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import lombok.val;
import nts.uk.ctx.sys.gateway.dom.outage.helper.RoleExpectation;
import nts.uk.ctx.sys.gateway.dom.outage.helper.RoleExpectation.RoleType;

public class OutageModeTest {

	@Test
	public void employee() {

		val roles = RoleExpectation.setup(RoleType.EMPLOYEE);
		assertThat(OutageMode.ADMINISTRATOR.canUseBy(roles)).isFalse();
		assertThat(OutageMode.PERSON_IN_CHARGE.canUseBy(roles)).isFalse();
	}

	@Test
	public void personInCharge() {

		val roles = RoleExpectation.setup(RoleType.PERSON_IN_CHARGE);
		assertThat(OutageMode.ADMINISTRATOR.canUseBy(roles)).isFalse();
		assertThat(OutageMode.PERSON_IN_CHARGE.canUseBy(roles)).isTrue();
	}

	@Test
	public void companyAdmin() {

		val roles = RoleExpectation.setup(RoleType.COMPANY_ADMIN);
		assertThat(OutageMode.ADMINISTRATOR.canUseBy(roles)).isTrue();
		assertThat(OutageMode.PERSON_IN_CHARGE.canUseBy(roles)).isTrue();
	}

	@Test
	public void tenantAdmin() {

		val roles = RoleExpectation.setup(RoleType.TENANT_ADMIN);
		assertThat(OutageMode.ADMINISTRATOR.canUseBy(roles)).isTrue();
		assertThat(OutageMode.PERSON_IN_CHARGE.canUseBy(roles)).isTrue();
	}
}
