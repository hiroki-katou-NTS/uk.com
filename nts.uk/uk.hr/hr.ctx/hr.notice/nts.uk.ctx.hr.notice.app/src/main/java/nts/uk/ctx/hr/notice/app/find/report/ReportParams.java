package nts.uk.ctx.hr.notice.app.find.report;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ReportParams {
	private Integer reportId;
	private int reportLayoutId;
}
