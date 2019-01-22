package nts.uk.ctx.pereg.app.find.filemanagement;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EmployeeRowDto {
	private  String personId;
	private String employeeId;
	private String employeeCode;
	private String employeeName;
	private int numberOfError;
	private List<ItemRowDto> items;

}
