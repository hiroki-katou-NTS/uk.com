package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain;

import java.util.Optional;

import lombok.Getter;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         直行直帰申請
 */
@Getter
public class NRQueryGoBackDirectlyApp extends NRQueryApp {

	// 直行区分
	private NotUseAtr goOutAtr;

	// 直帰区分
	private NotUseAtr comeBackAtr;

	// 就業時間帯名
	private Optional<String> workTimeName;

	// 勤務種類名
	private Optional<String> workTypeName;

	public NRQueryGoBackDirectlyApp(NRQueryApp app, NotUseAtr goOutAtr, NotUseAtr comeBackAtr,
			Optional<String> workTimeName, Optional<String> workTypeName) {
		super(app.getAppDate(), app.getInputDate(), app.getAppName(), app.getAppType(), app.getBeforeAfterType(),
				app.getApprovalStatus());
		this.goOutAtr = goOutAtr;
		this.comeBackAtr = comeBackAtr;
		this.workTimeName = workTimeName;
		this.workTypeName = workTypeName;
	}

}
