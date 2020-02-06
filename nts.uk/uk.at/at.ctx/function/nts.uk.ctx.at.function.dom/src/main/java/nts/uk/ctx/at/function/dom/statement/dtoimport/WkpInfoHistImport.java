package nts.uk.ctx.at.function.dom.statement.dtoimport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WkpInfoHistImport {

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
