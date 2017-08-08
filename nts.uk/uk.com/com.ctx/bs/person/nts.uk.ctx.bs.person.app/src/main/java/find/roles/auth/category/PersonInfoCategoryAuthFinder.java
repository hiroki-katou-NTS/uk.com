package find.roles.auth.category;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.role.auth.category.PersonInfoCategoryAuth;
import nts.uk.ctx.bs.person.dom.person.role.auth.category.PersonInfoCategoryAuthRepository;
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
		return this.personCategoryAuthRepository.getAllCategory(roleId, AppContexts.user().contractCode()).stream()
				.map(x -> PersonInfoCategoryDetailDto.fromDomain(x)).collect(Collectors.toList());

	}

	public PersonInfoCategoryAuthDto getDetailPersonCategoryAuthByPId(String personCategoryAuthId) {
		Optional<PersonInfoCategoryAuth> opt = this.personCategoryAuthRepository
				.getDetailPersonCategoryAuthByPId(personCategoryAuthId);
		if (!opt.isPresent())
			return null;
		return opt.map(c -> PersonInfoCategoryAuthDto.fromDomain(c)).get();

	}
}
