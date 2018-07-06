package nts.uk.ctx.exio.app.command.exo.condset;

import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetService;

@Stateless
public class ExcuteCopyOutCondSetCommandHandler
		extends CommandHandlerWithResult<StdOutputCondSetCommand, Map<String, String>> {

	@Inject
	private StdOutputCondSetService stdOutputCondSetService;

	@Override
	protected Map<String, String> handle(CommandHandlerContext<StdOutputCondSetCommand> context) {
		StdOutputCondSetCommand command = context.getCommand();
		return stdOutputCondSetService.excuteCopy(
				command.getCopyDestinationCode(),
				command.getDestinationName(),
				command.getConditionSetCd(),
				command.isOverWrite());
	}

}
