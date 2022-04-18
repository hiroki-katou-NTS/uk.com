package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain;

import java.util.List;
import java.util.Optional;

import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         出張申請
 */
@Getter
public class NRQueryBussinessTripApp extends NRQueryApp {

	// 就業時間帯名
	private Optional<String> workTimeName;

	// 勤務種類名
	private Optional<String> workTypeName;

	// 期間
	private List<TripAppTimeZone> period;

	public NRQueryBussinessTripApp(NRQueryApp app, Optional<String> workTimeName, Optional<String> workTypeName,
			List<TripAppTimeZone> period) {
		super(app.getAppDate(), app.getInputDate(), app.getAppName(), app.getAppType(), app.getBeforeAfterType(),
				app.getApprovalStatus());
		this.workTimeName = workTimeName;
		this.workTypeName = workTypeName;
		this.period = period;
	}

}
