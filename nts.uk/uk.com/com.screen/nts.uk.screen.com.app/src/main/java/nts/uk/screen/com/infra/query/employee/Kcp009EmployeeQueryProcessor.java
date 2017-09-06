package nts.uk.screen.com.infra.query.employee;

import java.util.Optional;

import javax.inject.Inject;

public class Kcp009EmployeeQueryProcessor {

	
	@Inject
	private EmployeeSearchQueryRepository employeeSearchQueryRepository;
	
	public Optional<Kcp009EmployeeSearchData> searchByCode(String code, System system) {
		EmployeeReferenceRange employeeReferenceRange = this.getEmployeeReferenceRange();
		
		switch (employeeReferenceRange) {
		case AllEmployee:
			return this.employeeSearchQueryRepository.findInAllEmployee(code, system);
		default:
			break;
		}
		
		return null;
	}
	
	private EmployeeReferenceRange getEmployeeReferenceRange() {
		//TODO: get Employee reference range (社員参照範囲を取得する).
		return EmployeeReferenceRange.AllEmployee;
	}
}
