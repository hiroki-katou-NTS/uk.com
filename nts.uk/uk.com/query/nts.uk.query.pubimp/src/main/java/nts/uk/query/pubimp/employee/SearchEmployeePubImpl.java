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
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.query.model.employee.EmployeeAuthAdapter;
import nts.uk.query.model.employee.history.EmployeeHistoryRepository;
import nts.uk.query.pub.employee.SearchEmployeePub;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

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

	/** The person repo. */
	@Inject
	private PersonRepository personRepo;

	/** The emp his repo. */
	@Inject
	private EmployeeHistoryRepository empHisRepo;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.query.pub.employee.SearchEmployeePub#searchByEmployeeName(java.
	 * lang.String, java.lang.Integer)
	 */
	@Override
	public List<String> searchByEmployeeName(String sName, Integer systemType) {
		List<String> pIds = this.personRepo.findByName(sName).stream().map(Person::getPersonId)
				.collect(Collectors.toList());

		List<String> sIds = this.empDataMngInfoRepo.findByListPersonId(AppContexts.user().companyId(), pIds).stream()
				.map(EmployeeDataMngInfo::getEmployeeId).collect(Collectors.toList());

		return this.empAuthAdapter.narrowEmpListByReferenceRange(sIds, systemType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.query.pub.employee.SearchEmployeePub#searchByEntryDate(nts.uk.shr.
	 * com.time.calendar.period.DatePeriod, java.lang.Integer)
	 */
	@Override
	public List<String> searchByEntryDate(DatePeriod period, Integer systemType) {
		List<String> sIds = this.empHisRepo.findEmployeeByEntryDate(period);

		return this.empAuthAdapter.narrowEmpListByReferenceRange(sIds, systemType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.query.pub.employee.SearchEmployeePub#searchByRetirementDate(nts.uk
	 * .shr.com.time.calendar.period.DatePeriod, java.lang.Integer)
	 */
	@Override
	public List<String> searchByRetirementDate(DatePeriod period, Integer systemType) {
		List<String> sIds = this.empHisRepo.findEmployeeByRetirementDate(period);

		return this.empAuthAdapter.narrowEmpListByReferenceRange(sIds, systemType);
	}

}
