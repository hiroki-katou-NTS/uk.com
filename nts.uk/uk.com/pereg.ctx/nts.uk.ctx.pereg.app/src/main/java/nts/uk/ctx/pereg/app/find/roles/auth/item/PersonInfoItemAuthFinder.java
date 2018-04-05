package nts.uk.ctx.pereg.app.find.roles.auth.item;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.app.find.person.info.item.ItemRequiredBackGroud;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefFinder;
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
	
	@Inject
	private PerInfoItemDefFinder itemDefFinder;

	public ItemAuth getAllItemDetail(String roleId, String personCategoryAuthId) {
		List<PersonInfoItemDetailDto> itemDto = this.personItemAuthRepository
				.getAllItemDetail(roleId, personCategoryAuthId, AppContexts.user().contractCode()).stream()
				.map(item -> PersonInfoItemDetailDto.createDto(item)).collect(Collectors.toList());
		List<PersonInfoItemDetailDto> itemAuth = itemDto.stream()
				.filter(c -> (c.getDataType() != 9))
				.collect(Collectors.toList());
		
		List<ItemReadOnly> itemRead = itemDto.stream()
				.filter(c -> (c.getDataType() == 9))
				.map(c -> new ItemReadOnly(c.getPersonItemDefId(), Arrays.asList("1","2")))
				.collect(Collectors.toList());
		List<ItemRequiredBackGroud>  itemRequired = this.itemDefFinder.getAllItemRequiredIdsByCtgId(personCategoryAuthId);
		return  new ItemAuth(itemDto, itemAuth , itemRead, itemRequired);
	}

}
