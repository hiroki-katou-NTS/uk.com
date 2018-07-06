package nts.uk.ctx.at.request.ac.env;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.common.adapter.sys.EnvAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.sys.dto.MailDestinationImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.sys.dto.OutGoingMailImport;
import nts.uk.ctx.sys.env.pub.maildestination.IMailDestinationPub;
import nts.uk.ctx.sys.env.pub.maildestination.OutGoingMail;

@Stateless
public class EnvAdapterImpl implements EnvAdapter {
	@Inject
	private IMailDestinationPub iMailDestinationPub;

	@Override
	public List<MailDestinationImport> getEmpEmailAddress(String cID, List<String> sIDs, Integer functionID) {
		List<MailDestinationImport> listEmpMail = iMailDestinationPub.getEmpEmailAddress(cID, sIDs, functionID).stream()
				.map(x -> new MailDestinationImport(x.getEmployeeID(), mapGoingMail(x.getOutGoingMails())))
				.collect(Collectors.toList());
		return listEmpMail;
	}

	private List<OutGoingMailImport> mapGoingMail(List<OutGoingMail> outGoingMails) {
		return outGoingMails.stream().map(x -> new OutGoingMailImport(x.getEmailAddress()))
				.collect(Collectors.toList());
	}
	@Override
	public OutGoingMailImport findMailBySid(List<MailDestinationImport> lsMail, String sID){
		for (MailDestinationImport mail : lsMail) {
			if(mail.getEmployeeID().equals(sID)){
				return mail.getOutGoingMails().isEmpty() ? null : mail.getOutGoingMails().get(0);
			}
		}
		return null;
	}
}
