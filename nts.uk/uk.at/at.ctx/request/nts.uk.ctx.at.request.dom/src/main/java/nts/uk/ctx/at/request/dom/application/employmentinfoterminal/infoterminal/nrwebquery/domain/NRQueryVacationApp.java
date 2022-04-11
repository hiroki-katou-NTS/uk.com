package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain;

import java.util.List;
import java.util.Optional;

import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         休暇申請
 */
@Getter
public class NRQueryVacationApp extends NRQueryApp {

	// 休暇区分
	private String vacationType;

	// 勤務種類名
	private String workTypeName;

	// 就業時間帯名
	private Optional<String> workTimeName;

	// 時間消化休暇名
	private List<NRQueryTimeDigesLeavName> timeDigestLeavNameLst;

	public NRQueryVacationApp(NRQueryApp app, String vacationType, String workTypeName, Optional<String> workTimeName,
			List<NRQueryTimeDigesLeavName> timeDigestLeavNameLst) {
		super(app.getAppDate(), app.getInputDate(), app.getAppName(), app.getAppType(), app.getBeforeAfterType(),
				app.getApprovalStatus());
		this.vacationType = vacationType;
		this.workTypeName = workTypeName;
		this.workTimeName = workTimeName;
		this.timeDigestLeavNameLst = timeDigestLeavNameLst;
	}
}
