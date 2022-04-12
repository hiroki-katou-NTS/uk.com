package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain;

import java.util.List;

import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         任意項目申請
 */
@Getter
public class NRQueryAnyItemApp extends NRQueryApp {

	// 任意項目申請詳細
	private List<NRQueryAnyItemAppDetail> anyItemDetailLst;

	public NRQueryAnyItemApp(NRQueryApp app, List<NRQueryAnyItemAppDetail> anyItemDetailLst) {
		super(app.getAppDate(), app.getInputDate(), app.getAppName(), app.getAppType(), app.getBeforeAfterType(),
				app.getApprovalStatus());
		this.anyItemDetailLst = anyItemDetailLst;
	}

}
