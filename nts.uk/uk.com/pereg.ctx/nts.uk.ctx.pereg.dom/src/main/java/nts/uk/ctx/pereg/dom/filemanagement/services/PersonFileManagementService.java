/**
 * 
 */
package nts.uk.ctx.pereg.dom.filemanagement.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.filemanagement.EmpFileManagementRepository;
import nts.uk.ctx.pereg.dom.filemanagement.PersonFileManagement;
import nts.uk.ctx.pereg.dom.filemanagement.TypeFile;

@Stateless
public class PersonFileManagementService {

	@Inject
	private EmpFileManagementRepository empFileManagementRepo;

	/**
	 * [RQ624]個人IDから個人ファイル管理を取得する
	 */
	public List<PersonFileManagementDto> getPersonalFileManagementFromPID(List<String> lstpid) {

		List<PersonFileManagement> listPersonFile = empFileManagementRepo.getPersonalFileManagementFromPID(lstpid);
		
		if (listPersonFile.isEmpty()) 
			return new ArrayList<PersonFileManagementDto>();
		
		List<PersonFileManagementDto> result = new ArrayList<>();
		
		Map<String, List<PersonFileManagement>> mapPidAndListFile = listPersonFile.stream().collect(Collectors.groupingBy(x -> x.getPId()));
		
		mapPidAndListFile.forEach((pid,listFile)->{
			String thumbnailFileId = null, facePhotoFileID = null;
			Optional<String>  mapFileID = Optional.empty();
			Optional<PersonFileManagement> mapFile = listFile.stream().filter(x -> x.getTypeFile() == TypeFile.MAP_FILE).findFirst();
			if (mapFile.isPresent()) {
				mapFileID = Optional.of(mapFile.get().getFileID());
			}
			
			Optional<PersonFileManagement> thumbnailFile = listFile.stream().filter(x -> x.getTypeFile() == TypeFile.AVATAR_FILE).findFirst();
			if (thumbnailFile.isPresent()) {
				thumbnailFileId = thumbnailFile.get().getFileID();
			}	
			
			Optional<PersonFileManagement> facePhotoFile = listFile.stream().filter(x -> x.getTypeFile() == TypeFile.AVATAR_FILE_NOTCROP).findFirst();
			if (facePhotoFile.isPresent()) {
				facePhotoFileID = facePhotoFile.get().getFileID();
			}
			
			List<PersonFileManagement> _listDocumentFile = listFile.stream().filter(x -> x.getTypeFile() == TypeFile.DOCUMENT_FILE).collect(Collectors.toList());
			
			List<DocumentFile> listDocumentFile = new ArrayList<>();
			
			if (!_listDocumentFile.isEmpty()) {
				listDocumentFile = _listDocumentFile.stream().map(x -> {
					return new DocumentFile(x.getFileID(), x.getUploadOrder().intValue());
				}).collect(Collectors.toList());
			}
			Optional<FacePhotoFile> faceObj = Optional.empty();
			if (thumbnailFileId != null || facePhotoFileID != null) {
				faceObj = Optional.of(new FacePhotoFile(thumbnailFileId, facePhotoFileID));
			}
			
			PersonFileManagementDto obj = new PersonFileManagementDto(pid, faceObj, mapFileID, listDocumentFile);
			
			result.add(obj);
		});
		
		return result;

	}
}
