package nts.uk.ctx.pereg.app.command.process.checkdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.pereg.app.find.layout.dto.EmpMaintLayoutDto;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DataEmployeeToCheck {
	
	String employeeId;
	
	String categoryId;
	
	String employeeCode;
	
	String businessName;
	
	String categoryName;
	
	String checkType;
	
	String categoryCode;
	
	int categoryType;
	
	EmpMaintLayoutDto data;
	

}
