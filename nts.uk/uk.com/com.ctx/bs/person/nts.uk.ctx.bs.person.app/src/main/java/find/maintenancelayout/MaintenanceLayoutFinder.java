/**
 * 
 */
package find.maintenancelayout;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
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
	private MaintenanceLayoutRepository maintenanceLayoutRepository;

	public List<MaintenanceLayoutDto> getAllLayout() {
		return this.maintenanceLayoutRepository.getAllMaintenanceLayout()
				.stream()
				.map(item -> MaintenanceLayoutDto.fromDomain(item)).collect(Collectors.toList());
	}
	
	public Optional<MaintenanceLayoutDto> getDetailLayout(String layoutId){
		return this.maintenanceLayoutRepository.getDetailMaintenanceLayout(layoutId)
				.map(item -> MaintenanceLayoutDto.fromDomain(item));
	}

}
