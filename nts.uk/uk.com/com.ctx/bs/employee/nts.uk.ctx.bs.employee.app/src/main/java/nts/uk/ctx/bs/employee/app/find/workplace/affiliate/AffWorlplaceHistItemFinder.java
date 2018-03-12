package nts.uk.ctx.bs.employee.app.find.workplace.affiliate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItem;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Stateless
public class AffWorlplaceHistItemFinder implements PeregFinder<AffWorlplaceHistItemDto> {

	@Inject
	private AffWorkplaceHistoryItemRepository affWrkplcHistItemRepo;

	@Inject
	private AffWorkplaceHistoryRepository affWrkplcHistRepo;

	@Override
	public String targetCategoryCode() {
		return "CS00017";
	}

	@Override
	public Class<AffWorlplaceHistItemDto> dtoClass() {
		return AffWorlplaceHistItemDto.class;
	}

	@Override
	public DataClassification dataType() {
		return DataClassification.EMPLOYEE;
	}

	@Override
	public PeregDomainDto getSingleData(PeregQuery query) {
		return query.getInfoId() == null ? getByEmpIdAndStandDate(query.getEmployeeId(), query.getStandardDate())
				: getByHistId(query.getInfoId());
	}

	@Override
	public List<PeregDomainDto> getListData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	public AffWorlplaceHistItemDto getByEmpIdAndStandDate(String employeeId, GeneralDate standDate) {
		Optional<AffWorkplaceHistory> affWrkplcHist = affWrkplcHistRepo.getByEmpIdAndStandDate(employeeId,
				standDate);
		if (affWrkplcHist.isPresent()) {

			Optional<AffWorkplaceHistoryItem> affWrkplcHistItem = affWrkplcHistItemRepo
					.getByHistId(affWrkplcHist.get().getHistoryItems().get(0).identifier());
			if (affWrkplcHistItem.isPresent()) {
				return AffWorlplaceHistItemDto.getFirstFromDomain(affWrkplcHist.get(), affWrkplcHistItem.get());
			}
		}
		return null;
	}

	private PeregDomainDto getByHistId(String historyId) {
		Optional<AffWorkplaceHistory> affWrkplcHist = affWrkplcHistRepo.getByHistId(historyId);
		if (affWrkplcHist.isPresent()) {
			Optional<AffWorkplaceHistoryItem> affWrkplcHistItem = affWrkplcHistItemRepo
					.getByHistId(affWrkplcHist.get().getHistoryItems().get(0).identifier());
			if (affWrkplcHistItem.isPresent()) {
				return AffWorlplaceHistItemDto.getFirstFromDomain(affWrkplcHist.get(), affWrkplcHistItem.get());
			}

		}
		return null;
	}

	@Override
	public List<ComboBoxObject> getListFirstItems(PeregQuery query) {
		
		Optional<AffWorkplaceHistory> affWrkplcHist = affWrkplcHistRepo
				.getByEmployeeIdDesc(AppContexts.user().companyId(), query.getEmployeeId());
		
		if (affWrkplcHist.isPresent()) {
			return affWrkplcHist.get().getHistoryItems().stream()
					.filter(x -> affWrkplcHistItemRepo.getByHistId(x.identifier()).isPresent())
					.map(x -> ComboBoxObject.toComboBoxObject(x.identifier(), x.start().toString(), 
							x.end().equals(GeneralDate.max()) 
							//&& query.getCtgType() == 3 
							? "" : x.end().toString()))
					.collect(Collectors.toList());
		}
		return new ArrayList<>();
	}
}
