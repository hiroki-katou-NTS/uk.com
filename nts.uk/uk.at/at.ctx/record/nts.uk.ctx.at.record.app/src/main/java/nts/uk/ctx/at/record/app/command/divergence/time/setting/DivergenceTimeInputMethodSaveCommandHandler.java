package nts.uk.ctx.at.record.app.command.divergence.time.setting;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.app.command.divergence.time.history.ComDivergenceRefTimeHistSaveCommand;
import nts.uk.ctx.at.record.app.command.divergence.time.history.ComDivergenceRefTimeSaveCommand;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeRepository;

public class DivergenceTimeInputMethodSaveCommandHandler extends CommandHandler<DivergenceTimeInputMethodSaveCommand> {
	
	@Inject DivergenceTimeRepository divergenceTimeRepo;
	
	@Override
	protected void handle(CommandHandlerContext<DivergenceTimeInputMethodSaveCommand> context) {
		//get command
		DivergenceTimeInputMethodSaveCommand command = context.getCommand();
		
		//convert to domain
		
		
		//update
		//this.divergenceTimeRepo.update(command);
	}

}
