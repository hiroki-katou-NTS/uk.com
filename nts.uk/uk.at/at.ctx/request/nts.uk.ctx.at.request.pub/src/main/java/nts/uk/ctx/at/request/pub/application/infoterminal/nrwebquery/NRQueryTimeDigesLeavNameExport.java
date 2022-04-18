package nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         時間消化休暇名
 */
@Getter
@AllArgsConstructor
public class NRQueryTimeDigesLeavNameExport {

	// 時間休暇名
	private String timeDigestName;

	// 時間休暇時間
	private String timeDigestHours;
}
