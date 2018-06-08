package nts.uk.ctx.pereg.infra.repository.roles.functionauth;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.pereg.dom.roles.functionauth.authsetting.PersonInfoAuthority;
import nts.uk.ctx.pereg.dom.roles.functionauth.authsetting.PersonInfoAuthorityRepository;

@Stateless
public class PersonInfoAuthorityRepoImpl implements PersonInfoAuthorityRepository{

	@Override
	public List<PersonInfoAuthority> getListOfRole(String companyId, String roleId) {
		List<PersonInfoAuthority> authList = new ArrayList<>();
		authList.add(PersonInfoAuthority.createFromJavaType(companyId, roleId, 1, true));
		authList.add(PersonInfoAuthority.createFromJavaType(companyId, roleId, 2, true));
		authList.add(PersonInfoAuthority.createFromJavaType(companyId, roleId, 3, true));
		authList.add(PersonInfoAuthority.createFromJavaType(companyId, roleId, 4, true));
		authList.add(PersonInfoAuthority.createFromJavaType(companyId, roleId, 5, true));
		authList.add(PersonInfoAuthority.createFromJavaType(companyId, roleId, 6, true));
		authList.add(PersonInfoAuthority.createFromJavaType(companyId, roleId, 7, true));
		authList.add(PersonInfoAuthority.createFromJavaType(companyId, roleId, 8, true));
		authList.add(PersonInfoAuthority.createFromJavaType(companyId, roleId, 9, true));
		authList.add(PersonInfoAuthority.createFromJavaType(companyId, roleId, 10, true));
		return authList;
	}

}
