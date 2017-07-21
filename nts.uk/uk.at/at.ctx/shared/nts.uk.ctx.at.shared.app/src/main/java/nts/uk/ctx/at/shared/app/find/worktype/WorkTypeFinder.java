package nts.uk.ctx.at.shared.app.find.worktype;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktype.DisplayAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class WorkTypeFinder {

	@Inject
	private WorkTypeRepository workTypeRepo;

	// user contexts
	String companyId = AppContexts.user().companyId();

	public List<WorkTypeDto> getPossibleWorkType(List<String> lstPossible) {
		List<WorkTypeDto> lst = this.workTypeRepo.getPossibleWorkType(companyId, lstPossible).stream()
				.map(c -> WorkTypeDto.fromDomain(c)).collect(Collectors.toList());
		return lst;
	}

	/**
	 * Find by company id.
	 *
	 * @return the list
	 */
	public List<WorkTypeDto> findByCompanyId() {
		return this.workTypeRepo.findByCompanyId(companyId).stream().map(c -> WorkTypeDto.fromDomain(c))
				.collect(Collectors.toList());
	}

	/**
	 * Find by companyId and displayAtr = DISPLAY (added by sonnh1)
	 * 
	 * @return List WorkTypeDto
	 */
	public List<WorkTypeDto> findByCIdAndDisplayAtr() {
		return this.workTypeRepo.findByCIdAndDisplayAtr(companyId, DisplayAtr.DisplayAtr_Display.value).stream()
				.map(c -> WorkTypeDto.fromDomain(c)).collect(Collectors.toList());
	}
}
