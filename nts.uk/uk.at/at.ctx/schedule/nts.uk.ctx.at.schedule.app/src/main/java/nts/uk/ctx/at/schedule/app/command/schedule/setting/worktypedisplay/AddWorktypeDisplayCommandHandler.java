package nts.uk.ctx.at.schedule.app.command.schedule.setting.worktypedisplay;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
/**
 * 
 * @author phongtq
 *
 */
@Transactional
@RequestScoped
public class AddWorktypeDisplayCommandHandler extends CommandHandlerWithResult<AddWorktypeDisplayCommand, List<String>> {

	@Override
	protected List<String> handle(CommandHandlerContext<AddWorktypeDisplayCommand> context) {
		// TODO Auto-generated method stub
		return null;
	}

}
