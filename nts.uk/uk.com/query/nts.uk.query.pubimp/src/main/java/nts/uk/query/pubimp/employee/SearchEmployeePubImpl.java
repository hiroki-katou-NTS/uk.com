/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.pubimp.employee;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.query.app.employee.RegulationInfoEmployeeFinder;
import nts.uk.query.pub.employee.SearchEmployeePub;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class SearchEmployeePubImpl.
 */
@Stateless
public class SearchEmployeePubImpl implements SearchEmployeePub {

	/** The employee finder. */
	@Inject
	private RegulationInfoEmployeeFinder employeeFinder;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.query.pub.employee.SearchEmployeePub#searchByEmployeeCode(java.
	 * lang.String, java.lang.Integer)
	 */
	@Override
	public List<String> searchByEmployeeCode(String sCd, Integer systemType) {
		return this.employeeFinder.searchByEmployeeCode(sCd, systemType);
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
		return this.employeeFinder.searchByEmployeeName(sName, systemType);
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
		return this.employeeFinder.searchByEntryDate(period, systemType);
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
		return this.employeeFinder.searchByRetirementDate(period, systemType);
	}

}
