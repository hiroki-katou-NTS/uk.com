package nts.uk.ctx.at.function.dom.annualworkschedule.export;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.OutputAgreementTime;

@Setter
@Getter
public class HeaderData {
	/** A1_1 タイトル */
	private String title;
	/** A1_2 */
	private String reportName;
	/** B1_1 期間（見出し） */
	private String period;
	/** B1_2 期間 (start) */
	private YearMonth startYearMonth;
	/** B1_2 期間 (end) */
	private YearMonth endYearMonth;
	/** C1_1 */
	private String empInfoLabel;
	/** C1_2 */
	private List<String> months;
	/** C2_2 or C2_4*/
	private OutputAgreementTime outputAgreementTime;
	/** C2_3 or C2_5 */
	private List<String> monthPeriodLabels;
	private PrintFormat printFormat;
}
