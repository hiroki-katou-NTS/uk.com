package find.roles.auth.item;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.role.auth.item.PersonInfoItemAuthRepository;

/**
 * The Class PersonInfoItemAuthFinder
 * @author lanlt
 *
 */
@Stateless
public class PersonInfoItemAuthFinder {
	@Inject
	private PersonInfoItemAuthRepository personItemAuthRepository;
	public List<PersonInfoItemAuthDto> getAllPersonItemAuth(){
		return this.personItemAuthRepository.getAllPersonItemAuth()
				.stream()
				.map(item -> PersonInfoItemAuthDto.fromDomain(item))
				.collect(Collectors.toList());
		
	}
	public List<PersonInfoItemAuthDto> getAllPersonItemAuthByCategory(String roleId, String personCategoryAuthId){
		return this.personItemAuthRepository.getAllPersonItemAuthByCategory(roleId, personCategoryAuthId)
				.stream()
				.map(item -> PersonInfoItemAuthDto.fromDomain(item))
				.collect(Collectors.toList());
	}
	
	public Optional<PersonInfoItemAuthDto> getDetailPersonItemAuth(String roleId, String personCategoryAuthId,
			String personItemDefId){
		return this.personItemAuthRepository.getDetailPersonItemAuth(roleId, personCategoryAuthId,personItemDefId)
				.map(item -> PersonInfoItemAuthDto.fromDomain(item));
	}
}
