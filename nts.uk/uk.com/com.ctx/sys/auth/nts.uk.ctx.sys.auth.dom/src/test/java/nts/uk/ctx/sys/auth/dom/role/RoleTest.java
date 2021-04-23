package nts.uk.ctx.sys.auth.dom.role;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import lombok.val;
/**
 * ロールのUTコード
 * @author lan_lt
 *
 */
public class RoleTest {
	private String roleId;
	private String companyId;
	private RoleCode roleCode;
	private RoleName roleName;
	private ContractCode contractCode;
	
	@Before
	public void initData() {
		this.roleId = "roleId";
		this.companyId = "companyId";
		this.roleCode = new RoleCode("roleCode");
		this.roleName = new RoleName("roleName");
		this.contractCode = new ContractCode("contractCode");
	}
	
	@Test
	public void createGeneralRole() {
		val roleType = RoleType.PERSONAL_INFO;
		val employeeReferenceRange = EmployeeReferenceRange.DEPARTMENT_AND_CHILD;
		
		val generalRole = Role.createGeneralRoll(
					this.roleId, this.contractCode, this.companyId
				,	this.roleCode,	this.roleName
				,	roleType, employeeReferenceRange);
		
		assertThat(generalRole.getContractCode()).isEqualTo(this.contractCode);
		assertThat(generalRole.getCompanyId()).isEqualTo(this.companyId);
		
		assertThat(generalRole.getRoleId()).isEqualTo(this.roleId);
		assertThat(generalRole.getRoleCode()).isEqualTo(roleCode);
		assertThat(generalRole.getName()).isEqualTo(roleName);
		assertThat(generalRole.getRoleType()).isEqualTo(roleType);
		
		/** 担当区分　＝　一般　**/
		assertThat(generalRole.getAssignAtr()).isEqualTo(RoleAtr.GENERAL);
		assertThat(generalRole.getEmployeeReferenceRange()).isEqualTo(employeeReferenceRange);
		
	}
	
	/**
	 * 担当区分　＝　一般
	 * 照範囲　＝　全員
	 * 期待： runtime エラー
	 * 	 */
	@Test
	public void createGeneralRole_ReferenceRange_ALL_EMPLOYEE() {
		NtsAssert.systemError(() -> {
				Role.createGeneralRoll(
					this.roleId, this.contractCode, this.companyId
				,	this.roleCode,	this.roleName
				,	RoleType.PERSONAL_INFO, EmployeeReferenceRange.ALL_EMPLOYEE);	
		});
	}
	
	@Test
	public void createInChargeRole() {
		val roleType = RoleType.EMPLOYMENT;
		
		val inchargelRole = Role.createInChargeRoll(
					this.roleId,	this.contractCode
				,	this.companyId,	this.roleCode
				,	this.roleName,	roleType);
		
		assertThat(inchargelRole.getContractCode()).isEqualTo(this.contractCode);
		assertThat(inchargelRole.getCompanyId()).isEqualTo(this.companyId);
		
		assertThat(inchargelRole.getRoleId()).isEqualTo(this.roleId);
		assertThat(inchargelRole.getRoleCode()).isEqualTo(this.roleCode);
		assertThat(inchargelRole.getName()).isEqualTo(this.roleName);
		assertThat(inchargelRole.getRoleType()).isEqualTo(roleType);
		
		/** 担当区分　＝　担当　**/
		assertThat(inchargelRole.getAssignAtr()).isEqualTo(RoleAtr.INCHARGE);
		assertThat(inchargelRole.getEmployeeReferenceRange()).isEqualTo(EmployeeReferenceRange.ALL_EMPLOYEE);

	}

}