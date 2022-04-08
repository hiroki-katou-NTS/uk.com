package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         残業枠
 */
@AllArgsConstructor
@Getter
public class NRQueryOvertimeQuota {

	private String overtimeQuotaName;

	private String overtimeHours;

}
