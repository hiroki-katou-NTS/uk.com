package nts.uk.ctx.bs.employee.app.find.department.affiliate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.department.affiliate.AffDepartmentHistory;
import nts.uk.ctx.bs.employee.dom.department.affiliate.AffDepartmentHistoryItem;
import nts.uk.ctx.bs.employee.dom.department.affiliate.AffDepartmentHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.department.affiliate.AffDepartmentHistoryRepository;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Stateless
public class AffDeptHistFinder implements PeregFinder<AffDeptHistDto>{
	
	@Inject
	private AffDepartmentHistoryRepository affDeptHistRepo;
	
	@Inject
	private AffDepartmentHistoryItemRepository affDeptHistItemRepo;

	@Override
	public String targetCategoryCode() {
		return "CS00015";
	}

	@Override
	public Class<AffDeptHistDto> dtoClass() {
		return AffDeptHistDto.class;
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

	private PeregDomainDto getByEmpIdAndStandDate(String employeeId, GeneralDate standDate){
		if(standDate == null) return null;
		Optional<AffDepartmentHistory> affDeptHist = affDeptHistRepo.getAffDeptHistByEmpHistStandDate(employeeId, standDate);
		if(affDeptHist.isPresent()){
			Optional<AffDepartmentHistoryItem> affDeptHistItem = affDeptHistItemRepo.getByHistId(affDeptHist.get().getHistoryItems().get(0).identifier());
			return AffDeptHistDto.getFirstFromDomain(affDeptHist.get(), affDeptHistItem.get());
		}
		return null;
	}
	
	private PeregDomainDto getByHistId(String historyId){
		Optional<AffDepartmentHistory> affDeptHist = affDeptHistRepo.getByHistId(historyId);
		if(affDeptHist.isPresent()){
			Optional<AffDepartmentHistoryItem> affDeptHistItem = affDeptHistItemRepo.getByHistId(affDeptHist.get().getHistoryItems().get(0).identifier());
			return AffDeptHistDto.getFirstFromDomain(affDeptHist.get(), affDeptHistItem.get());
		}
		return null;
	}

	@Override
	public List<ComboBoxObject> getListFirstItems(PeregQuery query) {
		Optional<AffDepartmentHistory> affDeptHist = affDeptHistRepo.getByEmployeeId(query.getEmployeeId());
		if (!affDeptHist.isPresent())
			return new ArrayList<>();
		List<DateHistoryItem> historyItems = affDeptHist.get().getHistoryItems();
		if(historyItems.size() == 0)
			return new ArrayList<>();
		return historyItems.stream()
				.sorted((a, b) -> b.start().compareTo(a.end()))
				.map(x -> ComboBoxObject.toComboBoxObject(x.identifier(), x.start().toString(), x.end().toString()))
				.collect(Collectors.toList());
		
	} 
}
