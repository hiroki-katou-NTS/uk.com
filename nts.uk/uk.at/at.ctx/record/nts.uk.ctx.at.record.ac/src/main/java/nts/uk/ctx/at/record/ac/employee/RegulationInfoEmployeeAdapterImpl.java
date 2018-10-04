/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.ac.employee;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.adapter.employee.RegulationInfoEmployeeAdapter;
import nts.uk.ctx.at.record.dom.adapter.employee.SortingConditionOrderImport;
import nts.uk.query.pub.employee.RegularSortingType;
import nts.uk.query.pub.employee.RegulationInfoEmployeePub;
import nts.uk.query.pub.employee.SortingConditionOrderDto;

/**
 * The Class RegulationInfoEmployeeAdapterImpl.
 */
@Stateless
public class RegulationInfoEmployeeAdapterImpl implements RegulationInfoEmployeeAdapter {

	/** The regulation info employee adapter. */
	@Inject
	private RegulationInfoEmployeePub regulationInfoEmployeePub;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.adapter.employee.RegulationInfoEmployeeAdapter#sortEmployees(java.lang.String, java.util.List, java.util.List, nts.arc.time.GeneralDateTime)
	 */
	@Override
	public List<String> sortEmployees(String comId, List<String> sIds, List<SortingConditionOrderImport> orders,
			GeneralDateTime referenceDate) {
		List<SortingConditionOrderDto> convertedOrders = orders.stream().map(item->{
//			return new SortingConditionOrderDto();
			return new SortingConditionOrderDto(item.getOrder(),RegularSortingType.valueOf(item.getType().value));
		}).collect(Collectors.toList());
		return regulationInfoEmployeePub.sortEmployee(comId, sIds, convertedOrders, referenceDate);
	}

}
