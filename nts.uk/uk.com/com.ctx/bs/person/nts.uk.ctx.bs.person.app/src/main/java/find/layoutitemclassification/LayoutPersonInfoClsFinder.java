/**
 * 
 */
package find.layoutitemclassification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

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

	public List<LayoutPersonInfoClsDto> getListClsDto(String layoutId) {
		
		List<LayoutPersonInfoClsDto> listItemCls = this.itemClsRepo.getAllByLayoutId(layoutId).stream()
				.map(item -> LayoutPersonInfoClsDto.fromDomain(item)).collect(Collectors.toList());
		
		int sizeOflist = listItemCls.size();
		if (sizeOflist > 0) {
			for (int i = 0; i < sizeOflist; i++) {
				LayoutPersonInfoClsDto classDto = listItemCls.get(i);
				switch (classDto.getLayoutItemType()) {
				case 0:
					/*
					 * is item lấy id của itemdefine rồi dùng hàm sau (PerInfoItemDefFinder)
					 * itemDefFinder.getPerInfoItemDefById(Id); để lấy ra 1 item rồi đẩy vào
					 * item.listItemDf
					 */

					String itemId = this.itemClsRepo.getOneItemDfId(classDto.getLayoutID(),
							String.valueOf(classDto.getDispOrder()));

					if (itemId != null) {
						PerInfoItemDefDto itemDef = itemDfFinder.getPerInfoItemDefById(itemId);

						classDto.setClassName(itemDef.getItemName());

						List<PerInfoItemDefDto> _list = new ArrayList<>();
						_list.add(itemDef);
						classDto.setListItemDf(_list);
					}
					break;
				case 1:
					/*
					 * is listItems lấy ra các ids của itemdefine rồi dùng hàm sau
					 * (PerInfoItemDefFinder) itemDefFinder.getPerInfoItemDefByListId(listItemDefId)
					 * để lấy ra các itemdefine và đẩy vào item.listItemDf
					 */

					// list items -> className = categoy name
					PerInfoCtgWithItemsNameDto catDto = this.catFinder
							.getPerInfoCtgWithItemsName(classDto.getPersonInfoCategoryID());
					if (catDto != null) {
						classDto.setClassName(catDto.getCategoryName());
					}

					List<String> listId = this.itemClsRepo.getAllItemDefIdByLayoutId(listItemCls.get(i).getLayoutID(),
							String.valueOf(listItemCls.get(i).getDispOrder()));
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
