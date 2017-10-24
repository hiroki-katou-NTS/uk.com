/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.ac.organization;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.organization.EmploymentImported;
import nts.uk.ctx.at.record.dom.organization.adapter.EmploymentAdapter;
import nts.uk.ctx.bs.employee.pub.employment.SyEmploymentPub;

/**
 * The Class EmploymentAdapterImpl.
 */
@Stateless
public class EmploymentAdapterImpl implements EmploymentAdapter {

	/** The emp pub. */
	@Inject
	private SyEmploymentPub empPub;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.organization.EmploymentAdapter#getAllEmployment(
	 * java.lang.String)
	 */
	@Override
	public List<EmploymentImported> getAllEmployment(String comId) {
		return this.empPub.findAll(comId).stream()
				.map(item -> new EmploymentImported(comId, item.getCode(), item.getName()))
				.collect(Collectors.toList());
	}

}
