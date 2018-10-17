package nts.uk.ctx.pr.transfer.dom.adapter.wageprovision.processdatecls;

import java.util.Optional;

/**
 * 
 * @author HungTT
 *
 */
public interface CurrProcessYmAdapter {

	public Optional<CurrProcessYmImport> getCurrentSalaryProcessYm(String companyId, int processCateNo);

}
