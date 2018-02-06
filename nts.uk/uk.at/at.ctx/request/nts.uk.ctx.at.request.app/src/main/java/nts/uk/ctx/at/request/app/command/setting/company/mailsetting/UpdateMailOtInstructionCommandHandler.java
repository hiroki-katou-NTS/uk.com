package nts.uk.ctx.at.request.app.command.setting.company.mailsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.overtimeworkinstructionmail.MailOtInstruction;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.overtimeworkinstructionmail.MailOtInstructionRepository;

/**
 * update mail ot instruction
 * @author yennth
 *
 */
@Stateless
public class UpdateMailOtInstructionCommandHandler extends CommandHandler<MailOtInstructionCommand>{
	@Inject
	private MailOtInstructionRepository mailRep;

	@Override
	protected void handle(CommandHandlerContext<MailOtInstructionCommand> context) {
		MailOtInstructionCommand data = context.getCommand();
		Optional<MailOtInstruction> mail = mailRep.getMail();
		MailOtInstruction mailHd = MailOtInstruction.createFromJavaType(data.getCompanyId(), data.getSubject(), data.getContent());
		if(mail.isPresent()){
			mailRep.update(mailHd);
			return;
		}
		mailRep.insert(mailHd);
	}
	
}
