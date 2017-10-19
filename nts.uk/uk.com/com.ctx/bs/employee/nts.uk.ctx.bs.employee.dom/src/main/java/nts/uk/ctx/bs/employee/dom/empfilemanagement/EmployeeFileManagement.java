package nts.uk.ctx.bs.employee.dom.empfilemanagement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeFileManagement extends AggregateRoot{
	
	/**
	 * domain : 社員ファイル管理
	 */
	
	/** The employeeId 社員ID */
	private String sId;
	
	/** The fileID*/
	private String fileID;
	
	/** The type file (0 : avatarfile, 1: mapfile , 2 : documentfile) */
	private int typeFile;

	/** order document file*/
	private Integer uploadOrder;

	/** The PersonalInformationCtgID */
	private String personInfoCategoryId;
	
	public static EmployeeFileManagement createFromJavaType(String sId, String fileID,int typeFile , Integer uploadOrder , String personInfoCategoryId) {
		return new EmployeeFileManagement(sId, fileID, typeFile, uploadOrder, personInfoCategoryId);
	}

}
