package nts.uk.ctx.at.record.pub.workrecord.operationsetting;

import java.util.Optional;

public interface FormatPerformancePub {
	
	/**
	 * RequestList402
	 * @param companyId
	 * @return
	 */
	Optional<FormatPerformanceExport> findByCompanyId(String companyId);

}
