package nts.uk.ctx.pereg.infra.repository.roles.functionauth;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;

import nts.uk.ctx.pereg.dom.roles.functionauth.authsetting.PersonInfoAuthority;
import nts.uk.ctx.pereg.dom.roles.functionauth.authsetting.PersonInfoAuthorityRepository;

@Stateless
public class PersonInfoAuthorityRepoImpl implements PersonInfoAuthorityRepository{

	@Override
	public Map<Integer, PersonInfoAuthority> getListOfRole(String companyId, String roleId) {
		Map<Integer, PersonInfoAuthority> authList = new HashMap<>();
		authList.put(1, PersonInfoAuthority.createFromJavaType(companyId, roleId, 1, true));
		authList.put(2, PersonInfoAuthority.createFromJavaType(companyId, roleId, 2, true));
		authList.put(3, PersonInfoAuthority.createFromJavaType(companyId, roleId, 3, true));
		authList.put(4, PersonInfoAuthority.createFromJavaType(companyId, roleId, 4, true));
		return authList;
	}

	@Override
	public void add(PersonInfoAuthority auth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(PersonInfoAuthority auth) {
		// TODO Auto-generated method stub
		
	}

}
