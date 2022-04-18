package nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery;

import java.util.List;

import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         NRQueryLateCancelApp
 */
@Getter
public class NRQueryLateCancelAppExport extends NRQueryAppExport {

	// 申請内容
	private List<String> lateCancelAppDetailLst;

	public NRQueryLateCancelAppExport(NRQueryAppExport app, List<String> lateCancelAppDetailLst) {
		super(app.getAppDate(), app.getInputDate(), app.getAppName(), app.getAppType(), app.getBeforeAfterType(),
				app.getApprovalStatus());
		this.lateCancelAppDetailLst = lateCancelAppDetailLst;
	}

}
