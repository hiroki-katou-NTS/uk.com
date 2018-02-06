package nts.uk.ctx.at.request.app.command.setting.company.mailsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailholidayinstruction.MailHdInstruction;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailholidayinstruction.MailHdInstructionRepository;
/**
 * update mail holiday instruction
 * @author yennth
 *
 */
@Stateless
public class UpdateMailHdInstructionCommandHandler extends CommandHandler<MailHdInstructionCommand>{
	@Inject
	private MailHdInstructionRepository mailRep;

	@Override
	protected void handle(CommandHandlerContext<MailHdInstructionCommand> context) {
		MailHdInstructionCommand data = context.getCommand();
		Optional<MailHdInstruction> mail = mailRep.getMail();
		MailHdInstruction mailHd = MailHdInstruction.createFromJavaType(data.getCompanyId(), data.getSubject(), data.getContent());
		if(mail.isPresent()){
			mailRep.update(mailHd);
			return;
		}
		mailRep.insert(mailHd);
	}
	
}
