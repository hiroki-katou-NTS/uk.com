/**
 * 
 */
package find.maintenancelayout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import find.layoutitemclassification.LayoutPersonInfoClsDto;
import find.person.info.item.PerInfoItemDefDto;
import nts.uk.ctx.bs.person.dom.person.info.item.PernfoItemDefRepositoty;
import nts.uk.ctx.bs.person.dom.person.layoutitemclassification.LayoutPersonInfoClsRepository;
import nts.uk.ctx.bs.person.dom.person.maintenancelayout.MaintenanceLayoutRepository;

/**
 * @author laitv
 *
 */
@Stateless
public class MaintenanceLayoutFinder {

	@Inject
	private MaintenanceLayoutRepository maintenanceLayoutRepository;

	@Inject
	private LayoutPersonInfoClsRepository layoutPersonInfoClsRepository;
	
//	@Inject
//	private PernfoItemDefRepositoty itemDefRepositoty;

	public List<MaintenanceLayoutDto> getAllLayout() {

		List<MaintenanceLayoutDto> listLayoutDto = new ArrayList<>();

		// get All Maintenance Layout
		listLayoutDto = this.maintenanceLayoutRepository.getAllMaintenanceLayout().stream()
				.map(item -> MaintenanceLayoutDto.fromDomain(item)).collect(Collectors.toList());

		// get all ItemClassification have LayoutID = listLayoutDto[0].layoutId
		if (listLayoutDto.size() > 0) {
			String layoutID = listLayoutDto.get(0).maintenanceLayoutID;

			List<LayoutPersonInfoClsDto> listItemCls = new ArrayList<>();

			listItemCls = this.layoutPersonInfoClsRepository.getAllItemCls(layoutID).stream()
					.map(item -> LayoutPersonInfoClsDto.fromDomain(item)).collect(Collectors.toList());
			
			// get List ItemDifination cho mỗi ItemClassification
			
			List<PerInfoItemDefDto> listItemClsDf = new ArrayList<>();
			
			listItemCls.forEach(
						item ->{
						
													
						});
			
			
			listLayoutDto.get(0).setListItemClsDto(listItemCls);
			
			// 
			
		}

		return listLayoutDto;

	}
}
