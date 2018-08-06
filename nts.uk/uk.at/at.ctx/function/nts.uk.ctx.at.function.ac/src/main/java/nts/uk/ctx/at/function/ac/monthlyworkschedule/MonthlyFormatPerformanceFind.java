package nts.uk.ctx.at.function.ac.monthlyworkschedule;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.monthlyworkschedule.MonthlyFormatPerformanceAdapter;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.MonthlyFormatPerformanceImport;
import nts.uk.ctx.at.record.pub.workrecord.operationsetting.FormatPerformanceExport;
import nts.uk.ctx.at.record.pub.workrecord.operationsetting.FormatPerformancePub;

/**
 * The Class MonthlyFormatPerformanceFind.
 */
@Stateless
public class MonthlyFormatPerformanceFind implements MonthlyFormatPerformanceAdapter {

	/** The format performance pub. */
	@Inject
	private FormatPerformancePub formatPerformancePub;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.monthlyworkschedule.MonthlyFormatPerformanceAdapter#getFormatPerformance(java.lang.String)
	 */
	@Override
	public Optional<MonthlyFormatPerformanceImport> getFormatPerformance(String companyId) {
		Optional<FormatPerformanceExport> optFormatPerformanceExport = formatPerformancePub.findByCompanyId(companyId);
		if (optFormatPerformanceExport.isPresent()) {
			return Optional.of(new MonthlyFormatPerformanceImport(companyId,
					optFormatPerformanceExport.get().getSettingUnitType()));
		}
		return Optional.empty();
	}

}
