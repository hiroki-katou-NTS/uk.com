package nts.uk.ctx.basic.app.find.organization.workplace;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.dom.organization.workplace.WorkPlace;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlaceMemo;
import nts.uk.ctx.basic.dom.organization.workplace.WorkPlaceRepository;

@Stateless
public class WorkPlaceFinder {

	@Inject
	private WorkPlaceRepository workPlaceRepository;

	public boolean checkExist(String companyCode) {
		return workPlaceRepository.checkExist(companyCode);
	}

	public List<WorkPlace> findHistories(String companyCode) {
		return workPlaceRepository.findHistories(companyCode);
	}

	public List<WorkPlace> findAllByHistory(String companyCode, String historyId) {
		return workPlaceRepository.findAllByHistory(companyCode, historyId);
	}

	public Optional<WorkPlaceMemo> findMemo(String companyCode, String historyId) {
		return workPlaceRepository.findMemo(companyCode, historyId);
	}

}
