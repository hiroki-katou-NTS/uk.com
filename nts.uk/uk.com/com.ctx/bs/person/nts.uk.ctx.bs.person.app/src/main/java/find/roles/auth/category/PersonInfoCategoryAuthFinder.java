package find.roles.auth.category;

import java.util.List;
import java.util.Optional;
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

	public List<PersonInfoCategoryAuthDto> getAllPersonCategoryAuth() {
		return this.personCategoryAuthRepository.getAllPersonCategoryAuth()
				.stream()
				.map(item -> PersonInfoCategoryAuthDto.fromDomain(item))
				.collect(Collectors.toList());
	}

	public List<PersonInfoCategoryAuthDto> getAllPersonCategoryAuthByRoleId(String roleId) {
		return this.personCategoryAuthRepository.getAllPersonCategoryAuthByRoleId(roleId)
				.stream()
				.map(item -> PersonInfoCategoryAuthDto.fromDomain(item))
				.collect(Collectors.toList());
	}

	public Optional<PersonInfoCategoryAuthDto> getDetailPersonCategoryAuth(String roleId, String personCategoryAuthId) {
		return this.personCategoryAuthRepository.getDetailPersonCategoryAuth(roleId,personCategoryAuthId)
				.map(c -> PersonInfoCategoryAuthDto.fromDomain(c));
	}
}
