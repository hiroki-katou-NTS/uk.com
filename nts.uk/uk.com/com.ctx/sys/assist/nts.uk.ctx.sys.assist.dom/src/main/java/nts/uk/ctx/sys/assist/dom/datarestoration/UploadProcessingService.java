package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.error.BusinessException;
import nts.uk.ctx.sys.assist.dom.datarestoration.common.ServerPreparationService;
import nts.uk.ctx.sys.assist.dom.datarestoration.common.ServerUploadProcessingService;

@Stateless
public class UploadProcessingService {
	
	@Inject
	private ServerPrepareMngRepository serverPrepareMngRepository;
	
	@Inject
	private ServerUploadProcessingService serverUploadProcessingService;
	
	@Inject
	private ServerPreparationService serverPreparationService;
	
	
	//アップロード処理
	public ServerPrepareMng uploadProcessing(String fileId, String fileName, String password){
		//サーバー準備動作管理への登録
		String processId = UUID.randomUUID().toString();
		ServerPrepareMng serverPrepareMng = new ServerPrepareMng(processId, null, null, null, 0, null, ServerPrepareOperatingCondition.UPLOADING.value);
		serverPrepareMngRepository.add(serverPrepareMng);
		serverPrepareMng = serverUploadProcessingService.serverUploadProcessing(serverPrepareMng, fileId);
		if (serverPrepareMng.getOperatingCondition() == ServerPrepareOperatingCondition.UPLOAD_COMPLETED){
			serverPreparationService.serverPreparationProcessing(serverPrepareMng);
		}
		return serverPrepareMng;
	}
}
