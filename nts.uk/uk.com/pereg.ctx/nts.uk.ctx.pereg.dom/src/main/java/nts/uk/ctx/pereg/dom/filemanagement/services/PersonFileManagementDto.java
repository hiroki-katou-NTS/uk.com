package nts.uk.ctx.pereg.dom.filemanagement.services;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PersonFileManagementDto {
	
	private String pId;

	private Optional<FacePhotoFile>avatarFile;
	
	private Optional<String> mapFileID;
	
	private List<DocumentFile> listDocumentFile;
	

}
