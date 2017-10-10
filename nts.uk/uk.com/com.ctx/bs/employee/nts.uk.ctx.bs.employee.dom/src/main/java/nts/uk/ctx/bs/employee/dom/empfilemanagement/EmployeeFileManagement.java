package nts.uk.ctx.bs.employee.dom.empfilemanagement;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@AllArgsConstructor
@Getter
public class EmployeeFileManagement extends AggregateRoot{
	
	/** The employeeId 社員ID */
	private String sId;
	
	/** The avatarFileID*/
	private String avatarFileID;
	
	
	/** The MapFileID */
	private String mapFileID;

	/** The DocumentFileID */
	private List<DocumentFile> documentFileID;
	
	public static EmployeeFileManagement createFromJavaType(String sId, String avatarFileID,String mapFileID ) {
		return new EmployeeFileManagement(sId, avatarFileID, mapFileID, null);
	}
	
	

}
