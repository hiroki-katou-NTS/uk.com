package nts.uk.ctx.at.request.dom.application.common.adapter.workplace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
/**
 * 
 * @author hoatt
 *
 */
@AllArgsConstructor
@Getter
public class WkpInfo {
	/**配属期間*/
	private DatePeriod datePeriod;
	/**職場ID*/
	private String wpkID;
	/**職場コード*/
	private String wpkCD;
	/**職場表示名*/
	private String wpkName;
}
