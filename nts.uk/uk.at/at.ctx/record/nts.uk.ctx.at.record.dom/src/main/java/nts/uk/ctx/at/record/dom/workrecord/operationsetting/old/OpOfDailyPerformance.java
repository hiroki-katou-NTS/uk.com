/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.operationsetting.old;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * @author danpv
 *
 */
public interface OpOfDailyPerformance {
	
	public OperationOfDailyPerformance find(CompanyId companyId);
	
	public void register(OperationOfDailyPerformance domain);

}
