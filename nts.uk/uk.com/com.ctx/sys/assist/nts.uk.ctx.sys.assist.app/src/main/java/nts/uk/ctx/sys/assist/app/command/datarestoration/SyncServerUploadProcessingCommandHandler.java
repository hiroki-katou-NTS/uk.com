package nts.uk.ctx.sys.assist.app.command.datarestoration;

import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.assist.dom.datarestoration.UploadProcessingService;

@Stateless
public class SyncServerUploadProcessingCommandHandler extends AsyncCommandHandler<SyncServerUploadProcessingCommand> {
	
	@Inject
	private UploadProcessingService uploadProcessingService;
	
	@Override
	protected void handle(CommandHandlerContext<SyncServerUploadProcessingCommand> context) {
		val asyncTask = context.asAsync();
		uploadProcessingService.uploadProcessing(context.getCommand().getProcessingId(), context.getCommand().getFileId(), context.getCommand().getFileName(), context.getCommand().getPassword());
	}
	
}
