package nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery;

import java.util.List;

import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         時間年休申請
 */
@Getter
public class NRQueryTimeLeaveAppExport extends NRQueryAppExport {

	// 時間年休申請詳細
	private List<NRQueryTimeLeaveAppDetailExport> timeLeavDetail;

	public NRQueryTimeLeaveAppExport(NRQueryAppExport app, List<NRQueryTimeLeaveAppDetailExport> timeLeavDetail) {
		super(app.getAppDate(), app.getInputDate(), app.getAppName(), app.getAppType(), app.getBeforeAfterType(),
				app.getApprovalStatus());
		this.timeLeavDetail = timeLeavDetail;
	}
}
