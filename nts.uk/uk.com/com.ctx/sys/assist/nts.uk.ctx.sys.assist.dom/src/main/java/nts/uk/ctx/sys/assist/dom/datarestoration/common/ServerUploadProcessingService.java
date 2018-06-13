package nts.uk.ctx.sys.assist.dom.datarestoration.common;

import java.io.File;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.file.storage.StoredFileInfo;
import nts.arc.layer.infra.file.storage.StoredFileInfoRepository;
import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.gul.file.FileUtil;
import nts.gul.file.archive.FileArchiver;
import nts.gul.security.crypt.commonkey.CommonKeyCrypt;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMngRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareOperatingCondition;

@Stateless
public class ServerUploadProcessingService {
	@Inject
	private StoredFileInfoRepository fileInfoRepository;
	@Inject
	private ServerPrepareMngRepository serverPrepareMngRepository;
	//アルゴリズム「サーバーアップロード処理」を実行する
	public ServerPrepareMng serverUploadProcessing(ServerPrepareMng serverPrepareMng, String fileId){
		Optional<StoredFileInfo> fileInfo = fileInfoRepository.find(fileId);
		if(fileInfo.isPresent()){
			serverPrepareMng.setFileId(Optional.of(fileId));
			serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.UPLOAD_COMPLETED);
		} else {
			serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.UPLOAD_FAILED);
		}
		serverPrepareMngRepository.update(serverPrepareMng);
		return serverPrepareMng;
		
	}
}


