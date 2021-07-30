package nts.uk.ctx.at.auth.dom.employmentrole;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.enums.EnumAdaptor;
import nts.arc.testing.assertion.NtsAssert;

/**
 * 
 * @author chungnt
 *
 */

public class EmploymentRoleTest {

	String companyId = "Cid";
	String roleId = "RoleId";
	
	
	// Test contracter 2 param
	@Test
	public void test() {
		EmploymentRole role = new EmploymentRole(companyId, roleId);
		NtsAssert.invokeGetters(role);
		assertThat(role.getCompanyId()).isEqualTo("Cid");
		assertThat(role.getRoleId()).isEqualTo("RoleId");
	}
	
	// Test all contracter
	@Test
	public void test_1() {
		EmploymentRole role = new EmploymentRole(companyId,
				roleId,
				EnumAdaptor.valueOf(4, ScheduleEmployeeRef.class),
				EnumAdaptor.valueOf(0, EmployeeRefRange.class),
				EnumAdaptor.valueOf(0, EmployeeRefRange.class),
				EnumAdaptor.valueOf(0, EmployeeReferenceRange.class),
				EnumAdaptor.valueOf(0, DisabledSegment.class));
		NtsAssert.invokeGetters(role);
		assertThat(role.getCompanyId()).isEqualTo("Cid");
		assertThat(role.getRoleId()).isEqualTo("RoleId");
		assertThat(role.getScheduleEmployeeRef().value).isEqualTo(4);
		assertThat(role.getBookEmployeeRef().value).isEqualTo(0);
		assertThat(role.getEmployeeRefSpecAgent().value).isEqualTo(0);
		assertThat(role.getPresentInqEmployeeRef().value).isEqualTo(0);
		assertThat(role.getFutureDateRefPermit().value).isEqualTo(0);
	}
	
	// Test all createFromJavaType
		@Test
		public void test_2() {
			EmploymentRole role = EmploymentRole.createFromJavaType(companyId, roleId);
			NtsAssert.invokeGetters(role);
			assertThat(role.getCompanyId()).isEqualTo("Cid");
			assertThat(role.getRoleId()).isEqualTo("RoleId");
		}

}
