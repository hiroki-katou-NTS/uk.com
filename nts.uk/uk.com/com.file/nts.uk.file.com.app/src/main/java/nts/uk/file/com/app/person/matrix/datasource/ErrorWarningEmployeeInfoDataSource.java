package nts.uk.file.com.app.person.matrix.datasource;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorWarningEmployeeInfoDataSource {
	private String employeeId;
	private String employeeCd;
	private String employeeName;
	private int order;
	private List<ErrorWarningInfoOfRowOrderDataSource> errorLst;
	

}
