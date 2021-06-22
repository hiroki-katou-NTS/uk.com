package nts.uk.ctx.sys.auth.dom.roleset;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class RoleSetTest {
	
	@Test
	public void createRoleSet() {
		String cid = "cid";
		String roleSetCd = "01";
		String roleSetName = "roleSet";
		String attendanceRoleId = "attendanceRoleId";
		String personInfoRoleId = "personalInfoRoleId";
		RoleSet roleSet = RoleSet.create(cid, roleSetCd, roleSetName, attendanceRoleId, personInfoRoleId);
		assertThat(roleSet.getCompanyId()).isEqualTo(cid);
		assertThat(roleSet.getRoleSetCd().v()).isEqualTo(roleSetCd);
		assertThat(roleSet.getRoleSetName().v()).isEqualTo(roleSetName);
		assertThat(roleSet.getApprovalAuthority()).isEqualTo(ApprovalAuthority.HasRight );
		assertThat(roleSet.getPersonInfRoleId().get()).isEqualTo(personInfoRoleId);
		assertThat(roleSet.getEmploymentRoleId().get()).isEqualTo(attendanceRoleId);
		assertThat(roleSet.getHRRoleId()).isEmpty();
		assertThat(roleSet.getMyNumberRoleId()).isEmpty();
		assertThat(roleSet.getOfficeHelperRoleId()).isEmpty();
		assertThat(roleSet.getSalaryRoleId()).isEmpty();
		
	} 

}
