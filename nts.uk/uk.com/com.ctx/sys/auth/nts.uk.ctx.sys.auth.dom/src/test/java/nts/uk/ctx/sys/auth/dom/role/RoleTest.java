package nts.uk.ctx.sys.auth.dom.role;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import lombok.val;

public class RoleTest {
	
	@Test
	public void createGeneralRole() {
		val roleId = "roleId";
		val roleCode = new RoleCode("roleCode");
		val roleName = new RoleName("roleName");
		val roleType = RoleType.PERSONAL_INFO;
		val employeeReferenceRange = EmployeeReferenceRange.DEPARTMENT_AND_CHILD;
		val companyId = "companyId";
		val contractCode = new ContractCode("contractCode");
		
		val generalRole = Role.createGeneralRoll(
					roleId, roleCode
				,	roleType, employeeReferenceRange
				,	roleName, contractCode, companyId);
		
		assertThat(generalRole.getContractCode()).isEqualTo(contractCode);
		assertThat(generalRole.getCompanyId()).isEqualTo(companyId);
		assertThat(generalRole.getRoleId()).isEqualTo(roleId);
		assertThat(generalRole.getRoleCode()).isEqualTo(roleCode);
		assertThat(generalRole.getName()).isEqualTo(roleName);
		assertThat(generalRole.getRoleType()).isEqualTo(roleType);
		
		/** 担当区分　＝　一般　**/
		assertThat(generalRole.getAssignAtr()).isEqualTo(RoleAtr.GENERAL);
		assertThat(generalRole.getEmployeeReferenceRange()).isEqualTo(employeeReferenceRange);
		
	}
	
	@Test
	public void createInChargeRole() {
		val roleId = "roleId";
		val roleCode = new RoleCode("roleCode");
		val roleName = new RoleName("roleName");
		val roleType = RoleType.EMPLOYMENT;
		val companyId = "companyId";
		val contractCode = new ContractCode("contractCode");
		
		val inchargelRole = Role.createInChargeRoll(
					roleId, roleCode
				,	roleType, roleName
				,	contractCode, companyId);
		
		assertThat(inchargelRole.getContractCode()).isEqualTo(contractCode);
		assertThat(inchargelRole.getCompanyId()).isEqualTo(companyId);
		assertThat(inchargelRole.getRoleId()).isEqualTo(roleId);
		assertThat(inchargelRole.getRoleCode()).isEqualTo(roleCode);
		assertThat(inchargelRole.getName()).isEqualTo(roleName);
		assertThat(inchargelRole.getRoleType()).isEqualTo(roleType);
		
		/** 担当区分　＝　担当　**/
		assertThat(inchargelRole.getAssignAtr()).isEqualTo(RoleAtr.INCHARGE);
		assertThat(inchargelRole.getEmployeeReferenceRange()).isEqualTo(EmployeeReferenceRange.ALL_EMPLOYEE);

	}


}
