package nts.uk.ctx.pr.transfer.dom.adapter.wageprovision.processdatecls;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author HungTT
 *
 */
public interface CurrProcessYmAdapter {

	public Optional<CurrProcessYmImport> getCurrentSalaryProcessYm(String companyId, int processCateNo);
	
	public List<CurrProcessYmImport> getCurrentSalaryProcessYm(String companyId, List<Integer> processCateNo);

}
