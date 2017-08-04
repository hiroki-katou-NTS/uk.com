package find.roles.auth.category;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.role.auth.category.PersonInfoCategoryAuthRepository;

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
		return this.personCategoryAuthRepository.getAllCategory(roleId).stream()
				.map(x -> PersonInfoCategoryDetailDto.fromDomain(x)).collect(Collectors.toList());

	}

	public PersonInfoCategoryAuthDto getDetailPersonCategoryAuthByPId(String personCategoryAuthId) {
		return this.personCategoryAuthRepository.getDetailPersonCategoryAuthByPId(personCategoryAuthId).map(item -> {
			return PersonInfoCategoryAuthDto.fromDomain(item);
		}).orElse(null);
	}
}
