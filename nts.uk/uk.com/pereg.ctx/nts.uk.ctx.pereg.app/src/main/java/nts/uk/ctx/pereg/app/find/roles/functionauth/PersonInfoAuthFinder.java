package nts.uk.ctx.pereg.app.find.roles.functionauth;

import java.util.List;
import java.util.Optional;
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

	public List<PersonInfoAuthDto> getListAuth() {

		String roleId = AppContexts.user().roles().forPersonalInfo();
		String companyId = AppContexts.user().companyId();

		List<AuthFullInfoObject> authObjectList = personInfoAuthSerice.getFullInfo(companyId, roleId);
		return authObjectList.stream().map(x -> new PersonInfoAuthDto(x)).collect(Collectors.toList());
	}

	public List<PersonInfoAuthDto> getListAuthWithRole(String roleId) {
		String companyId = AppContexts.user().companyId();
		List<AuthFullInfoObject> authObjectList = personInfoAuthSerice.getFullInfo(companyId, roleId);
		return authObjectList.stream().map(x -> new PersonInfoAuthDto(x)).collect(Collectors.toList());
	}
	
	public PersonInfoAuthDto getAllFunctionAuthByPersonInfo() {
		String companyId = AppContexts.user().companyId();
		String roleId = AppContexts.user().roles().forPersonalInfo();
		Optional<AuthFullInfoObject> authObjectList = personInfoAuthSerice.getFullInfo(companyId, roleId).stream().filter(c -> c.getFunctionNo() == 11).findFirst();
		return authObjectList.isPresent() == true? new PersonInfoAuthDto(authObjectList.get()): null;
	}

}
