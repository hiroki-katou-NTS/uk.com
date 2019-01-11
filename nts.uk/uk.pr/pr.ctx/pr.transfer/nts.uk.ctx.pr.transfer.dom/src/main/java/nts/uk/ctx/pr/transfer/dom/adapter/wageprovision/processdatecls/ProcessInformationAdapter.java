package nts.uk.ctx.pr.transfer.dom.adapter.wageprovision.processdatecls;

import java.util.List;

/**
 * 
 * @author HungTT
 *
 */
public interface ProcessInformationAdapter {

	public List<ProcessInformationImport> getProcessInformationByDeprecatedCategory(String companyId, int deprecateAtr);
	
}
