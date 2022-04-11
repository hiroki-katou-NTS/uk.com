package nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery;

import java.util.List;
import java.util.Optional;

import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         出張申請
 */
@Getter
public class NRQueryBussinessTripAppExport extends NRQueryAppExport {

	// 就業時間帯名
	private Optional<String> workTimeName;

	// 勤務種類名
	private Optional<String> workTypeName;

	// 期間
	private List<TripAppTimeZoneExport> period;

	public NRQueryBussinessTripAppExport(NRQueryAppExport app, Optional<String> workTimeName, Optional<String> workTypeName,
			List<TripAppTimeZoneExport> period) {
		super(app.getAppDate(), app.getInputDate(), app.getAppName(), app.getAppType(), app.getBeforeAfterType(),
				app.getApprovalStatus());
		this.workTimeName = workTimeName;
		this.workTypeName = workTypeName;
		this.period = period;
	}

}
