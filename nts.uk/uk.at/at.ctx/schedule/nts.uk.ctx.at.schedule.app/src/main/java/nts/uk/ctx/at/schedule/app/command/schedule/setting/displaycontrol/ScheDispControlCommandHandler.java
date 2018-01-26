package nts.uk.ctx.at.schedule.app.command.schedule.setting.displaycontrol;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol.DisplayControlRepository;
import nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol.ScheDispControl;
import nts.uk.shr.com.context.AppContexts;

/**
 * TanLV
 *
 */
@Transactional
@Stateless
public class ScheDispControlCommandHandler extends CommandHandler<ScheDispControlCommand> {
	@Inject
	private DisplayControlRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<ScheDispControlCommand> context) {
		ScheDispControlCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		ScheDispControl scheDispControl = command.toDomain();
		
		Optional<ScheDispControl> data = this.repository.getScheDispControl(companyId);
		
		if (data.isPresent()) {
			// update process
			repository.updateScheDispControl(scheDispControl);
			
		} else {
			// insert process
			repository.addScheDispControl(scheDispControl);
		}
	}
}
