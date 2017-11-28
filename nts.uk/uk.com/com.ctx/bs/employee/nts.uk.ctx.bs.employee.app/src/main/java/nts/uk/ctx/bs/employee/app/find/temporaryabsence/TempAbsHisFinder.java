package nts.uk.ctx.bs.employee.app.find.temporaryabsence;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsHistRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsItemRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHistory;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

/**
 * Temporary absence finder
 */

@Stateless
public class TempAbsHisFinder implements PeregFinder<TempAbsHisItemDto> {

	@Inject
	private TempAbsItemRepository tempAbsItemRepo;

	@Inject
	private TempAbsHistRepository tempAbsHistRepo;

	@Override
	public String targetCategoryCode() {
		return "CS00018";
	}

	@Override
	public Class<TempAbsHisItemDto> dtoClass() {
		return TempAbsHisItemDto.class;
	}
	
	@Override
	public DataClassification dataType() {
		return DataClassification.EMPLOYEE;
	}

	@Override
	public PeregDomainDto getSingleData(PeregQuery query) {
		Optional<TempAbsenceHisItem> optionalData = tempAbsItemRepo.getItemByEmpIdAndReferDate(query.getEmployeeId(),
				query.getStandardDate());
		if (optionalData.isPresent()) {
			TempAbsenceHisItem histItem = optionalData.get();
			TempAbsenceHistory history = tempAbsHistRepo.getByHistId(histItem.getHistoryId()).get();
			return TempAbsHisItemDto.createFromDomain(history, histItem);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.shr.pereg.app.find.PeregFinder#getListData(nts.uk.shr.pereg.app.
	 * find.PeregQuery)
	 */
	@Override
	public List<PeregDomainDto> getListData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
