package nts.uk.file.at.app.export.roledaily;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @author hoidd
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkTypeDtoExcel {
	private String codeEmp;
	private String nameEmp;
	private int groupNo;
	private String groupName;
	private String workTypeName;
	
	public void setCodeEmpAndName(String codeEmp, String nameEmp){
		this.nameEmp = nameEmp;
		this.codeEmp = codeEmp;
	}
}
