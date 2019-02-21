package nts.uk.file.com.app.person.check.consistency.datasource;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmployeInfoErrorDataSource {
	private String employeeCode;
	private String employeeName;
	private String checkAtr;
	private String categoryName;
	private String contentError;
}
