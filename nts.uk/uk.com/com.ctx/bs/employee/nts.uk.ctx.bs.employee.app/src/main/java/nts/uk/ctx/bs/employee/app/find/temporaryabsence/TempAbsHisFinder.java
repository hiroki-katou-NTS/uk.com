package nts.uk.ctx.bs.employee.app.find.temporaryabsence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsHistRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsItemRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHistory;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.pereg.app.ComboBoxObject;
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
		Optional<TempAbsenceHisItem> histItemOpt;
		Optional<DateHistoryItem> historyOpt;
		if (query.getInfoId() != null) {
			historyOpt = tempAbsHistRepo.getByHistId(query.getInfoId());
		} else {
			historyOpt = tempAbsHistRepo.getItemByEmpIdAndStandardDate(query.getEmployeeId(), query.getStandardDate());

		}
		if (historyOpt.isPresent()) {
			histItemOpt = tempAbsItemRepo.getItemByHitoryID(historyOpt.get().identifier());
			return TempAbsHisItemDto.createFromDomain(historyOpt.get(), histItemOpt.get());
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

	@Override
	public List<ComboBoxObject> getListFirstItems(PeregQuery query) {

		Optional<TempAbsenceHistory> history = tempAbsHistRepo.getByEmployeeIdDesc(AppContexts.user().companyId(),
				query.getEmployeeId());
		if (history.isPresent()) {
			return history.get().getDateHistoryItems().stream()
					.filter(item -> tempAbsItemRepo.getItemByHitoryID(item.identifier()).isPresent())
					.map(x -> ComboBoxObject.toComboBoxObject(x.identifier(), x.start().toString(), 
							x.end().equals(GeneralDate.max()) 
							//&& query.getCtgType() == 3 
							? "" : x.end().toString()))
					.collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

}
