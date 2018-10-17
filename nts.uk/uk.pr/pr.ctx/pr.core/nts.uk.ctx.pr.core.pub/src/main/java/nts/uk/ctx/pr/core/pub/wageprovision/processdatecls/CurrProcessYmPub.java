package nts.uk.ctx.pr.core.pub.wageprovision.processdatecls;

import java.util.Optional;

/**
 * 
 * @author HungTT
 *
 */

public interface CurrProcessYmPub {

	public Optional<CurrProcessYmExport> getCurrentSalaryProcessYm(String companyId, int processCateNo);
	
}
