package nts.uk.ctx.pereg.app.find.roles.auth;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.roles.auth.PersonInfoRoleAuthRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class PersonInfoRoleAuthFinder
 * 
 * @author lanlt
 *
 */
@Stateless
public class PersonInfoRoleAuthFinder {
	@Inject
	private PersonInfoRoleAuthRepository personRoleAuthRepository;

	public List<PersonInfoRoleAuthDto> getAllPersonInfoRoleAuth() {
		return this.personRoleAuthRepository.getAllPersonInfoRoleAuth().stream()
				.map(item -> PersonInfoRoleAuthDto.fromDomain(item)).collect(Collectors.toList());
	}

	public Optional<PersonInfoRoleAuthDto> getDetailPersonRoleAuth(String roleId) {
		return this.personRoleAuthRepository.getDetailPersonRoleAuth(roleId, AppContexts.user().companyId())
				.map(c -> PersonInfoRoleAuthDto.fromDomain(c));
	}
}
