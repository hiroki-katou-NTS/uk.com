package nts.uk.ctx.pereg.app.find.roles.auth.item;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.roles.auth.item.PersonInfoItemAuthRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class PersonInfoItemAuthFinder
 * 
 * @author lanlt
 *
 */
@Stateless
public class PersonInfoItemAuthFinder {
	@Inject
	private PersonInfoItemAuthRepository personItemAuthRepository;

	public List<PersonInfoItemDetailDto> getAllItemDetail(String roleId, String personCategoryAuthId) {
		return this.personItemAuthRepository
				.getAllItemDetail(roleId, personCategoryAuthId, AppContexts.user().contractCode()).stream()
				.map(item -> PersonInfoItemDetailDto.createDto(item)).collect(Collectors.toList());
	}

}
