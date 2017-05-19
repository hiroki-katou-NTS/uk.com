package nts.uk.shr.infra.file.constraint;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import org.apache.logging.log4j.util.Strings;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.file.storage.StoredFileInfo;
import nts.arc.layer.infra.file.constraint.FileConstraintsChecker;
import nts.arc.layer.infra.file.constraint.FileStereo;

@Stateless
public class FileConstraintsCheckerImp implements FileConstraintsChecker {

	@Override
	public void canDownload(String stereoTypeName) {
	}

	@Override
	public void canUpload(StoredFileInfo fileInfor) {
		Objects.nonNull(fileInfor);

		Optional<FileStereo> stereo = FileStereoFactory.of(fileInfor.getFileType());
		if (stereo.isPresent()) {
			FileStereo fileStereo = stereo.get();
			// check size
			if (fileInfor.getOriginalSize() > fileStereo.getLimitedSize()) {
				throw new BusinessException("Msg_70", (fileStereo.getLimitedSize() / 1024) + "");
			}
			// authorization
			// TODO:
			// check extension
			if (!fileStereo.getSupportedExtension().isEmpty()
					&& !fileStereo.getSupportedExtension().contains(getFileExtension(fileInfor.getOriginalName()))) {
				throw new BusinessException("Msg_77",
						fileStereo.getSupportedExtension().stream().collect(Collectors.joining(",")));
			}
		}
	}

	private String getFileExtension(String fileName) {
		int index = fileName.lastIndexOf('.');
		if (index == -1 || index == fileName.length() - 1) {
			return Strings.EMPTY;
		}

		return fileName.substring(index + 1);
	}

	@Override
	public void canDelete(String stereoTypeName) {
	}
}
