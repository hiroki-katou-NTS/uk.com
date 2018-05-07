package nts.uk.ctx.at.function.dom.annualworkschedule.export;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;

@Setter
@Getter
public class HeaderData {
	/** A1_1 タイトル */
	private String title;
	/** B1_1 期間（見出し） */
	private String periodLabel;
	/** B1_2 期間 (start) */
	private YearMonth startYearMonth;
	/** B1_2 期間 (end) */
	private YearMonth endYearMonth;
	/** C1_1 */
	private List<String> empOutputItemLabel;
	/** C1_2 */
	private List<String> months;
}
