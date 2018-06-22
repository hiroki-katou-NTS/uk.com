package nts.uk.ctx.pereg.dom.roles.functionauth.authsetting;

import java.util.Map;

import nts.uk.shr.com.permit.AvailabilityPermissionRepositoryBase;

public interface PersonInfoAuthorityRepository extends AvailabilityPermissionRepositoryBase<PersonInfoAuthority>{
	
	Map<Integer, PersonInfoAuthority> getListOfRole(String companyId, String roleId);
	
	void delete(String companyId, String roleId);
	
}
