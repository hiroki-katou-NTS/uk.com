package nts.uk.ctx.sys.gateway.app.command.sendmail;

import java.util.ArrayList;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.mail.send.MailContents;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserImportNew;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.mail.MailSender;
import nts.uk.shr.com.url.RegisterEmbededURL;

/**
 * The Class SendMailInfoCommandHandler.
 */
@Stateless
@Transactional
public class SendMailInfoCommandHandler extends CommandHandler<SendMailInfoCommand> {

	/** The user adapter. */
	@Inject
	private UserAdapter userAdapter;
	
	@Inject
	private MailSender mailSender;
	
	/** The register embeded URL. */
	@Inject
	private RegisterEmbededURL registerEmbededURL;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SendMailInfoCommand> context) {
		// get command
		SendMailInfoCommand command = context.getCommand();

		// Get RequestList 222
		Optional<UserImportNew> user = this.userAdapter.findUserByContractAndLoginIdNew(command.getContractCode(),
				command.getLoginId());
		
		if (user.isPresent()){
			if (user.get().getMailAddress().isEmpty()){
				throw new BusinessException("Msg_1129");
			} else {
				
				//Send Mail アルゴリズム「メール送信実行」を実行する
				this.sendMail(user.get().getMailAddress(), command);
			}
		}

	}
	
	/**
	 * Send mail.
	 *
	 * @param command the command
	 * @return true, if successful
	 */
	//Send Mail アルゴリズム「メール送信実行」を実行する
	private boolean sendMail(String mailto, SendMailInfoCommand command){
		
		//get param input
		String programId = AppContexts.programId();
		String employeeId = AppContexts.user().employeeId();
		String employeeCD = AppContexts.user().employeeCode();
		
		//get URL from CCG033
		String url = this.registerEmbededURL.embeddedUrlInfoRegis(programId, "H", 1, 24, 
				employeeId, command.getContractCode(), command.getLoginId(), employeeCD, new ArrayList<>());
		//sendMail
		MailContents contents = new MailContents("", "#CCG007_21 \r\n" + url);
		
		mailSender.sendFromAdmin(mailto, contents);
		
		return false;
	}
	
}
