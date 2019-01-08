package nts.uk.ctx.exio.app.command.exo.createexouttext;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.exoutsummaryservice.CreateExOutTextService;
import nts.uk.ctx.exio.dom.exo.exoutsummaryservice.ExOutSetting;

@Stateless
public class CreateExOutTextCommandHandler extends AsyncCommandHandler<CreateExOutTextCommand> {

	@Inject
	private CreateExOutTextService createExOutTextService;
	
	@Override
	protected void handle(CommandHandlerContext<CreateExOutTextCommand> context) {
		CreateExOutTextCommand command = context.getCommand();
		ExOutSetting exOutSetting = new ExOutSetting(command.getConditionSetCd(), command.getUserId(), command.getCategoryId(), command.getStartDate(), command.getEndDate(),
				command.getReferenceDate(), command.getProcessingId(), command.isStandardType(), command.getSidList());
		createExOutTextService.start(exOutSetting);
	}

}
