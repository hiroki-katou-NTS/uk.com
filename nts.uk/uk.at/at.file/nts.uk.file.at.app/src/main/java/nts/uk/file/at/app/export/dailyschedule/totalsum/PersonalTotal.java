package nts.uk.file.at.app.export.dailyschedule.totalsum;

import java.util.List;

import lombok.Data;

@Data
public class PersonalTotal {
	// 社員ID
	private String employeeId;
	// 個人合算値
	private List<TotalValue> personalSumTotal;
}
