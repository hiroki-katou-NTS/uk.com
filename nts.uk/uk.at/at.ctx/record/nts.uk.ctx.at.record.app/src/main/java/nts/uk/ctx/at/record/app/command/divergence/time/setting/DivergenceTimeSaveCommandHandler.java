package nts.uk.ctx.at.record.app.command.divergence.time.setting;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTime;

public class DivergenceTimeSaveCommandHandler extends CommandHandler<DivergenceTimeSaveCommand>{

	@Override
	protected void handle(CommandHandlerContext<DivergenceTimeSaveCommand> context) {
		DivergenceTimeSaveCommand command = context.getCommand();
		
		DivergenceTime divergenceTimeDomain = new DivergenceTime(command);
//		DivergenceReasonInputMethod divergenceReasonInputMethod =  new DivergenceReasonInputMethod(command);
	}



}
