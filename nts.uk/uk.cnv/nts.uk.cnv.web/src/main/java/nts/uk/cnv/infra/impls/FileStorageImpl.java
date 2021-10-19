package nts.uk.cnv.infra.impls;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import nts.arc.layer.app.file.storage.FileStorage;
import nts.arc.layer.app.file.storage.StoredFileInfo;

@RequestScoped
public class FileStorageImpl implements FileStorage {

	@Override
	public StoredFileInfo store(Path pathToSource, String originalFileName, String fileType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoredFileInfo storeTemporary(Path pathToSource, String originalFileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoredFileInfo storeWithId(String fileId, String fileType, Path pathToSource, String originalFileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoredFileInfo storeTemporaryWithId(String fileId, Path pathToSource, String originalFileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<StoredFileInfo> getInfo(String fileId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<StoredFileInfo> getZipEntryInfo(String fileId, String entryName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<InputStream> getStream(String fileId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getStream(StoredFileInfo fileInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String fileId) {
		// TODO Auto-generated method stub
		
	}

}
