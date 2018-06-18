package nts.uk.ctx.pereg.app.find.roles.functionauth;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.roles.functionauth.AuthFullInfoObject;
import nts.uk.ctx.pereg.dom.roles.functionauth.PersonInfoAuthService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class PersonInfoAuthFinder {
	
	@Inject
	private PersonInfoAuthService personInfoAuthSerice;
	
	public List<PersonInfoAuthDto> getListAuth(String roleId) {
		
		if (roleId == null || roleId.equals("")) {
			roleId = AppContexts.user().roles().forPersonalInfo();
		}
		String companyId = AppContexts.user().companyId();
		List<AuthFullInfoObject> authObjectList = personInfoAuthSerice.getFullInfo(companyId, roleId);
		return authObjectList.stream().map( x-> new PersonInfoAuthDto(x)).collect(Collectors.toList());
	}

}
