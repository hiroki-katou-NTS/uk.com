package nts.uk.ctx.basic.dom.organization.workplace;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class WorkPlaceDomainService {

	@Inject
	private WorkPlaceRepository workPlaceRepository;

	public void add(WorkPlace workPlace) {
		if (workPlace.getHierarchyCode().toString().trim().length() / 3 > 10) {
			// throw error
		}
		if (workPlaceRepository.isDuplicateWorkPlaceCode(workPlace.getCompanyCode(), workPlace.getWorkPlaceCode())) {
			// throw error
		}
		workPlaceRepository.add(workPlace);
	}

	public void update(WorkPlace workPlace) {
		workPlaceRepository.update(workPlace);
	}

	public void remove(String companyCode, WorkPlaceCode workPlaceCode, String historyId) {
		workPlaceRepository.remove(companyCode, workPlaceCode, historyId);
	}

}
