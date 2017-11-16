/**
 * 
 */
package find.groupitem;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;

import find.person.info.item.PerInfoItemDefDto;
import find.person.info.item.PerInfoItemDefFinder;
import nts.uk.ctx.bs.person.dom.person.groupitem.IPersonInfoItemGroupRepository;
import nts.uk.ctx.bs.person.dom.person.groupitem.PersonInfoItemGroup;

@Stateless
public class PersonInfoItemGroupFinder {

	@Inject
	private IPersonInfoItemGroupRepository repo;

	@Inject
	private PerInfoItemDefFinder itemDfFinder;

	/**
	 * Get All GroupItem
	 * 
	 * @return
	 */
	public List<PersonInfoItemGroupDto> getAllPersonInfoGroup() {
		return this.repo.getAll().stream().map(item -> {
			if (getAllItemDf(item.getPersonInfoItemGroupID()).size() > 0) {
				return PersonInfoItemGroupDto.fromDomain(item);
			}
			return null;
		}).filter(m -> m != null).collect(Collectors.toList());
	}

	/**
	 * 
	 * @param groupId
	 * @return
	 */
	public PersonInfoItemGroupDto getById(String groupId) {
		Optional<PersonInfoItemGroup> groupItem = this.repo.getById(groupId);

		if (!groupItem.isPresent()) {
			return null;
		}

		return PersonInfoItemGroupDto.fromDomain(groupItem.get());
	}

	/**
	 * Get All Item Difination
	 * 
	 * @return
	 */
	public List<PerInfoItemDefDto> getAllItemDf(String groupId) {
		List<String> listItemDfId = this.repo.getListItemIdByGrId(groupId);

		if (listItemDfId.isEmpty()) {
			return null;
		} else {
			return itemDfFinder.getPerInfoItemDefByListId(listItemDfId);
		}

	}
}
