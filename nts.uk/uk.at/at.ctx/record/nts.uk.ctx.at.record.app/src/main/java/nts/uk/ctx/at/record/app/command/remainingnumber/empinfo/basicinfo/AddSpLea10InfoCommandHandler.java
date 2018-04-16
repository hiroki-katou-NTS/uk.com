package nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.SpecialLeaveCode;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

@Stateless
public class AddSpLea10InfoCommandHandler extends CommandHandlerWithResult<SpecialleaveinformationCommand, PeregAddCommandResult>
implements PeregAddCommandHandler<SpecialleaveinformationCommand>{

	@Inject 
	private SpLeaInfoCommandHandler addSpLeaInfoCommandHandler;
	
	@Override
	public String targetCategoryCd() {
		return "CS00034";
	}

	@Override
	public Class<?> commandClass() {
		return SpecialleaveinformationCommand.class;
	}

	@Override
	protected PeregAddCommandResult handle(CommandHandlerContext<SpecialleaveinformationCommand> context) {
		
		val command = context.getCommand();
		
		return new PeregAddCommandResult(addSpLeaInfoCommandHandler.addHandler(command, SpecialLeaveCode.CS00034.value));
	}

}
