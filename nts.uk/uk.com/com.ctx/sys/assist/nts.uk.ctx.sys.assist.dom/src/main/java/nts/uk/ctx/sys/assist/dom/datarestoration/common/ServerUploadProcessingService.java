package nts.uk.ctx.sys.assist.dom.datarestoration.common;

import java.io.File;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.gul.file.archive.FileArchiver;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareOperatingCondition;

@Stateless
public class ServerUploadProcessingService {
	
	private FileArchiver fileArchiver;
	private static final String TEMP_PATH = "D://UK//temp";
	private static final String PACK_PATH = "D://UK//temp//packs";
	@Inject
	private StoredFileStreamService fileStreamService;
	@Inject
	private DataExtractionService dataExtractionService;
	//アルゴリズム「サーバーアップロード処理」を実行する
	public ServerPrepareMng serverUploadProcessing(ServerPrepareMng serverPrepareMng){
		String fileId = serverPrepareMng.getFileId().get();
		File uploadedFile = new File(TEMP_PATH + "//" + fileId +".zip");
		//TODO
		// Waiting for Kiban
//		if (uploadedFile.exists()){
//			serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.UPLOAD_COMPLETED);
//		} else {
//			serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.UPLOAD_FAILED);
//		}
		serverPrepareMng = dataExtractionService.extractData(serverPrepareMng);
		
		return serverPrepareMng;
		
	}
}


