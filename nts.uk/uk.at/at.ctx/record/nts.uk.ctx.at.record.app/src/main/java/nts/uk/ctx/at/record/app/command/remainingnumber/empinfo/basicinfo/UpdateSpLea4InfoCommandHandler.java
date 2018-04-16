package nts.uk.ctx.at.record.app.command.remainingnumber.empinfo.basicinfo;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.SpecialLeaveCode;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

@Stateless
public class UpdateSpLea4InfoCommandHandler extends CommandHandler<SpecialleaveinformationCommand>
implements PeregUpdateCommandHandler<SpecialleaveinformationCommand>{

	@Inject 
	private SpLeaInfoCommandHandler updateSpLeaInfoCommandHandler;
	
	@Override
	public String targetCategoryCd() {
		return "CS00028";
	}

	@Override
	public Class<?> commandClass() {
		return SpecialleaveinformationCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<SpecialleaveinformationCommand> context) {
		
		val command = context.getCommand();
		
		updateSpLeaInfoCommandHandler.addHandler(command, SpecialLeaveCode.CS00028.value);
	}

}
