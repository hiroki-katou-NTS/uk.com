package nts.uk.shr.sample.pereg.command;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.shr.pereg.app.command.PeregDeleteCommandHandler;

public class SampleDeletePersonBaseCommandHandler extends CommandHandler<SampleDeletePersonBaseCommand>
		implements PeregDeleteCommandHandler<SampleDeletePersonBaseCommand> {

	@Override
	protected void handle(CommandHandlerContext<SampleDeletePersonBaseCommand> context) {
		
		/* delete process */
	}

	@Override
	public String targetCategoryId() {
		return "CS00001";
	}

	@Override
	public Class<?> commandClass() {
		return SampleDeletePersonBaseCommand.class;
	}

}
