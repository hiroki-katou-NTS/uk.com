package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         休出枠
 */
@Getter
@AllArgsConstructor
public class NRQueryHolidayQuota {

	// 休出枠名
	private String holidayQuotaName;

	// 休出時間
	private String holidayQuotaTime;
}
