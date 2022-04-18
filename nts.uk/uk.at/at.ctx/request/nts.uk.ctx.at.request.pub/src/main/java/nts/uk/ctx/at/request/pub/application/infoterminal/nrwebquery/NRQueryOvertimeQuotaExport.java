package nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         残業枠
 */
@AllArgsConstructor
@Getter
public class NRQueryOvertimeQuotaExport {

	private String overtimeQuotaName;

	private String overtimeHours;

}
