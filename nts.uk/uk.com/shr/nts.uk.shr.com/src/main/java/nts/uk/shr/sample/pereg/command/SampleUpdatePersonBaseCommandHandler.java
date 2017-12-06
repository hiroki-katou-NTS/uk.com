package nts.uk.shr.sample.pereg.command;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

@Stateless
public class SampleUpdatePersonBaseCommandHandler extends CommandHandler<SampleUpdatePersonBaseCommand>
		implements PeregUpdateCommandHandler<SampleUpdatePersonBaseCommand> {

	@Override
	protected void handle(CommandHandlerContext<SampleUpdatePersonBaseCommand> context) {
		
		val command = context.getCommand();
		String fullName = command.getFullName();
		fullName.toString();
	}

	@Override
	public String targetCategoryCd() {
		return "CS00001";
	}

	@Override
	public Class<?> commandClass() {
		return SampleUpdatePersonBaseCommand.class;
	}

}
