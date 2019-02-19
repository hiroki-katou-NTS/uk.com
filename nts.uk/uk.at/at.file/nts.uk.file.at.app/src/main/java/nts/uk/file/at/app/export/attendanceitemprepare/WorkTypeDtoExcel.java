package nts.uk.file.at.app.export.attendanceitemprepare;

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
	private String workTypeCode;
	private String workTypeName;
	
	
	public void setCodeAndNameWorkType(String workTypeCode, String workTypeName){
		this.workTypeCode = workTypeCode;
		this.workTypeName = workTypeName;
	}
}
