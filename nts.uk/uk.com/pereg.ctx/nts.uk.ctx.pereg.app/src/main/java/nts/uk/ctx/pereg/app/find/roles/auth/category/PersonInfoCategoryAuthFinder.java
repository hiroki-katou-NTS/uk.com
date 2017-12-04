package nts.uk.ctx.pereg.app.find.roles.auth.category;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuth;
import nts.uk.ctx.pereg.dom.roles.auth.category.PersonInfoCategoryAuthRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class PersonInfoCategoryAuthFinder
 * 
 * @author lanlt
 *
 */
@Stateless
public class PersonInfoCategoryAuthFinder {
	@Inject
	private PersonInfoCategoryAuthRepository personCategoryAuthRepository;

	public List<PersonInfoCategoryDetailDto> getAllCategory(String roleId) {
		return this.personCategoryAuthRepository
				.getAllCategory(roleId, AppContexts.user().contractCode(), AppContexts.user().companyId()).stream()
				.map(x -> PersonInfoCategoryDetailDto.fromDomain(x)).collect(Collectors.toList());

	}
	
	public List<PersonInfoCategoryAuthDto> getAllCategoryAuth(String roleId) {
		return this.personCategoryAuthRepository
				.getAllCategoryAuthByRoleId(roleId).stream()
				.map(x -> PersonInfoCategoryAuthDto.fromDomain(x)).collect(Collectors.toList());

	}

	public PersonInfoCategoryAuthDto getDetailPersonCategoryAuthByPId(String roleId, String personCategoryAuthId) {
		Optional<PersonInfoCategoryAuth> opt = this.personCategoryAuthRepository
				.getDetailPersonCategoryAuthByPId(roleId, personCategoryAuthId);
		if (!opt.isPresent())
			return null;
		return opt.map(c -> PersonInfoCategoryAuthDto.fromDomain(c)).get();

	}

}
