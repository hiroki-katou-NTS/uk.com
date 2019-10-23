package nts.uk.ctx.bs.employee.app.find.employee.contact;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.employee.contact.EmployeeInfoContact;
import nts.uk.ctx.bs.employee.dom.employee.contact.EmployeeInfoContactRepository;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Stateless
public class PeregEmpInfoContactFinder implements PeregFinder<EmpInfoContactDto>{
	
	@Inject
	private EmployeeInfoContactRepository empInfoContactRepo;

	@Override
	public String targetCategoryCode() {
		return "CS00023";
	}

	@Override
	public Class<EmpInfoContactDto> dtoClass() {
		return EmpInfoContactDto.class;
	}

	@Override
	public DataClassification dataType() {
		// TODO Auto-generated method stub
		return DataClassification.EMPLOYEE;
	}

	@Override
	public PeregDomainDto getSingleData(PeregQuery query) {
		Optional<EmployeeInfoContact> empInfoContact = empInfoContactRepo.findByEmpId(query.getEmployeeId());
		if(empInfoContact.isPresent())
			return EmpInfoContactDto.fromDomain(empInfoContact.get());
		return null;
	}

	@Override
	public List<PeregDomainDto> getListData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ComboBoxObject> getListFirstItems(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}


}
