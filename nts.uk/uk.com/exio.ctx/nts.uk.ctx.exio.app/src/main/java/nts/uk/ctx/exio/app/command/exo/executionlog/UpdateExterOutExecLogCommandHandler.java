package nts.uk.ctx.exio.app.command.exo.executionlog;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.file.storage.FileStorage;
import nts.arc.layer.app.file.storage.StoredFileInfo;
import nts.uk.ctx.exio.dom.exo.execlog.ExterOutExecLogRepository;

@Stateless
public class UpdateExterOutExecLogCommandHandler extends CommandHandler<ExterOutExecLogCommand> {

	@Inject
	private ExterOutExecLogRepository repository;

	@Inject
	private FileStorage fileStorage;

	@Override
	protected void handle(CommandHandlerContext<ExterOutExecLogCommand> context) {
		val command = context.getCommand();
		Optional<StoredFileInfo> fileInfo = fileStorage.getInfo(command.getFileId());
		if (fileInfo.isPresent()) {
			repository.update(command.getCompanyId(), command.getOutputProcessId(), fileInfo.get().getOriginalSize());
		}
	}
}
