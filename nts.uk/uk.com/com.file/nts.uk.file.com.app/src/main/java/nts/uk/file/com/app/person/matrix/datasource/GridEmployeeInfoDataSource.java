package nts.uk.file.com.app.person.matrix.datasource;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GridEmployeeInfoDataSource {
	private String personId;
	
	private String employeeId;
	
	private String employeeCode;
	
	private String employeeName;
	
	private String employeeBirthday;
	
	private String departmentCode;
	
	private String departmentName;
	
	private String workplaceCode;	
	
	private String workplaceName;	

	private String positionCode;	
	
	private String positionName;
	
	private String employmentCode;	
	
	private String employmentName;
	
	private String classificationCode;	
	
	private String classificationName;	

	/** Contains all columns (fixed and selected category) */
	private List<GridEmpBodyDataSource> items;
	

}
