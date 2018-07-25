package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.security.crypt.commonkey.CommonKeyCrypt;
import nts.uk.ctx.sys.assist.dom.datarestoration.common.ServerPreparationService;
import nts.uk.ctx.sys.assist.dom.storage.ResultOfSaving;
import nts.uk.ctx.sys.assist.dom.storage.ResultOfSavingRepository;

@Stateless
public class ServerZipFileTempService {
	@Inject
	private ServerPrepareMngRepository repoServerPrepare;
	@Inject
	private ResultOfSavingRepository repoResultOfSaving;
	@Inject
	private ServerPreparationService serverPreparationService;
	public ServerPrepareMng handleServerZipFile(String recoveryProcessingId, String storeProcessingId){
		Optional<ResultOfSaving> optResultOfSaving = repoResultOfSaving.getResultOfSavingById(storeProcessingId);
		if (optResultOfSaving.isPresent()) {
			String fileId = optResultOfSaving.get().getFileId().orElse(null);
			String uploadFileName = optResultOfSaving.get().getSaveFileName().map(i -> i.v()).orElse(null);
			Integer doNotUpload = 0;
			String password = optResultOfSaving.get().getCompressedPassword().map(i -> CommonKeyCrypt.decrypt(i.v())).orElse(null);
			Integer operatingCondition = 13;
			ServerPrepareMng serverPrepareMng = new ServerPrepareMng(recoveryProcessingId, storeProcessingId, fileId,
					uploadFileName, doNotUpload, password, operatingCondition);
			repoServerPrepare.add(serverPrepareMng);
			// サーバー準備処理
			serverPrepareMng = serverPreparationService.serverPreparationProcessing(serverPrepareMng);
			return serverPrepareMng;
		}
		return null;
	}
}
