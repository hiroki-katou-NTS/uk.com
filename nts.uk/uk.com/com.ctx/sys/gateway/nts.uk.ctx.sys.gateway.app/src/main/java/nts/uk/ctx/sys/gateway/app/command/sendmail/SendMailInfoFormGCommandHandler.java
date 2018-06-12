package nts.uk.ctx.sys.gateway.app.command.sendmail;

import java.util.ArrayList;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.i18n.I18NText;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.mail.send.MailContents;
import nts.uk.ctx.sys.gateway.app.command.sendmail.dto.SendMailReturnDto;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserAdapter;
import nts.uk.ctx.sys.gateway.dom.adapter.user.UserImportNew;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.mail.MailSender;
import nts.uk.shr.com.mail.SendMailFailedException;
import nts.uk.shr.com.url.RegisterEmbededURL;

/**
 * The Class SendMailInfoCommandHandler.
 */
@Stateless
@Transactional
public class SendMailInfoFormGCommandHandler extends CommandHandlerWithResult<SendMailInfoFormGCommand, SendMailReturnDto> {

	/** The user adapter. */
	@Inject
	private UserAdapter userAdapter;

	/** The mail sender. */
	@Inject
	private MailSender mailSender;

	/** The register embeded URL. */
	@Inject
	private RegisterEmbededURL registerEmbededURL;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected SendMailReturnDto handle(CommandHandlerContext<SendMailInfoFormGCommand> context) {
		// get command
		SendMailInfoFormGCommand command = context.getCommand();

//		// Get RequestList 222
//		Optional<UserImportNew> user = this.userAdapter.findUserByContractAndLoginIdNew(command.getContractCode(),
//				command.getLoginId());
//
//		if (user.isPresent()) {
//			if (user.get().getMailAddress().isEmpty()) {
//				throw new BusinessException("Msg_1129");
//			} else {
//				// Send Mail アルゴリズム「メール送信実行」を実行する
//				return this.sendMail(user.get().getMailAddress(), command);
//			}
//		}
		
		return new SendMailReturnDto(null);
	}

	/**
	 * Send mail.
	 *
	 * @param mailto
	 *            the mailto
	 * @param command
	 *            the command
	 * @return true, if successful
	 */
	// Send Mail アルゴリズム「メール送信実行」を実行する
	private SendMailReturnDto sendMail(String mailto, SendMailInfoCommand command) {

		// get param input
		String programId = AppContexts.programId();
		String employeeId = AppContexts.user().employeeId();
		String employeeCD = AppContexts.user().employeeCode();

		// get URL from CCG033
		String url = this.registerEmbededURL.embeddedUrlInfoRegis(programId, "H", 1, 24, employeeId,
				command.getContractCode(), command.getLoginId(), employeeCD, new ArrayList<>());
		// sendMail
		MailContents contents = new MailContents("", I18NText.getText("CCG007_21") +" \n" + url);

		try {
			mailSender.sendFromAdmin(mailto, contents);
			SendMailReturnDto dto = new SendMailReturnDto(url);
			return dto;
		} catch (SendMailFailedException e) {
			// Send mail fail
			throw new BusinessException("Msg_208");
		}

	}

}
