package nts.uk.ctx.at.request.dom.application.common.adapter.sys;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.adapter.sys.dto.MailDestinationImport;

public interface EnvAdapter {
	
	
	/**
	 * RequestList 419
	 * @param cID
	 * @param sIDs
	 * @param functionID
	 * @return
	 */
	List<MailDestinationImport> getEmpEmailAddress(String cID, List<String> sIDs, Integer functionID);
}
