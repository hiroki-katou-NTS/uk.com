package nts.uk.ctx.at.schedule.app.command.schedule.setting.functioncontrol;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.setting.functioncontrol.FunctionControlRepository;
import nts.uk.ctx.at.schedule.dom.schedule.setting.functioncontrol.ScheFuncControl;
import nts.uk.shr.com.context.AppContexts;

/**
 * TanLV
 *
 */
@Transactional
@Stateless
public class ScheFuncControlCommandHandler extends CommandHandler<ScheFuncControlCommand> {
	@Inject
	private FunctionControlRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<ScheFuncControlCommand> context) {
		ScheFuncControlCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		ScheFuncControl scheFuncControl = command.toDomain();
		
		Optional<ScheFuncControl> data = this.repository.getScheFuncControl(companyId);
		
		if (data.isPresent()) {
			// update process
			repository.updateScheFuncControl(scheFuncControl);
			
		} else {
			// insert process
			repository.addScheFuncControl(scheFuncControl);
		}
	}
}
