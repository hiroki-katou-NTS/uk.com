/**
 * 
 */
package nts.uk.ctx.pereg.app.find.layoutdef;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsFinder;
import nts.uk.ctx.pereg.dom.person.layout.IMaintenanceLayoutRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class MaintenanceLayoutFinder {

	@Inject
	private IMaintenanceLayoutRepository layoutRepo;

	@Inject
	private LayoutPersonInfoClsFinder ClsFinder;

	

	public List<MaintenanceLayoutDto> getAllLayout() {
		String companyId = AppContexts.user().companyId();
		// get All Maintenance Layout
		return this.layoutRepo.getAllMaintenanceLayout(companyId).stream()
				.map(item -> MaintenanceLayoutDto.fromDomain(item)).collect(Collectors.toList());
	}

	public MaintenanceLayoutDto getDetails(String layoutId) {
		String companyId = AppContexts.user().companyId();
		// get detail maintenanceLayout By Id
		MaintenanceLayoutDto dto = this.layoutRepo.getById(companyId, layoutId).map(c -> MaintenanceLayoutDto.fromDomain(c)).get();

		// Get list Classification Item by layoutID
		List<LayoutPersonInfoClsDto> listItemCls = this.ClsFinder.getListClsDto(layoutId);

		dto.setListItemClsDto(listItemCls);

		return dto;

	}
}
