package nts.uk.ctx.at.shared.app.command.workrule.closure;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ClosureUpdateMonthHandler extends CommandHandler<ClosureUpdateMonthCommand> {
	
	/** The closure repository. */
	@Inject
	private ClosureRepository closureRepository;

	@Override
	protected void handle(CommandHandlerContext<ClosureUpdateMonthCommand> context) {
		
		String companyId = AppContexts.user().companyId();
		ClosureUpdateMonthCommand command = context.getCommand();
		
		Optional<Closure> optClosure = closureRepository.findById(companyId, command.getClosureId());
		if(!optClosure.isPresent()) return;
		
		Closure closure = optClosure.get();
		closure.udpateProcessingMonth();
		closureRepository.update(closure);
	}

}
