package nts.uk.ctx.bs.employee.app.find.workplace.affiliate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItem;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregEmpInfoQuery;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.PeregQueryByListEmp;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.GridPeregDomainDto;
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

	@Override
	public List<GridPeregDomainDto> getAllData(PeregQueryByListEmp query) {

		List<GridPeregDomainDto> result = new ArrayList<>();
		// key - sid , value - pid getEmployeeId getPersonId
		Map<String, String> mapSids = query.getEmpInfos().stream()
				.collect(Collectors.toMap(PeregEmpInfoQuery::getEmployeeId, PeregEmpInfoQuery::getPersonId));
		Map<String, List<AffWorkplaceHistory>> affWorkPlaceHist = affWrkplcHistRepo
				.getWorkplaceHistoryByEmpIdsAndDate(query.getStandardDate(), new ArrayList<>(mapSids.keySet())).stream()
				.collect(Collectors.groupingBy(c -> c.getHistoryIds().get(0)));
		List<AffWorkplaceHistoryItem> historyItems = affWrkplcHistItemRepo.findByHistIds(new ArrayList<>(affWorkPlaceHist.keySet()));
		
		historyItems.stream().forEach(item -> {
			List<AffWorkplaceHistory> affWorkplace = affWorkPlaceHist.get(item.getHistoryId());
			if(affWorkplace != null) {
				result.add(affWorkplace.size() > 0
						? new GridPeregDomainDto(item.getEmployeeId(), mapSids.get(item.getEmployeeId()),
								AffWorlplaceHistItemDto.getFirstFromDomain(affWorkplace.get(0), item))
						: new GridPeregDomainDto(item.getEmployeeId(), mapSids.get(item.getEmployeeId()), null));

			}else {
				result.add(new GridPeregDomainDto(item.getEmployeeId(), mapSids.get(item.getEmployeeId()),null));
			}
			
		});

		if (query.getEmpInfos().size() > result.size()) {
			for (int i = result.size(); i < query.getEmpInfos().size(); i++) {
				PeregEmpInfoQuery emp = query.getEmpInfos().get(i);
				result.add(new GridPeregDomainDto(emp.getEmployeeId(), emp.getPersonId(), null));
			}
		}
		return result;
	}
}
