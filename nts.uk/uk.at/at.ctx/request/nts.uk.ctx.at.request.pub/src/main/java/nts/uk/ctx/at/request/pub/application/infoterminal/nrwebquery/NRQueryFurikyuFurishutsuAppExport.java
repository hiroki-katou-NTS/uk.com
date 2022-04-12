package nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery;

import java.util.Optional;

import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         振休振出申請
 */
@Getter
public class NRQueryFurikyuFurishutsuAppExport extends NRQueryAppExport {

	// 勤務種類名
	private String workTypeName;

	// 就業時間帯名
	private Optional<String> workTimeName;

	// 振休申請
	private boolean furikyu;

	public NRQueryFurikyuFurishutsuAppExport(NRQueryAppExport app, String workTypeName, Optional<String> workTimeName,
			boolean furikyu) {
		super(app.getAppDate(), app.getInputDate(), app.getAppName(), app.getAppType(), app.getBeforeAfterType(),
				app.getApprovalStatus());
		this.workTypeName = workTypeName;
		this.workTimeName = workTimeName;
		this.furikyu = furikyu;
	}

}
