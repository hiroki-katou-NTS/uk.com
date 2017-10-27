package nts.uk.shr.infra.file.storage.stream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.file.storage.FileStorage;
import nts.arc.layer.app.file.storage.StoredFileInfo;
import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.arc.system.ServerSystemProperties;
import nts.gul.file.FileUtil;

@Stateless
public class DefaultStoredFileStreamService implements StoredFileStreamService {
	@Inject
	private FileStorage fileStorage;

	@Override
	public void store(StoredFileInfo fileInfo, InputStream streamToStore) {
		try {
			Files.copy(streamToStore, pathToTargetStoredFile(fileInfo.getId()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public InputStream takeOutFromFileId(String fileId) {
		Optional<StoredFileInfo> fileInfo = fileStorage.getInfo(fileId);
		if(!fileInfo.isPresent()){
			throw new BusinessException(new RawErrorMessage("file not found"));
		}
		
		return FileUtil.NoCheck.newInputStream(pathToTargetStoredFile(fileInfo.get().getId()));
	}
	@Override
	public InputStream takeOut(StoredFileInfo fileInfo) {
		return FileUtil.NoCheck.newInputStream(pathToTargetStoredFile(fileInfo.getId()));
	}

	@Override
	public InputStream takeOutDeleteOnClosed(StoredFileInfo fileInfo) {
		return FileUtil.NoCheck.newInputStream(pathToTargetStoredFile(fileInfo.getId()),
				StandardOpenOption.DELETE_ON_CLOSE);
	}

	@Override
	public void delete(String fileId) {
		try {
			Files.delete(pathToTargetStoredFile(fileId));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static Path pathToTargetStoredFile(String fileId) {
		return new File(ServerSystemProperties.fileStoragePath()).toPath().resolve(fileId);
	}

}
