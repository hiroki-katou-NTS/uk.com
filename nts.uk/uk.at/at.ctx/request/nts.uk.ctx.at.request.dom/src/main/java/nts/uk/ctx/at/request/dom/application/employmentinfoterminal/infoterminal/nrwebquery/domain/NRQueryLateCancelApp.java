package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain;

import java.util.List;

import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         NRQueryLateCancelApp
 */
@Getter
public class NRQueryLateCancelApp extends NRQueryApp {

	// 申請内容
	private List<String> lateCancelAppDetailLst;

	public NRQueryLateCancelApp(NRQueryApp app, List<String> lateCancelAppDetailLst) {
		super(app.getAppDate(), app.getInputDate(), app.getAppName(), app.getAppType(), app.getBeforeAfterType(),
				app.getApprovalStatus());
		this.lateCancelAppDetailLst = lateCancelAppDetailLst;
	}

}
