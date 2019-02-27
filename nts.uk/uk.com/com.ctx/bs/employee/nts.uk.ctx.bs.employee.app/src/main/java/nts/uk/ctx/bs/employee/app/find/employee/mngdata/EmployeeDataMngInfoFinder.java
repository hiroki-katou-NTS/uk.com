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
import nts.uk.shr.pereg.app.find.PeregQueryByListEmp;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.GridPeregDomainDto;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Stateless
public class EmployeeDataMngInfoFinder implements PeregFinder<EmployeeDataMngInfoDto> {
	@Inject
	EmployeeDataMngInfoRepository edMngRepo;

	@Override
	public String targetCategoryCode() {
		return "CS00001";
	}

	@Override
	public Class<EmployeeDataMngInfoDto> dtoClass() {
		return EmployeeDataMngInfoDto.class;
	}

	@Override
	public DataClassification dataType() {
		return DataClassification.EMPLOYEE;
	}

	@Override
	public EmployeeDataMngInfoDto getSingleData(PeregQuery query) {
		List<EmployeeDataMngInfo> domain = edMngRepo.findByEmployeeId(query.getEmployeeId());
		if (domain.isEmpty()) {
			return null;
		}
		return EmployeeDataMngInfoDto.fromDomain(domain.get(0));
	}

	@Override
	public List<PeregDomainDto> getListData(PeregQuery query) {
		return new ArrayList<>();
	}

	@Override
	public List<ComboBoxObject> getListFirstItems(PeregQuery query) {
		return new ArrayList<>();
	}

	@Override
	public List<GridPeregDomainDto> getAllData(PeregQueryByListEmp query) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public List<PeregDomainDto> getAllData(List<PeregQuery> query) {
//		List<String> sids = query.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
//		String cid = AppContexts.user().companyId();
//		List<EmployeeDataMngInfo> domain = edMngRepo.findByListEmployeeId(cid, sids);
//		if (domain.isEmpty()) {
//			return new ArrayList<>();
//		}
//		// domain.stream().map(c -> EmployeeDataMngInfoDto.fromDomain(c)).collect(Collectors.toList());
//		return null;
//	}
}
