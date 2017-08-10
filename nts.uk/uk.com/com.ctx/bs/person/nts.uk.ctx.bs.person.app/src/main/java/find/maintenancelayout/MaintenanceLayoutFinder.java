/**
 * 
 */
package find.maintenancelayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;

import find.layoutitemclassification.LayoutPersonInfoClsDto;
import find.person.info.item.PerInfoItemDefDto;
import find.person.info.item.PerInfoItemDefFinder;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.ILayoutPersonInfoClsRepository;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.LayoutPersonInfoClassification;
import nts.uk.ctx.bs.person.dom.person.maintenancelayout.IMaintenanceLayoutRepository;

/**
 * @author laitv
 *
 */
@Stateless
public class MaintenanceLayoutFinder {

	@Inject
	private IMaintenanceLayoutRepository layoutRepo;

	@Inject
	private ILayoutPersonInfoClsRepository itemClsRepo;

	@Inject
	private PerInfoItemDefFinder itemDfFinder;

	public List<MaintenanceLayoutDto> getAllLayout() {
		// get All Maintenance Layout
		return this.layoutRepo.getAllMaintenanceLayout().stream().map(item -> MaintenanceLayoutDto.fromDomain(item))
				.collect(Collectors.toList());
	}

	public MaintenanceLayoutDto getDetails(String layoutId) {
		// get detail maintenanceLayout By Id
		MaintenanceLayoutDto dto = this.layoutRepo.getById(layoutId).map(c -> MaintenanceLayoutDto.fromDomain(c)).get();

		// Get list Classification Item by layoutID
		List<LayoutPersonInfoClsDto> listItemCls = this.itemClsRepo.getAllItemClsById(layoutId).stream()
				.map(item -> LayoutPersonInfoClsDto.fromDomain(item)).collect(Collectors.toList());
		int sizeOflist = listItemCls.size();
		if (sizeOflist > 0) {
			for (int i = 0; i < sizeOflist; i++) {
				switch (listItemCls.get(i).getLayoutItemType()) {
				case 0:  
						/* is listItems
						 * lấy ra các ids của itemdefine rồi dùng hàm sau (PerInfoItemDefFinder)
						 * itemDefFinder.getPerInfoItemDefByListId(listItemDefId) để lấy ra các
						 * itemdefine và đẩy vào item.listItemDf
						 */
					List<String> listId = this.itemClsRepo.getAllItemDefIdByLayoutId(listItemCls.get(i).getLayoutID(),
							String.valueOf(listItemCls.get(i).getDispOrder()));
					if (!listId.isEmpty()) {
						List<PerInfoItemDefDto> listItemDef = itemDfFinder.getPerInfoItemDefByListId(listId);
						listItemCls.get(i).setListItemDf(listItemDef);
					}
					break;
				case 1: 
						/* is  item
						 * lấy id của itemdefine rồi dùng hàm sau (PerInfoItemDefFinder)
						 * itemDefFinder.getPerInfoItemDefById(Id); để lấy ra 1 item rồi đẩy vào
						 * item.listItemDf
						 */

					String itemId = this.itemClsRepo.getOneItemDfId(listItemCls.get(i).getLayoutID(),
							String.valueOf(listItemCls.get(i).getDispOrder()));

					if (itemId != null) {
						PerInfoItemDefDto itemDef = itemDfFinder.getPerInfoItemDefById(itemId);
						List<PerInfoItemDefDto> _list = new ArrayList<>();
						_list.add(itemDef);
						listItemCls.get(i).setListItemDf(_list);
					}
					break;

				case 2: // SeparatorLine
					break;
				}
			}
		}

		dto.setListItemClsDto(listItemCls);

		return dto;
	}
}
