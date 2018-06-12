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
	
	public ServerPrepareMng serverUploadProcessing(ServerPrepareMng serverPrepareMng, String fileId){
		File uploadedFile = new File(TEMP_PATH + "//" + fileId +".zip");
		if (uploadedFile.exists()){
			serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.UPLOAD_COMPLETED);
		} else {
			serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.UPLOAD_FAILED);
		}
		return serverPrepareMng;
		
	}
	public static void main(String[] args) {
		String path = "D://UK//temp//packs//8dbbe356-9a17-4336-b089-fc22df3e0baf";
		File f = new File(path);
		for(String filePath : f.list()){
			System.out.println(filePath);
		}
	}
}


