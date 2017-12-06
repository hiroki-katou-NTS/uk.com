package nts.uk.ctx.bs.employee.app.find.employee.mngdata;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Stateless
public class EmployeeDataMngInfoFinder implements PeregFinder<EmployeeDataMngInfoDto> {
	@Inject
	EmployeeDataMngInfoRepository edMngFinder;

	@Override
	public String targetCategoryCode() {
		return "CS00001";
	}

	@Override
	public Class<EmployeeDataMngInfoDto> dtoClass() {
		// TODO Auto-generated method stub
		return EmployeeDataMngInfoDto.class;
	}

	@Override
	public DataClassification dataType() {
		return DataClassification.EMPLOYEE;
	}

	@Override
	public EmployeeDataMngInfoDto getSingleData(PeregQuery query) {
		EmployeeDataMngInfo domain = edMngFinder.findById(query.getPersonId(), query.getEmployeeId());

		return EmployeeDataMngInfoDto.fromDomain(domain);
	}

	@Override
	public List<PeregDomainDto> getListData(PeregQuery query) {
		return new ArrayList<>();
	}

	@Override
	public List<ComboBoxObject> getListFirstItems(PeregQuery query) {
		return new ArrayList<>();
	}
}
