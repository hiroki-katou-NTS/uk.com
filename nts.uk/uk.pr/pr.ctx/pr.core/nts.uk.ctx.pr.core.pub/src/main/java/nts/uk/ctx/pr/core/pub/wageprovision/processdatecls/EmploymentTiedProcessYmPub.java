package nts.uk.ctx.pr.core.pub.wageprovision.processdatecls;

import java.util.List;

/**
 * 
 * @author HungTT
 *
 */

public interface EmploymentTiedProcessYmPub {
	
	public List<EmploymentTiedProcessYearMonth> getByListProcCateNo(String companyId, List<Integer> processCateNo);

}
