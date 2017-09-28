/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.operationsetting;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * @author danpv
 *
 */
public interface OperationOfDailyPerformanceRepoInterface {
	
	public OperationOfDailyPerformance findOperationOfDailyPerformance(CompanyId companyId);
	
	public void registerOperationOfDailyPerformance(OperationOfDailyPerformance domain);

}
