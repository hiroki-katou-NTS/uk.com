package nts.uk.ctx.at.request.dom.application.common.adapter.sys;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.adapter.sys.dto.MailDestinationImport;

public interface EnvAdapter {
	List<MailDestinationImport> getEmpEmailAddress(String cID, List<String> sIDs, Integer functionID);
}
