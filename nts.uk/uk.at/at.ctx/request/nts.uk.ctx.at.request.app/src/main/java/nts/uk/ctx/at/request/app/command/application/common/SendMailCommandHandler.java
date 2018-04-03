package nts.uk.ctx.at.request.app.command.application.common;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.mail.send.MailContents;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.mail.MailSender;
import nts.uk.shr.com.mail.SendMailFailedException;

@Stateless
public class SendMailCommandHandler extends CommandHandlerWithResult<SendMailCommand, List<Integer>>{
	
	@Inject
	private MailSender mailSender;
	protected List<Integer> handle(CommandHandlerContext<SendMailCommand> context)  {
		// TO DO
		String companyID =  AppContexts.user().companyId();
		List<Integer> sendMailStatus = new ArrayList<Integer>();
		String mailContent = context.getCommand().getMailContent();
		context.getCommand().getSendMailOption().forEach( x -> {
			try{
				mailSender.send("katohiro6180@gmail.com", "hiep.ld@3si.vn", new MailContents("subject", mailContent));
				sendMailStatus.add(0);
			} catch (SendMailFailedException e) {
				throw e;
			}
		});
		return sendMailStatus;
	}
}
