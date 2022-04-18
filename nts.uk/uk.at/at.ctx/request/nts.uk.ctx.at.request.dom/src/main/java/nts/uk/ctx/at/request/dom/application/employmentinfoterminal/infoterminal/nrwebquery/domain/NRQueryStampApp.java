package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain;

import java.util.List;

import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         打刻申請
 */
@Getter
public class NRQueryStampApp extends NRQueryApp {

	// 打刻申請詳細
	private List<NRQueryStampAppDetail> appDetailLst;

	public NRQueryStampApp(NRQueryApp app, List<NRQueryStampAppDetail> appDetailLst) {
		super(app.getAppDate(), app.getInputDate(), app.getAppName(), app.getAppType(), app.getBeforeAfterType(),
				app.getApprovalStatus());
		this.appDetailLst = appDetailLst;
	}
}
