/**
 * 
 */
package find.layout.classification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import find.layout.classification.definition.LayoutPersonInfoClsDefFinder;
import find.person.info.category.PerInfoCategoryFinder;
import find.person.info.category.PerInfoCtgWithItemsNameDto;
import find.person.info.item.PerInfoItemDefDto;
import find.person.info.item.PerInfoItemDefFinder;
import nts.uk.ctx.bs.person.dom.person.layout.classification.ILayoutPersonInfoClsRepository;
import nts.uk.ctx.bs.person.dom.person.layout.classification.LayoutItemType;

@Stateless
public class LayoutPersonInfoClsFinder {

	@Inject
	private ILayoutPersonInfoClsRepository itemClsRepo;

	@Inject
	private PerInfoItemDefFinder itemDfFinder;

	@Inject
	private PerInfoCategoryFinder catFinder;

	@Inject
	private LayoutPersonInfoClsDefFinder clsItemDefFinder;

	public List<LayoutPersonInfoClsDto> getListClsDto(String layoutId) {

		List<LayoutPersonInfoClsDto> listItemCls = itemClsRepo.getAllByLayoutId(layoutId).stream()
				.map(item -> LayoutPersonInfoClsDto.fromDomain(item)).collect(Collectors.toList());

		if (listItemCls.size() > 0) {
			for (LayoutPersonInfoClsDto classDto : listItemCls) {
				switch (classDto.getLayoutItemType()) {
				case ITEM: // single item
				case LIST: // list item

					List<String> listId = this.clsItemDefFinder.getItemDefineIds(classDto.getLayoutID(),
							classDto.getDispOrder());

					if (!listId.isEmpty()) {
						List<PerInfoItemDefDto> listItemDefDto = new ArrayList<PerInfoItemDefDto>();

						List<PerInfoItemDefDto> listItemDef = itemDfFinder.getPerInfoItemDefByListIdForLayout(listId);

						for (String id : listId) {
							List<PerInfoItemDefDto> dto = listItemDef.stream().filter(p -> p.getId().equals(id))
									.collect(Collectors.toList());

							if (!dto.isEmpty()) {
								listItemDefDto.add(dto.get(0));
							}
						}

						classDto.setListItemDf(listItemDefDto);

						if (classDto.getLayoutItemType() == LayoutItemType.ITEM && !listItemDefDto.isEmpty()
								&& listItemDefDto.get(0) != null) {
							classDto.setClassName(listItemDefDto.get(0).getItemName());
						}
					}

					if (classDto.getLayoutItemType() == LayoutItemType.LIST) {
						PerInfoCtgWithItemsNameDto catDto = this.catFinder
								.getPerInfoCtgWithItemsName(classDto.getPersonInfoCategoryID());

						if (catDto != null) {
							classDto.setClassName(catDto.getCategoryName());
						}
					}
					break;
				case SeparatorLine: // SeparatorLine
					break;
				}
			}
		}
		return listItemCls.stream()
				.filter(m -> (m.getLayoutItemType() != LayoutItemType.SeparatorLine && !m.getListItemDf().isEmpty())
						|| m.getLayoutItemType() == LayoutItemType.SeparatorLine)
				.collect(Collectors.toList());
	}
}
