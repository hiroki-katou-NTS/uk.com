package nts.uk.file.at.app.export.statement.requestlist422;

import lombok.Builder;
import lombok.Data;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Data
@Builder
public class WkpInfoHistExport {

	/** The period. */
	// 期間
	private DatePeriod period;

	/** The wkp code. */
	// 職場コード
	private String wkpCode;

	/** The wkp display name. */
	// 職場表示名
	private String wkpDisplayName;
}
