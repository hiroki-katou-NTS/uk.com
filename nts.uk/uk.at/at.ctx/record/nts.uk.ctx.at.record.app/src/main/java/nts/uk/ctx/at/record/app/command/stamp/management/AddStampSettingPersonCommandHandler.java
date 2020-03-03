package nts.uk.ctx.at.record.app.command.stamp.management;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.stamp.management.StampSetPerRepository;

@Stateless
public class AddStampSettingPersonCommandHandler extends CommandHandler<AddStampSettingPersonCommand>{

	@Inject
	private StampSetPerRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<AddStampSettingPersonCommand> context) {
		
		AddStampSettingPersonCommand command = context.getCommand();
		repo.insert(command.toDomain());
	}
}
