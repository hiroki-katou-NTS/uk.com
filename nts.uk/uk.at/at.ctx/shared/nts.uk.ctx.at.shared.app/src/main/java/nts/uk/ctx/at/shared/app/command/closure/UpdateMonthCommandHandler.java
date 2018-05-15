package nts.uk.ctx.at.shared.app.command.closure;

import java.util.Optional;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.shr.com.context.AppContexts;

public class UpdateMonthCommandHandler extends CommandHandler<UpdateMonthCommand>{
	
	@Inject
	private ClosureRepository closureRepository;
	
	@Override
	protected void handle(CommandHandlerContext<UpdateMonthCommand> context) {
		// Get Company Id
		String companyId = AppContexts.user().companyId();
		
		// Get command
		UpdateMonthCommand command = context.getCommand();
		
		// Get domain closure
		Optional<Closure> closure = this.closureRepository.findById(companyId, command.getClosureId().value);
		
		if (closure.isPresent()){
			closure.get().getClosureHistories().forEach(item -> {
				if (closure.get().getClosureMonth().getProcessingYm().equals(item.getStartYearMonth())){
//					if ()
				}
			});
			
		}
		
	}

}
