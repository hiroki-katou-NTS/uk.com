/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.pubimp.employee;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.query.model.employee.EmployeeAuthAdapter;
import nts.uk.query.pub.employee.SearchEmployeePub;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SearchEmployeePubImpl.
 */
@Stateless
public class SearchEmployeePubImpl implements SearchEmployeePub {

	/** The emp auth adapter. */
	@Inject
	private EmployeeAuthAdapter empAuthAdapter;

	/** The emp data mng info repo. */
	@Inject
	private EmployeeDataMngInfoRepository empDataMngInfoRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.query.pub.employee.SearchEmployeePub#searchByEmployeeCode(java.
	 * lang.String, java.lang.Integer)
	 */
	@Override
	public List<String> searchByEmployeeCode(String sCd, Integer systemType) {
		List<String> sIds = this.empDataMngInfoRepo.getEmployeeNotDeleteInCompany(AppContexts.user().companyId(), sCd)
				.stream().map(EmployeeDataMngInfo::getEmployeeId).collect(Collectors.toList());

		return this.empAuthAdapter.narrowEmpListByReferenceRange(sIds, systemType);
	}

}
