package nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery;

import java.util.List;

import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         打刻申請
 */
@Getter
public class NRQueryStampAppExport extends NRQueryAppExport {

	// 打刻申請詳細
	private List<NRQueryStampAppDetailExport> appDetailLst;

	public NRQueryStampAppExport(NRQueryAppExport app, List<NRQueryStampAppDetailExport> appDetailLst) {
		super(app.getAppDate(), app.getInputDate(), app.getAppName(), app.getAppType(), app.getBeforeAfterType(),
				app.getApprovalStatus());
		this.appDetailLst = appDetailLst;
	}
}
