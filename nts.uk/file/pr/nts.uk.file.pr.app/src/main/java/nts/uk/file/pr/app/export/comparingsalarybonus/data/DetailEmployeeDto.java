package nts.uk.file.pr.app.export.comparingsalarybonus.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
public class DetailEmployeeDto {
	private String personID;
	private String personName;
	DataRowComparingSalaryBonus dataRow;
}
