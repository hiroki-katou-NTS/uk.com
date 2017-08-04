/**
 * 
 */
package find.maintenancelayout;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.maintenancelayout.MaintenanceLayoutRepository;

/**
 * @author laitv
 *
 */
@Stateless
public class MaintenanceLayoutFinder {

	@Inject
	private MaintenanceLayoutRepository layoutRepo;

	public List<MaintenanceLayoutDto> getAllLayout() {
		// get All Maintenance Layout
		return this.layoutRepo.getAllMaintenanceLayout().stream().map(item -> MaintenanceLayoutDto.fromDomain(item))
				.collect(Collectors.toList());
	}

	public MaintenanceLayoutDto getDetails(String layoutId) {
		return null;
	}
}
