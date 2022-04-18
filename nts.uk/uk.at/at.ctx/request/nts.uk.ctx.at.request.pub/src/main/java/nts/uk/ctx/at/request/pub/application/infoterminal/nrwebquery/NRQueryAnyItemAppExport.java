package nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery;

import java.util.List;

import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         任意項目申請
 */
@Getter
public class NRQueryAnyItemAppExport extends NRQueryAppExport {

	// 任意項目申請詳細
	private List<NRQueryAnyItemAppDetailExport> anyItemDetailLst;

	public NRQueryAnyItemAppExport(NRQueryAppExport app, List<NRQueryAnyItemAppDetailExport> anyItemDetailLst) {
		super(app.getAppDate(), app.getInputDate(), app.getAppName(), app.getAppType(), app.getBeforeAfterType(),
				app.getApprovalStatus());
		this.anyItemDetailLst = anyItemDetailLst;
	}

}
