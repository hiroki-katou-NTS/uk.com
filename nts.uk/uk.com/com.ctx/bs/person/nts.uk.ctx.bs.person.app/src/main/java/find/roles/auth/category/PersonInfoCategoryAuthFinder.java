package find.roles.auth.category;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.role.auth.category.PersonInfoCategoryAuthRepository;
import nts.uk.ctx.bs.person.dom.person.role.auth.category.PersonInfoCategoryDetail;

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

	public List<PersonInfoCategoryDetail> getAllCategory(String roleId) {
		return this.personCategoryAuthRepository.getAllCategory(roleId);
	}

	public PersonInfoCategoryAuthDto getDetailPersonCategoryAuthByPId(String personCategoryAuthId) {
		return this.personCategoryAuthRepository.getDetailPersonCategoryAuthByPId(personCategoryAuthId).map(item -> {
			return PersonInfoCategoryAuthDto.fromDomain(item);
		}).orElse(null);
	}
}
