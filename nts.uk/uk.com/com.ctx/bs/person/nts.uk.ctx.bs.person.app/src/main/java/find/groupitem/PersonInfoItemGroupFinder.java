/**
 * 
 */
package find.groupitem;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.bs.person.dom.person.groupitem.IPersonInfoItemGroupRepository;
import nts.uk.ctx.bs.person.dom.person.groupitem.PersonInfoItemGroup;


@Stateless
public class PersonInfoItemGroupFinder {

	@Inject
	private IPersonInfoItemGroupRepository repo;

	/**
	 * Get All GroupItem
	 * @return
	 */
	public List<PersonInfoItemGroupDto> getAllPersonInfoGroup() {
		List<PersonInfoItemGroupDto> list =  this.repo.getAll().stream().map(item -> PersonInfoItemGroupDto.fromDomain(item))
				.collect(Collectors.toList());
		return list;
	}

	
	/**
	 * 
	 * @param groupId
	 * @return
	 */
	public PersonInfoItemGroupDto getById(String groupId) {
		Optional<PersonInfoItemGroup> groupItem = this.repo.getById(groupId);

		if (groupItem.isPresent()) {
			PersonInfoItemGroup _groupItem = groupItem.get();
			// get classifications

			return PersonInfoItemGroupDto.fromDomain(_groupItem);
		} else {
			return null;
		}

	}
}
