/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.ac.organization;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.organization.EmploymentImported;
import nts.uk.ctx.at.record.dom.organization.adapter.EmploymentAdapter;

/**
 * The Class EmploymentAdapterImpl.
 */
@Stateless
public class EmploymentAdapterImpl implements EmploymentAdapter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.organization.EmploymentAdapter#getAllEmployment(
	 * java.lang.String)
	 */
	@Override
	public List<EmploymentImported> getAllEmployment(String comId) {
		List<EmploymentImported> list = new ArrayList<EmploymentImported>();
		//TODO: mock data.
		for (int i = 0; i < 10; i++) {
			list.add(new EmploymentImported(comId, ""+i, "name"+i));
		}
		return list;
	}

}
