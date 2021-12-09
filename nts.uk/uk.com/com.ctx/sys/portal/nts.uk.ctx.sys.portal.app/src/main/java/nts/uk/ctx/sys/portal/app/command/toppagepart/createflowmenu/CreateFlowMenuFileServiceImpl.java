package nts.uk.ctx.sys.portal.app.command.toppagepart.createflowmenu;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.shr.infra.file.storage.stream.FileStoragePath;
import org.apache.commons.io.FileUtils;

import nts.arc.layer.app.file.storage.FileStorage;
import nts.arc.layer.app.file.storage.StoredFileInfo;
import nts.arc.layer.infra.file.storage.StoredFileInfoRepository;
import nts.arc.system.ServerSystemProperties;
import nts.uk.ctx.sys.portal.dom.toppagepart.createflowmenu.CreateFlowMenuFileService;
import nts.uk.ctx.sys.portal.dom.toppagepart.createflowmenu.FixedClassification;
import nts.uk.ctx.sys.portal.dom.toppagepart.createflowmenu.FlowMenuLayout;

@Stateless
public class CreateFlowMenuFileServiceImpl implements CreateFlowMenuFileService {

	@Inject
	private StoredFileInfoRepository storedFileInfoRepository;

	@Inject
	private FileStorage fileStorage;

	@Override
	public String copyFile(String fileId) throws IOException {
		// Get original file information
		Optional<StoredFileInfo> optFileInfo = this.storedFileInfoRepository.find(fileId);
		if (optFileInfo.isPresent()) {
			StoredFileInfo fileInfo = optFileInfo.get();
			// Copy file info, change to new fileId
			StoredFileInfo newFileInfo = StoredFileInfo.createNew(fileInfo.getOriginalName(), fileInfo.getFileType(),
					fileInfo.getMimeType(), fileInfo.getOriginalSize());
			String newFileId = newFileInfo.getId();
			// Copy physical file
			File file = Paths.get(new FileStoragePath().getPathOfCurrentTenant().toString() + "//" + fileId).toFile();
			File newFile = new File(new FileStoragePath().getPathOfCurrentTenant().toString() + "//" + newFileId);
			newFile.createNewFile();
			FileUtils.copyFile(file, newFile, false);
			// Persist
			this.storedFileInfoRepository.add(newFileInfo);
			return newFileId;
		}
		return null;
	}

	@Override
	public void deleteUploadedFiles(FlowMenuLayout layout) {
		layout.getFileAttachmentSettings().forEach(file -> this.fileStorage.delete(file.getFileId()));
		layout.getImageSettings().forEach(image -> {
			if (image.getIsFixed().equals(FixedClassification.RANDOM) && image.getFileId().isPresent()) {
				this.fileStorage.delete(image.getFileId().get());
			}
		});
		this.deleteLayout(layout);
	}

	@Override
	public void deleteLayout(FlowMenuLayout layout) {
		if (layout.getFileId() != null) {
			this.fileStorage.delete(layout.getFileId());
		}
	}

}
