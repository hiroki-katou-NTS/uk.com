package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.error.BusinessException;
import nts.uk.ctx.sys.assist.dom.datarestoration.common.ServerUploadProcessingService;

@Stateless
public class UploadProcessingService {
	
	private static final String tempPath = "D://UK//temp";
	@Inject
	private ServerPrepareMngRepository serverPrepareMngRepository;
	
	@Inject
	private ServerUploadProcessingService serverUploadProcessingService;
	public ServerPrepareMng uploadProcessing(String fileId, String fileName, String password){
		//サーバー準備動作管理への登録
		String processId = UUID.randomUUID().toString();
		ServerPrepareMng serverPrepareMng = new ServerPrepareMng(processId, null, fileId, fileName, 1, password, ServerPrepareOperatingCondition.UPLOADING.value);
		serverPrepareMng = serverUploadProcessingService.serverUploadProcessing(serverPrepareMng, fileId);
		return serverPrepareMng;
	}
	public static void main(String[] args) {
		String filePath = "D://UK//temp";
		File f = new File (filePath);
		String[] listFile = f.list();
		for(String a : listFile){
			//System.out.println(a);
			File f1 = new File(filePath + "//" + a);
			System.out.println(f1.exists());
			System.out.println("Path = " + f1.getAbsolutePath());
		}
	}
}
