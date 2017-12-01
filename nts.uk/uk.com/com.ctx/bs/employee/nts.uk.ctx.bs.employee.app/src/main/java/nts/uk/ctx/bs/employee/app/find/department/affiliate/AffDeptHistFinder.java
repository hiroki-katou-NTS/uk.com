package nts.uk.ctx.bs.employee.app.find.department.affiliate;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.department.affiliate.AffDepartmentHistory;
import nts.uk.ctx.bs.employee.dom.department.affiliate.AffDepartmentHistoryItem;
import nts.uk.ctx.bs.employee.dom.department.affiliate.AffDepartmentHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.department.affiliate.AffDepartmentHistoryRepository;
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
		Optional<AffDepartmentHistory> affDeptHist = affDeptHistRepo.getAffDeptHistByEmpHistStandDate(employeeId, standDate);
		if(affDeptHist.isPresent()){
			Optional<AffDepartmentHistoryItem> affDeptHistItem = affDeptHistItemRepo.getByHistId(affDeptHist.get().getHistoryItems().get(0).identifier());
			return AffDeptHistDto.getFirstFromDomain(affDeptHist.get(), affDeptHistItem.get());
		}
		return new PeregDomainDto();
	}
	
	private PeregDomainDto getByHistId(String historyId){
		Optional<AffDepartmentHistory> affDeptHist = affDeptHistRepo.getByHistId(historyId);
		if(affDeptHist.isPresent()){
			Optional<AffDepartmentHistoryItem> affDeptHistItem = affDeptHistItemRepo.getByHistId(affDeptHist.get().getHistoryItems().get(0).identifier());
			return AffDeptHistDto.getFirstFromDomain(affDeptHist.get(), affDeptHistItem.get());
		}
		return new PeregDomainDto();
	} 
}
