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
import nts.gul.text.StringUtil;
import nts.uk.ctx.sys.portal.dom.toppagepart.createflowmenu.CreateFlowMenuFileService;
import nts.uk.ctx.sys.portal.dom.toppagepart.createflowmenu.FixedClassification;
import nts.uk.ctx.sys.portal.dom.toppagepart.createflowmenu.FlowMenuLayout;
import nts.uk.ctx.sys.portal.dom.toppagepart.createflowmenu.ImageInformation;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CreateFlowMenuFileServiceImpl implements CreateFlowMenuFileService {

	@Inject
	private StoredFileInfoRepository storedFileInfoRepository;

	@Inject
	private FileStorage fileStorage;
	
	public static final String DATA_STORE_PATH = ServerSystemProperties.fileStoragePath();

	@Override
	public String copyFile(String fileId) {
		try {
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
			return "";
		} catch (IOException e) {
			return "";
    }

	@Override
	public void deleteUploadedFiles(FlowMenuLayout layout) {
		layout.getFileAttachmentSettings().forEach(file -> this.deleteFile(file.getFileId()));
		layout.getImageSettings().forEach(image -> {
			ImageInformation imageInfo = image.getImageInformation();
			if (imageInfo.getIsFixed().equals(FixedClassification.RANDOM) && imageInfo.getFileId().isPresent()) {
				this.deleteFile(imageInfo.getFileId().get());
			}
		});
		layout.getMenuSettings().forEach(menu -> {
			if (menu.getImageInformation().isPresent()) {
				ImageInformation imageInfo = menu.getImageInformation().get();
				if (imageInfo.getIsFixed().equals(FixedClassification.RANDOM) && imageInfo.getFileId().isPresent()) {
					this.deleteFile(imageInfo.getFileId().get());
				}
			}
		});
		this.deleteLayout(layout);
	}

	@Override
	public void deleteLayout(FlowMenuLayout layout) {
		this.deleteFile(layout.getFileId());
	}
	
	private void deleteFile(String fileId) {
		if (!StringUtil.isNullOrEmpty(fileId, true)) {
			this.fileStorage.delete(fileId);
		}
	}
}
