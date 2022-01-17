package nts.uk.ctx.at.function.ac.mailserver;

import nts.uk.ctx.at.function.dom.adapter.mailserver.*;
import nts.uk.ctx.sys.env.pub.mailserver.MailServerPub;
import nts.uk.ctx.sys.env.pub.mailserver.MailServerSetExport;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class MailServerAcFinder implements MailServerAdapter {

	@Inject
	private MailServerPub mailServerPub;
	
	@Override
	public boolean findBy(String companyId) {
		return mailServerPub.findBy(companyId);
	}

	@Override
	public MailServerSetImportDto checkMailServerSet(String companyID) {
		MailServerSetExport mailServer = mailServerPub.checkMailServerSet(companyID);
		return new MailServerSetImportDto(
				mailServer.isMailServerSet(),
				Optional.ofNullable(!mailServer.getOpMailServerExport().isPresent() ? null : new MailServerImportDto(
						mailServer.getOpMailServerExport().get().getCompanyId(),
						mailServer.getOpMailServerExport().get().getAuthenticationMethod(),
						mailServer.getOpMailServerExport().get().getEncryptionMethod(),
						mailServer.getOpMailServerExport().get().getAuthenticationMethod(),
						mailServer.getOpMailServerExport().get().getEmailAuthentication(),
						mailServer.getOpMailServerExport().get().getPassword(),
						new SmtpInfoImportDto(
								mailServer.getOpMailServerExport().get().getSmtpInfo().getServer(),
								mailServer.getOpMailServerExport().get().getSmtpInfo().getPort()),
						new PopInfoImportDto(
								mailServer.getOpMailServerExport().get().getPopInfo().getServer(),
								mailServer.getOpMailServerExport().get().getPopInfo().getUseServer(),
								mailServer.getOpMailServerExport().get().getPopInfo().getPort()),
						new ImapInfoImportDto(
								mailServer.getOpMailServerExport().get().getImapInfo().getServer(),
								mailServer.getOpMailServerExport().get().getImapInfo().getUseServer(),
								mailServer.getOpMailServerExport().get().getImapInfo().getPort())))
		);
	}

}
