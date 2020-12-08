package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain;

import java.util.List;
import java.util.Optional;

import lombok.Getter;

/**
 * @author thanh_nx
 *
 *         残業申請のデーた
 */
@Getter
public class NRQueryOvertimeApp extends NRQueryApp {

	// 外深夜時間
	private Optional<String> midnightIimeOutside;

	// フレックス超過時間
	private Optional<String> flexOvertime;

	// 勤務種類名
	private Optional<String> overTimeWoktypeName;

	// 就業時間帯名
	private Optional<String> overTimeZoneName;

	// 任意項目
	private List<NRQueryOvertimeAppAnyItem> anyItemLst;

	// 乖離理由
	private List<String> reasonDissocsLst;

	// 残業枠
	private List<NRQueryOvertimeQuota> overtimeQuotalst;

	public NRQueryOvertimeApp(NRQueryApp app, Optional<String> midnightIimeOutside, Optional<String> flexOvertime,
			Optional<String> overTimeWoktypeName, Optional<String> overTimeZoneName,
			List<NRQueryOvertimeAppAnyItem> anyItemLst, List<String> reasonDissocsLst,
			List<NRQueryOvertimeQuota> overtimeQuotalst) {
		super(app.getAppDate(), app.getInputDate(), app.getAppName(), app.getAppType(), app.getBeforeAfterType(),
				app.getApprovalStatus());
		this.midnightIimeOutside = midnightIimeOutside;
		this.flexOvertime = flexOvertime;
		this.overTimeWoktypeName = overTimeWoktypeName;
		this.overTimeZoneName = overTimeZoneName;
		this.anyItemLst = anyItemLst;
		this.reasonDissocsLst = reasonDissocsLst;
		this.overtimeQuotalst = overtimeQuotalst;
	}
}
