package nts.uk.file.at.app.export.statement.stamp;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OutputConditionListOfStampQuery {

	private StampHeader header; 
	//data list
	private List<EmployeeInfor> employeeList;
}
