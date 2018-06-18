package nts.uk.ctx.pereg.dom.roles.functionauth.authsetting;

import java.util.Map;

public interface PersonInfoAuthorityRepository {
	
	Map<Integer, PersonInfoAuthority> getListOfRole(String companyId, String roleId);
	
	void add(PersonInfoAuthority auth);
	
	void update(PersonInfoAuthority auth);

}
