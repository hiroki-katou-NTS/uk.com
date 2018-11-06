package nts.uk.ctx.pr.core.pub.wageprovision.processdatecls;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author HungTT
 *
 */

public interface CurrProcessYmPub {

	public Optional<CurrProcessYmExport> getCurrentSalaryProcessYm(String companyId, int processCateNo);
	
	public List<CurrProcessYmExport> getCurrentSalaryProcessYm(String companyId, List<Integer> processCateNo);
	
}
