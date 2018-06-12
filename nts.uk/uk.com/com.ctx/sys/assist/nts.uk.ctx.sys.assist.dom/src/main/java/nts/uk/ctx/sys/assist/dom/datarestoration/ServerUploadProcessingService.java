package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.io.File;
import java.nio.file.Paths;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.file.archive.FileArchiver;

@Stateless
public class ServerUploadProcessingService {
	
	private FileArchiver fileArchiver;
	
	private static final String tempPath = "D:\\UK\\temp";
	
	public void serverUploadProcessing(ServerPrepareMng serverPrepareMng, File uploadedFile){
		
		if (serverPrepareMng.getPassword().isPresent()){
			fileArchiver.extract(uploadedFile.toPath(), serverPrepareMng.getPassword().get().toString(), Paths.get(tempPath + "\\" + serverPrepareMng.getFileId().get()));
		} else {
			fileArchiver.extract(uploadedFile.toPath(), Paths.get(tempPath + "\\" + serverPrepareMng.getFileId().get()));
		}
		
	}
}
