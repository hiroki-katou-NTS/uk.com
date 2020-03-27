package nts.uk.ctx.at.record.app.command.stamp.management;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.stamp.management.StampSetPerRepository;
/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class UpdateStampSettingPersonCommandHandler extends CommandHandler<AddStampSettingPersonCommand>{

	@Inject
	private StampSetPerRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<AddStampSettingPersonCommand> context) {
		
		AddStampSettingPersonCommand command = context.getCommand();
		repo.update(command.toDomain());
	}
}