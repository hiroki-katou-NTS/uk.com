package nts.uk.ctx.exio.app.command.exo.condset;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetService;

public class CopyOutputCondSetCommandHandler extends CommandHandler<CopyOutCondSet>{

	@Inject
	private StdOutputCondSetService stdOutputCondSetService;
	
	@Override
	protected void handle(CommandHandlerContext<CopyOutCondSet> context) {
		
		
	}

	

}
