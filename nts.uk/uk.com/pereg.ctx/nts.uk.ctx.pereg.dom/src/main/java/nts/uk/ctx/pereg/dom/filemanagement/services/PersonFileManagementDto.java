package nts.uk.ctx.pereg.dom.filemanagement.services;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PersonFileManagementDto {
	
	private String pId;

	private FacePhotoFile avatarFile;
	
	private String mapFileID;
	
	private DocumentFile documentFile;
	

}
