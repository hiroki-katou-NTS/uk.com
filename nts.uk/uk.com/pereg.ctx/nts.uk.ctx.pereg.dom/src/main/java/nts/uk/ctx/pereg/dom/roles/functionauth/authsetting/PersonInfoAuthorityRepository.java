package nts.uk.ctx.pereg.dom.roles.functionauth.authsetting;

import java.util.List;

public interface PersonInfoAuthorityRepository {
	
	List<PersonInfoAuthority> getListOfRole(String companyId, String roleId);

}
