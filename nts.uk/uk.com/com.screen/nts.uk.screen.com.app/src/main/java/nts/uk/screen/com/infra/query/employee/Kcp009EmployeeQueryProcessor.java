/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.com.infra.query.employee;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class Kcp009EmployeeQueryProcessor.
 */
@Stateless
public class Kcp009EmployeeQueryProcessor {
	
	/** The employee search query repository. */
	@Inject
	private EmployeeSearchQueryRepository employeeSearchQueryRepository;
	
	/**
	 * Search by code.
	 *
	 * @param code the code
	 * @param system the system
	 * @return the optional
	 */
	public Optional<Kcp009EmployeeSearchData> searchByCode(String code, System system) {
		//TODO: get Employee reference range (社員参照範囲を取得する).
		EmployeeReferenceRange employeeReferenceRange = EmployeeReferenceRange.AllEmployee;
		Optional<Kcp009EmployeeSearchData> foundEmployee = Optional.ofNullable(null);
		GeneralDate systemDate = GeneralDate.today();
		String companyId = AppContexts.user().companyId();
		switch (employeeReferenceRange) {
		case AllEmployee:
			foundEmployee = this.employeeSearchQueryRepository.findInAllEmployee(code, system, systemDate, companyId);
		default:
			break;
		}
		
		if(!foundEmployee.isPresent()) {
			throw new BusinessException("Msg_7");
		}
		return foundEmployee;
	}
	
}
