package nts.uk.file.at.app.export.statement.stamp;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OutputConditionListOfStampQuery {
	// A1_1 会社名
	private String companyName;
	// A1_2 タイトル
	private String title;
	// A1_3 日時
	//private String time;
	// A1_4 ページ
	private String page;

	// B1_1 期間（見出し） + B1_2 期間
	private String datePeriodHead;

	//data list
	private List<EmployeeInfor> employeeList;
}
