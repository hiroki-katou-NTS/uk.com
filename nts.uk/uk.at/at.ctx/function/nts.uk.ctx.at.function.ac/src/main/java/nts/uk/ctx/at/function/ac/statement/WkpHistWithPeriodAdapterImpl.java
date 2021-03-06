/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.ac.statement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.statement.WkpHistWithPeriodAdapter;
import nts.uk.ctx.at.function.dom.statement.dtoimport.WkpHistWithPeriodImport;
import nts.uk.ctx.at.function.dom.statement.dtoimport.WkpInfoHistImport;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;
import nts.arc.time.calendar.period.DatePeriod;


/**
 * The Class WkpHistWithPeriodAdapterImpl.
 */
@Stateless
public class WkpHistWithPeriodAdapterImpl implements WkpHistWithPeriodAdapter{

	/** The sy workplace pub. */
	@Inject
	private WorkplacePub workplacePub;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.statement.WkpHistWithPeriodAdapter#getLstHistByWkpsAndPeriod(java.util.List, nts.arc.time.calendar.period.DatePeriod)
	 */
	@Override
	public List<WkpHistWithPeriodImport> getLstHistByWkpsAndPeriod(List<String> wkpIds, DatePeriod period) {
		List<WkpInfoHistImport> wkpInfoHistImport = new ArrayList<WkpInfoHistImport>();
		return workplacePub.findByWkpIds(wkpIds).stream().map(x -> {
			wkpInfoHistImport.add(new WkpInfoHistImport(null, x.getWorkplaceCode(), x.getWorkplaceName()));
			return new WkpHistWithPeriodImport(x.getWorkplaceId(),wkpInfoHistImport);}).collect(Collectors.toList());
	}
	
}
