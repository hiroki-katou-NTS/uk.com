package nts.uk.pr.file.infra.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.file.FileMetaData;
import nts.arc.file.StoredFileInfoRepository;
import nts.arc.task.AsyncTaskInfoRepository;
import nts.arc.task.AsyncTaskStatus;

@Stateless
public class FileTaskRepoImpl implements FileTaskRepository{

	@Inject
	private AsyncTaskInfoRepository taskInfo;
	
	@Inject
	private StoredFileInfoRepository fileStore;
	
	@Override
	public boolean isFinishedTask(String taskId) {
		return this.taskInfo.find(taskId).get().getStatus() == AsyncTaskStatus.FINISHED;
	}

	@Override
	public InputStream downloadFile(String fileId) {
        FileMetaData fileMeta = fileStore.findByFileId(fileId).orElseThrow(
				() -> new RuntimeException("File not found"));
        String fileStoragePath = fileMeta.getFilePath();
		File file = new File(fileStoragePath);// + fileMeta.getName());
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getFileIdByTask(String taskId) {
		return fileStore.findByTaskId(taskId).orElseThrow(
				() -> new RuntimeException("File not found")).getFileId();
	}

	@Override
	public FileMetaData getFileMetaData(String taskId) {
		return this.fileStore.findByTaskId(taskId).orElseThrow(
				() -> new RuntimeException("File not found"));
	}

}
