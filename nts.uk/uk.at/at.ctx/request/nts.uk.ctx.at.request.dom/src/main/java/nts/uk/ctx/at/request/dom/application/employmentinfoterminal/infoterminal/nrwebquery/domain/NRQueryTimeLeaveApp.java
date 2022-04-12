package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain;

import java.util.List;

import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         時間年休申請
 */
@Getter
public class NRQueryTimeLeaveApp extends NRQueryApp {

	// 時間年休申請詳細
	private List<NRQueryTimeLeaveAppDetail> timeLeavDetail;

	public NRQueryTimeLeaveApp(NRQueryApp app, List<NRQueryTimeLeaveAppDetail> timeLeavDetail) {
		super(app.getAppDate(), app.getInputDate(), app.getAppName(), app.getAppType(), app.getBeforeAfterType(),
				app.getApprovalStatus());
		this.timeLeavDetail = timeLeavDetail;
	}
}
