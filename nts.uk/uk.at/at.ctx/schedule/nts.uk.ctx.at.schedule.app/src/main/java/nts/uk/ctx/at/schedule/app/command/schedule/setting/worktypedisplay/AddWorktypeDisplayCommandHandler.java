package nts.uk.ctx.at.schedule.app.command.schedule.setting.worktypedisplay;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.setting.worktype.control.WorktypeDis;
import nts.uk.ctx.at.schedule.dom.schedule.setting.worktype.control.WorktypeDisRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author phongtq
 *
 */
@Transactional
@Stateless
public class AddWorktypeDisplayCommandHandler extends CommandHandler<AddWorktypeDisplayCommand> {

	@Inject
	private WorktypeDisRepository repository;
	@Override
	protected void  handle(CommandHandlerContext<AddWorktypeDisplayCommand> context) {
		AddWorktypeDisplayCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		WorktypeDis dis = command.toDomain(companyId);
		Optional<WorktypeDis> optional = this.repository.findByCId(companyId);

		if (optional.isPresent()) {
			// update Worktype Display
			this.repository.update(dis);
		} else {
			// add Worktype Display
			this.repository.add(dis);
		}
	}

}
