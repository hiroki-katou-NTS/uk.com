package nts.uk.ctx.at.request.dom.application.common.adapter.sys;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.adapter.sys.dto.MailDestinationImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.sys.dto.MailServerSetImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.sys.dto.OutGoingMailImport;

public interface EnvAdapter {
	//get list mail by list sID
	List<MailDestinationImport> getEmpEmailAddress(String cID, List<String> sIDs, Integer functionID);
	//find mail by sID
	OutGoingMailImport findMailBySid(List<MailDestinationImport> lsMail, String sID);
	
	public MailServerSetImport checkMailServerSet(String companyID);
}
