package nts.uk.ctx.sys.portal.dom.adapter.role;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface RoleGrantAdapter {

	Optional<String> getRoleId(String userId);
	
	List<RoleDto> findRole(String roleId);
	
	Optional<UserDto> getUserInfo(String userId);
	
	Optional<RoleSetGrantedPersonDto> getRoleSetPersonGrant(String employeeId);
	
	Optional<AffJobHistoryDto> getAffJobHist(String employeeId, GeneralDate baseDate);
	
	Optional<RoleSetGrantedJobTitleDetailDto> getRoleSetJobTitleGrant(String companyId, String jobTitleId);
	
	Optional<DefaultRoleSetDto> getDefaultRoleSet(String companyId);
}
