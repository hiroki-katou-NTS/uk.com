package nts.uk.ctx.hr.notice.dom.report.valueImported;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class DateRangeItemImport {
	private String personInfoCtgId;
	private String startDateItemId;
	private String endDateItemId;
	private String dateRangeItemId;
}
