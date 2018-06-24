package nts.uk.ctx.sys.assist.app.command.datarestoration;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.security.crypt.commonkey.CommonKeyCrypt;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMngRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.common.ServerPreparationService;
import nts.uk.ctx.sys.assist.dom.storage.ResultOfSaving;
import nts.uk.ctx.sys.assist.dom.storage.ResultOfSavingRepository;

@Stateless
@Transactional
public class ObtainRecoveryInfoCommandHandler extends CommandHandlerWithResult<ObtainRecoveryInfoCommand, String> {
	@Inject
	private ServerPrepareMngRepository repoServerPrepare;
	@Inject
	private ResultOfSavingRepository repoResultOfSaving;
	@Inject
	private ServerPreparationService serverPreparationService;

	protected String handle(CommandHandlerContext<ObtainRecoveryInfoCommand> context) {
		ObtainRecoveryInfoCommand obtainRecoveryInfoCommand = context.getCommand();
		Optional<ResultOfSaving> optResultOfSaving = repoResultOfSaving
				.getResultOfSavingById(obtainRecoveryInfoCommand.getStoreProcessingId());
		if (optResultOfSaving.isPresent()) {
			String dataRecoveryProcessId = obtainRecoveryInfoCommand.getDataRecoveryProcessId();
			String dataStoreProcessId = obtainRecoveryInfoCommand.getStoreProcessingId();
			String fileId = optResultOfSaving.get().getFileId();
			String uploadFileName = optResultOfSaving.get().getSaveFileName().v();
			Integer doNotUpload = 0;
			String password = optResultOfSaving.get().getCompressedPassword().v();
			Integer operatingCondition = 13;
			ServerPrepareMng serverPrepareMng = new ServerPrepareMng(dataRecoveryProcessId, dataStoreProcessId, fileId,
					uploadFileName, doNotUpload, password, operatingCondition);
			repoServerPrepare.add(serverPrepareMng);
			// サーバー準備処理
			String msg = serverPreparationService.serverPreparationProcessing(serverPrepareMng);
			return msg;
		}
		return null;
	}

}
