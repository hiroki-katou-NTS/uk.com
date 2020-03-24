package nts.uk.file.at.app.export.statement.stamp;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OutputConditionListOfStampQuery {
	// A1_1 会社名
	private String companyName;
	// A1_2 タイトル
	private String title;
	// A1_3 日時
	private String dateAndTime;
	// A1_4 ページ
	private String page;

	// B1_1 期間（見出し）
	private String datePeriodHead;
	// B1_2 期間
	private String datePeriod;

}
