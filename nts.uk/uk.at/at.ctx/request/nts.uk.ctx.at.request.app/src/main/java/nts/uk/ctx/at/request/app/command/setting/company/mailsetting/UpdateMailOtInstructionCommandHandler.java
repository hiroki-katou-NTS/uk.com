package nts.uk.ctx.at.request.app.command.setting.company.mailsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.overtimeworkinstructionmail.MailOtInstruction;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.overtimeworkinstructionmail.MailOtInstructionRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * update mail ot instruction
 * @author yennth
 *
 */
@Stateless
@Transactional
public class UpdateMailOtInstructionCommandHandler extends CommandHandler<MailOtInstructionCommand>{
	@Inject
	private MailOtInstructionRepository mailRep;

	@Override
	protected void handle(CommandHandlerContext<MailOtInstructionCommand> context) {
		String companyId = AppContexts.user().companyId();
		MailOtInstructionCommand data = context.getCommand();
		Optional<MailOtInstruction> mail = mailRep.getMail();
		MailOtInstruction mailHd = MailOtInstruction.createFromJavaType(companyId, 
													data.getSubject(), data.getContent());
		if(mail.isPresent()){
			mailRep.update(mailHd);
			return;
		}
		mailRep.insert(mailHd);
	}
	
}
