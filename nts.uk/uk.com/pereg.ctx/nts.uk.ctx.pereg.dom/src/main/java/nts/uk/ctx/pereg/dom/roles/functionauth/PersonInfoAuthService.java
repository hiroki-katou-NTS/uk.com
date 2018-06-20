package nts.uk.ctx.pereg.dom.roles.functionauth;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.roles.functionauth.authsetting.PersonInfoAuthority;
import nts.uk.ctx.pereg.dom.roles.functionauth.authsetting.PersonInfoAuthorityRepository;
import nts.uk.ctx.pereg.dom.roles.functionauth.authsettingdesc.PerInfoAuthDescRepository;
import nts.uk.ctx.pereg.dom.roles.functionauth.authsettingdesc.PersonInfoAuthDescription;

@Stateless
public class PersonInfoAuthService {

	@Inject
	private PersonInfoAuthorityRepository personInfoAuth;

	@Inject
	private PerInfoAuthDescRepository personInfoAuthDesc;

	public List<AuthFullInfoObject> getFullInfo(String companyId, String roleId) {
		
		List<PersonInfoAuthDescription> descriptionList = personInfoAuthDesc.getListDesc();
		if (roleId == null) {
			// roleId == null. get with default value
			return descriptionList.stream().map(desc -> new AuthFullInfoObject(desc)).collect(Collectors.toList());
		}
		
		// roleId # null. get with value in database
		Map<Integer, PersonInfoAuthority> authorityList = personInfoAuth.getListOfRole(companyId, roleId);
		return descriptionList.stream().map(desc -> {
			PersonInfoAuthority auth = authorityList.get(desc.getFunctionNo().v());
			if (auth != null) {
				return new AuthFullInfoObject(desc, auth);
			} else {
				return new AuthFullInfoObject(desc);
			}
		}).collect(Collectors.toList());

	}

}
