/**
 * 
 */
package find.layoutitemclassification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import find.layoutitemclassification.definition.LayoutPersonInfoClsDefFinder;
import find.person.info.category.PerInfoCategoryFinder;
import find.person.info.category.PerInfoCtgWithItemsNameDto;
import find.person.info.item.PerInfoItemDefDto;
import find.person.info.item.PerInfoItemDefFinder;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.ILayoutPersonInfoClsRepository;

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

		int sizeOflist = listItemCls.size();
		if (sizeOflist > 0) {
			for (int i = 0; i < sizeOflist; i++) {
				LayoutPersonInfoClsDto classDto = listItemCls.get(i);
				switch (classDto.getLayoutItemType()) {
				case 0: // list item
				case 1: // single item
					PerInfoCtgWithItemsNameDto catDto = this.catFinder
							.getPerInfoCtgWithItemsName(classDto.getPersonInfoCategoryID());

					if (catDto != null) {
						classDto.setClassName(catDto.getCategoryName());
					}

					List<String> listId = this.clsItemDefFinder.getItemDefineIds(classDto.getLayoutID(),
							classDto.getDispOrder());

					if (!listId.isEmpty()) {
						List<PerInfoItemDefDto> listItemDef = itemDfFinder.getPerInfoItemDefByListId(listId);
						classDto.setListItemDf(listItemDef);
					}
					break;
				case 2: // SeparatorLine
					break;
				}
			}
		}
		return listItemCls;
	}
}
