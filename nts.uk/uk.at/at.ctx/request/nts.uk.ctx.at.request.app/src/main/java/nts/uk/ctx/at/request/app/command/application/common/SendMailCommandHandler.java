package nts.uk.ctx.at.request.app.command.application.common;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.MailSenderResult;
import nts.uk.ctx.at.request.dom.mail.CheckTransmission;

@Stateless
public class SendMailCommandHandler extends CommandHandlerWithResult<SendMailCommand, MailSenderResult>{
	
	@Inject
	private CheckTransmission checkTranmission;
	protected MailSenderResult handle(CommandHandlerContext<SendMailCommand> context)  {
		ApplicationCommand_New app = context.getCommand().getApplication();
		return checkTranmission.doCheckTranmission(app.getApplicationID(), app.getApplicationType(), app.getPrePostAtr(), 
				context.getCommand().getSendMailOption(), null, context.getCommand().getMailContent(), 
				null, app.getApplicationDate(), app.getApplicantSID(), context.getCommand().isSendMailApplicaint());
	}
}
