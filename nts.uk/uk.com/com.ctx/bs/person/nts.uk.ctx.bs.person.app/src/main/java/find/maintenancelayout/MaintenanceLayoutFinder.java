/**
 * 
 */
package find.maintenancelayout;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import find.layoutitemclassification.LayoutPersonInfoClsDto;
import find.layoutitemclassification.LayoutPersonInfoClsFinder;
import nts.uk.ctx.bs.person.dom.person.layout.IMaintenanceLayoutRepository;

/**
 * @author laitv
 *
 */
@Stateless
public class MaintenanceLayoutFinder {

	@Inject
	private IMaintenanceLayoutRepository layoutRepo;

	@Inject
	private LayoutPersonInfoClsFinder ClsFinder;

	public List<MaintenanceLayoutDto> getAllLayout() {
		// get All Maintenance Layout
		return this.layoutRepo.getAllMaintenanceLayout().stream().map(item -> MaintenanceLayoutDto.fromDomain(item))
				.collect(Collectors.toList());
	}

	public MaintenanceLayoutDto getDetails(String layoutId) {
		// get detail maintenanceLayout By Id
		MaintenanceLayoutDto dto = this.layoutRepo.getById(layoutId).map(c -> MaintenanceLayoutDto.fromDomain(c)).get();

		// Get list Classification Item by layoutID
		List<LayoutPersonInfoClsDto> listItemCls = this.ClsFinder.getListClsDto(layoutId);

		dto.setListItemClsDto(listItemCls);

		return dto;

	}
}
