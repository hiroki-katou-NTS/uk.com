package nts.uk.ctx.pr.core.pub.wageprovision.processdatecls;

import java.util.List;

/**
 * 
 * @author HungTT
 *
 */

public interface ProcessInformationPub {

	public List<ProcessInformationExport> getProcessInformationByDeprecatedCategory(String companyId, int deprecateAtr);

}
