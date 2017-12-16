package nts.uk.shr.infra.file.storage.filetype;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import nts.arc.error.BusinessException;
import nts.arc.i18n.I18NText;
import nts.arc.layer.app.file.storage.StoredFileInfo;
import nts.uk.shr.infra.file.storage.info.StoredFileSecurityInfo;

@RequiredArgsConstructor
public class FileTypeDescriptionExtend {

	private final FileTypeDescription description;

	public static Optional<FileTypeDescriptionExtend> of(String nameOfFileType) {
		return FileTypeDef.of(nameOfFileType).map(d -> new FileTypeDescriptionExtend(d));
	}
	
	public boolean isPack() {
		return this.description.isPack();
	}
	
	public boolean keepsPack() {
		return this.description.keepsPack();
	}
	
	public void checkIfAcceptableExtension(String targetExtension) {
		if (this.acceptsAllExtensions()) {
			return;
		}
		
		if (this.acceptsExtension(targetExtension)) {
			return;
		}
		
		throw new BusinessException(
				I18NText.main("Msg_77").addRaw(this.listOfAcceptableExtensions()).build());
	}
	
	public String listOfAcceptableExtensions() {
		return String.join(", ", this.description.acceptableExtensions());
	}
	
	public void checkIfCanStore(StoredFileInfo fileInfo, StoredFileSecurityInfo securityInfo) {
		this.description.checkIfCanStore(fileInfo, securityInfo);
	}
	
	public void checkIfCanTakeOut(StoredFileInfo fileInfo, StoredFileSecurityInfo securityInfo) {
		this.description.checkIfCanTakeOut(fileInfo, securityInfo);
	}
	
	public void checkIfCanDelete(StoredFileInfo fileInfo, StoredFileSecurityInfo securityInfo) {
		this.description.checkIfCanDelete(fileInfo, securityInfo);
	}
	
	private boolean acceptsAllExtensions() {
		return this.description.acceptableExtensions().isEmpty();
	}
	
	private boolean acceptsExtension(String targetExtension) {
		return this.description.acceptableExtensions().stream()
				.map(e -> e.toLowerCase())
				.anyMatch(e -> e.equals(targetExtension.toLowerCase()));
	}
}
