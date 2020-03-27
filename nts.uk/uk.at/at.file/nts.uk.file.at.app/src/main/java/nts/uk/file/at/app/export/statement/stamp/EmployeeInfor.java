package nts.uk.file.at.app.export.statement.stamp;

import java.util.List;

import lombok.Data;

@Data
public class EmployeeInfor {

	private String workplace;
	private String employee;
	private String cardNo;
	//data list
	private List<StampList> stampList;
}
