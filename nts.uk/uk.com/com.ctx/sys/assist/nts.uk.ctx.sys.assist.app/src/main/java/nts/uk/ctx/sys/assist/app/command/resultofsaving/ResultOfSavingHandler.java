package nts.uk.ctx.sys.assist.app.command.resultofsaving;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.file.storage.FileStorage;
import nts.arc.layer.app.file.storage.StoredFileInfo;
import nts.uk.ctx.sys.assist.dom.storage.ResultOfSavingRepository;

@Stateless
/**
 * データ保存の保存結果
 */
public class ResultOfSavingHandler extends CommandHandler<ResultOfSavingCommand>{

	@Inject
	private ResultOfSavingRepository repoResultSaving;

	@Inject
	private FileStorage fileStorage;
	
	@Override
	protected void handle(CommandHandlerContext<ResultOfSavingCommand> context) {
		val command = context.getCommand();
		// Get file info
		Optional<StoredFileInfo> fileInfo = fileStorage.getInfo(command.getFileId());
		
		if (fileInfo.isPresent()){
			repoResultSaving.update(command.getStoreProcessingId(),fileInfo.get().getOriginalSize());
		}
		
	}

	
}
