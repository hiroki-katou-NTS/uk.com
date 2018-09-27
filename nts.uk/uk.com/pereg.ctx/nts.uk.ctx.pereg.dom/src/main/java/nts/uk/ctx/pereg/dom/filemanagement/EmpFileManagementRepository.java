package nts.uk.ctx.pereg.dom.filemanagement;

import java.util.List;
import java.util.Optional;

public interface EmpFileManagementRepository {
	
	void insert(PersonFileManagement domain);
	
	void update(PersonFileManagement domain);
	
	void remove(PersonFileManagement domain);
	
	void removebyFileId(String fileId);
	
	List<PersonFileManagement> getDataByParams(String employeeId, int fileType);
	
	boolean checkObjectExist(String employeeId, int fileType);
	
	Optional<PersonFileManagement> getEmpMana(String fileid);

	List<Object[]> getListDocumentFile(String employeeId, int fileType);
	
}
