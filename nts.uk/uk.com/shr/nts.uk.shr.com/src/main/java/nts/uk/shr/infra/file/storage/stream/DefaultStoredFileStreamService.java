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

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.file.storage.StoredFileInfo;
import nts.arc.layer.infra.file.storage.StoredFileInfoRepository;
import nts.arc.layer.infra.file.storage.StoredFileStreamService;
import nts.arc.system.ServerSystemProperties;
import nts.gul.file.FileUtil;
import nts.gul.security.crypt.commonkey.CommonKeyCrypt;
import nts.uk.shr.infra.file.storage.info.StoredPackInfoRepository;

@Stateless
public class DefaultStoredFileStreamService implements StoredFileStreamService {
	
	@Inject
	private StoredFileInfoRepository fileInfoRepository;
	
	@Inject
	private StoredPackInfoRepository packInfoRepository;

	@Override
	public void store(StoredFileInfo fileInfo, InputStream streamToStore) {
		try {
			Files.copy(CommonKeyCrypt.encrypt(streamToStore, fileInfo.getOriginalSize()), pathToTargetStoredFile(fileInfo.getId()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void storeZipEntry(StoredFileInfo fileInfo, InputStream streamToStore) {
		val pathToEntry = pathToStoredZipEntry(fileInfo);

		try (val is = CommonKeyCrypt.encrypt(streamToStore, fileInfo.getOriginalSize())){
			Files.createDirectories(pathToEntry.getParent());
			Files.copy(is, pathToEntry);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	public InputStream takeOutFromFileId(String fileId) {
		Optional<StoredFileInfo> fileInfo = fileInfoRepository.find(fileId);
		if(!fileInfo.isPresent()){
			throw new BusinessException(new RawErrorMessage("file not found"));
		}
		
		return CommonKeyCrypt.decrypt(
				FileUtil.NoCheck.newInputStream(pathToTargetStoredFile(fileInfo.get().getId())), 
				fileInfo.get().getOriginalSize());
	}
	
	@Override
	public InputStream takeOut(StoredFileInfo fileInfo) {
		Path filePath = null;
		if (fileInfo.isZipEntryFile()) {
			filePath = pathToStoredZipEntry(fileInfo);
		} else {
			filePath = pathToTargetStoredFile(fileInfo.getId());
		}
		
		return CommonKeyCrypt.decrypt(
				FileUtil.NoCheck.newInputStream(filePath), fileInfo.getOriginalSize());
	}

	@Override
	public InputStream takeOutDeleteOnClosed(StoredFileInfo fileInfo) {
		Path filePath = null;
		if (fileInfo.isZipEntryFile()) {
			filePath = pathToStoredZipEntry(fileInfo);
		} else {
			filePath = pathToTargetStoredFile(fileInfo.getId());
		}
		
		return CommonKeyCrypt.decrypt(
				FileUtil.NoCheck.newInputStream(filePath, StandardOpenOption.DELETE_ON_CLOSE), 
				fileInfo.getOriginalSize());
	}

	@Override
	public void delete(String fileId) {
		try {
			Files.deleteIfExists(pathToTargetStoredFile(fileId));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static Path pathToTargetStoredFile(String fileId) {
		return new File(ServerSystemProperties.fileStoragePath()).toPath().resolve(fileId);
	}
	
	private Path pathToStoredZipEntry(StoredFileInfo entryInfo) {
		String packId = this.packInfoRepository.getPackId(entryInfo.getId())
				.orElseThrow(() -> new RuntimeException("pack not found"));
		
		String packsDirectory = "packs";
		
		return new File(ServerSystemProperties.fileStoragePath()).toPath()
				.resolve(packsDirectory)
				.resolve(packId)
				.resolve(entryInfo.getOriginalName());
	}

}
