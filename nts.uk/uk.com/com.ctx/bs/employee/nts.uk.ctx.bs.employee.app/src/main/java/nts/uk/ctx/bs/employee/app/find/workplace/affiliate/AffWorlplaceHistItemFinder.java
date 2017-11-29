package nts.uk.ctx.bs.employee.app.find.workplace.affiliate;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItem;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItemRepository_v1;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryRepository_v1;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory_ver1;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Stateless
public class AffWorlplaceHistItemFinder implements PeregFinder<AffWorlplaceHistItemDto>{

	@Inject
	private AffWorkplaceHistoryItemRepository_v1 affWrkplcHistItemRepo;
	
	@Inject
	private AffWorkplaceHistoryRepository_v1 affWrkplcHistRepo;
	
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
		Optional<AffWorkplaceHistory_ver1> affWrkplcHist = affWrkplcHistRepo.getByEmpIdAndStandDate(query.getEmployeeId(), query.getStandardDate());
		if(affWrkplcHist.isPresent()){
			Optional<AffWorkplaceHistoryItem> affWrkplcHistItem = affWrkplcHistItemRepo.getByHistId(affWrkplcHist.get().getHistoryItems().get(0).identifier());
			return AffWorlplaceHistItemDto.getFirstFromDomain(affWrkplcHist.get(), affWrkplcHistItem.get());
		}
		return new PeregDomainDto();
	}

	@Override
	public List<PeregDomainDto> getListData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

}
