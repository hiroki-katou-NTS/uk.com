/**
 * 
 */
package nts.uk.ctx.bs.employee.app.find.classification.affiliate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1.AffClassHistItemRepository_ver1;
import nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1.AffClassHistItem_ver1;
import nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1.AffClassHistoryRepository_ver1;
import nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1.AffClassHistory_ver1;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

/**
 * @author danpv
 *
 */
@Stateless
public class AffClassificationFinder implements PeregFinder<AffClassificationDto> {

	@Inject
	private AffClassHistoryRepository_ver1 affClassHistRepo;

	@Inject
	private AffClassHistItemRepository_ver1 affClassHistItemRepo;

	@Override
	public String targetCategoryCode() {
		return "CS00004";
	}

	@Override
	public Class<AffClassificationDto> dtoClass() {
		return AffClassificationDto.class;
	}

	@Override
	public DataClassification dataType() {
		return DataClassification.EMPLOYEE;
	}

	@Override
	public AffClassificationDto getSingleData(PeregQuery query) {
		Optional<AffClassHistItem_ver1> histItem;
		Optional<DateHistoryItem> history;
		if (query.getInfoId() != null) {
			history = affClassHistRepo.getByHistoryId(query.getInfoId());
		} else {
			history = affClassHistRepo.getByEmpIdAndStandardDate(query.getEmployeeId(), query.getStandardDate());
		}
		if (history.isPresent()) {
			histItem = affClassHistItemRepo.getByHistoryId(history.get().identifier());
			if (histItem.isPresent()) {
				return AffClassificationDto.createFromDomain(histItem.get(), history.get());
			}
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

		Optional<AffClassHistory_ver1> affClassHistory = affClassHistRepo
				.getByEmployeeIdDesc(AppContexts.user().companyId(), query.getEmployeeId());

		if (affClassHistory.isPresent()) {
			return affClassHistory.get().getPeriods().stream()
					.filter(x -> affClassHistItemRepo.getByHistoryId(x.identifier()).isPresent())
					.map(x -> ComboBoxObject.toComboBoxObject(x.identifier(), x.start().toString(), 
							x.end().equals(GeneralDate.max()) 
							//&& query.getCtgType() == 3
							?"":x.end().toString()))
					.collect(Collectors.toList());
		}
		return new ArrayList<>();

	}

}
