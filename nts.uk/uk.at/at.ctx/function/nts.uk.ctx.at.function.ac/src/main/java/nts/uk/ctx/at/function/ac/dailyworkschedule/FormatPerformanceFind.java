/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.ac.dailyworkschedule;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.dailyworkschedule.FormatPerformanceAdapter;
import nts.uk.ctx.at.function.dom.dailyworkschedule.FormatPerformanceImport;
import nts.uk.ctx.at.record.pub.workrecord.operationsetting.FormatPerformanceExport;
import nts.uk.ctx.at.record.pub.workrecord.operationsetting.FormatPerformancePub;

/**
 * The Class FormatPerformanceFinder.
 */
// @author HoangDD
@Stateless
public class FormatPerformanceFind implements FormatPerformanceAdapter{

	@Inject
	private FormatPerformancePub formatPerformancePub; 
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.FormatPerformanceAdapter#getFormatPerformance(java.lang.String)
	 */
	@Override
	public Optional<FormatPerformanceImport> getFormatPerformance(String companyId) {
		Optional<FormatPerformanceExport> optFormatPerformanceExport = formatPerformancePub.findByCompanyId(companyId);
		if (optFormatPerformanceExport.isPresent()) {
			return Optional.of(new FormatPerformanceImport(companyId, optFormatPerformanceExport.get().getSettingUnitType()));
		}
		return Optional.empty();
	}

}
