package nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.TimeZone;

/**
 * @author thanh_nx
 *
 *         勤務変更申請
 */
@Getter
public class NRQueryWorkChangeAppExport extends NRQueryAppExport {

	// 直行区分
	private NotUseAtr goOutAtr;

	// 直帰区分
	private NotUseAtr comeBackAtr;

	// 就業時間帯名
	private Optional<String> workTimeName;

	// 勤務種類名
	private Optional<String> workTypeName;

	// 時刻
	private List<TimeZone> hourMinuteLst;

	public NRQueryWorkChangeAppExport(NRQueryAppExport app, NotUseAtr goOutAtr, NotUseAtr comeBackAtr,
			Optional<String> workTimeName, Optional<String> workTypeName, List<TimeZone> hourMinuteLst) {
		super(app.getAppDate(), app.getInputDate(), app.getAppName(), app.getAppType(), app.getBeforeAfterType(),
				app.getApprovalStatus());
		this.goOutAtr = goOutAtr;
		this.comeBackAtr = comeBackAtr;
		this.workTimeName = workTimeName;
		this.workTypeName = workTypeName;
		this.hourMinuteLst = hourMinuteLst;
	}
}
