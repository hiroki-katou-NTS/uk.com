package nts.uk.pr.file.infra.file;

import java.io.InputStream;

import nts.arc.file.FileMetaData;

public interface FileTaskRepository {

	boolean isFinishedTask(String taskId);
	
	InputStream downloadFile(String fileId) ;
	
	FileMetaData getFileMetaData(String taskId) ;
	
	String getFileIdByTask(String taskId);
}
