package nts.uk.ctx.hr.notice.app.find.report;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ReportParams {
	private String reportId;
	private int reportLayoutId;
	private boolean screenC;
}
