package nts.uk.ctx.sys.assist.app.command.datarestoration;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerZipFileTempService;

@Stateless
public class ObtainRecoveryInfoCommandHandler extends CommandHandlerWithResult<ObtainRecoveryInfoCommand, String> {
	@Inject
	private ServerZipFileTempService serverZipFileTempService;

	protected String handle(CommandHandlerContext<ObtainRecoveryInfoCommand> context) {
		return serverZipFileTempService.handleServerZipFile(context.getCommand().getDataRecoveryProcessId(), context.getCommand().getStoreProcessingId());
	}

}
