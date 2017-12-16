package nts.uk.shr.infra.file.storage.info;

import lombok.Value;
import nts.arc.layer.app.file.storage.StoredFileInfo;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.file.storage.filetype.FileTypeDescriptionExtend;

@Value
public class UkStoredFileInfo {

	private final StoredFileInfo fileInfo;
	private final StoredFileSecurityInfo securityInfo;
	
	public boolean isPack() {
		return FileTypeDescriptionExtend.of(this.fileInfo.getFileType())
				.map(t -> t.isPack())
				.orElse(false);
	}
	
	public boolean keepsPack() {
		return FileTypeDescriptionExtend.of(this.fileInfo.getFileType())
				.map(t -> t.keepsPack())
				.orElse(false);
	}
	
	public void checkIfCanStore() {
		FileTypeDescriptionExtend.of(this.fileInfo.getFileType()).ifPresent(fileType -> {
			fileType.checkIfAcceptableExtension(this.getExtension());
			fileType.checkIfCanStore(this.fileInfo, this.securityInfo);
		});
	}
	
	public void checkIfCanTakeOut() {
		if (!this.securityInfo.sameContract(AppContexts.user().contractCode())) {
			throw new RuntimeException("Can not access file of other contracts");
		}

		FileTypeDescriptionExtend.of(this.fileInfo.getFileType()).ifPresent(fileType -> {
			fileType.checkIfCanTakeOut(this.fileInfo, this.securityInfo);
		});
	}
	
	public void checkIfCanDelete() {
		FileTypeDescriptionExtend.of(this.fileInfo.getFileType()).ifPresent(fileType -> {
			fileType.checkIfCanDelete(this.fileInfo, this.securityInfo);
		});
	}
	
	public String getId() {
		return this.fileInfo.getId();
	}
	
	public String getFileName() {
		return this.fileInfo.getOriginalName();
	}
	
	public String getExtension() {
		int point = this.getFileName().lastIndexOf(".");
		return this.getFileName().substring(point + 1);
	}
	
}
