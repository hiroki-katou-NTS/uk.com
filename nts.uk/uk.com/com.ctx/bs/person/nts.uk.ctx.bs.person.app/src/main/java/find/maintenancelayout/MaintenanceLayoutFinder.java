/**
 * 
 */
package find.maintenancelayout;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;

import find.layoutitemclassification.LayoutPersonInfoClsDto;
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

		if (listItemCls.size() > 0) {
			listItemCls.forEach(item -> {
					
			});
		}

		dto.setListItemClsDto(listItemCls);

		return dto;
	}
}
